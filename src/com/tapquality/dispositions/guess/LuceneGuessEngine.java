package com.tapquality.dispositions.guess;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.csv.CSVRecord;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;

import com.tapquality.dispositions.guess.Guess.Status;

public abstract class LuceneGuessEngine implements GuessEngine {
	private static final Log LOG = LogFactory.getLog(LuceneGuessEngine.class);
	static final String GUESS_FIELD = "guess";
	static final double MINIMUM_SCORE = .95;
	protected Directory directory;
	protected Analyzer analyzer;
	protected final String[] searchableFields;
	protected boolean initialized = false;

	public LuceneGuessEngine(String[] searchableFields, boolean initialize) throws GuessException {
		this.searchableFields = searchableFields;
		if (initialize) {
			try {
				initialize();
			} catch (IOException e) {
				String errMsg = "Unknown exception initializing lucene engine.";
				LOG.warn(errMsg, e);
				throw new GuessException(errMsg, e);
			}
		}
	}
	
	public void initialize() throws IOException {
		this.initialized = true;
	}

	@Override
	public Guess guess(CSVRecord headers, CSVRecord record) throws GuessException {
		assert record != null : "Null was passed instead of a record";
		
		try {
			if (!initialized) { this.initialize(); }
		} catch (IOException e) {
			String errMsg = "Failed to initialize guess engine.";
			LOG.warn(errMsg, e);
			throw new GuessException(errMsg, e);
		}
		try (DirectoryReader ireader = DirectoryReader.open(directory)) {
			
			IndexSearcher isearcher = new IndexSearcher(ireader);
	
			Map<String, Integer> indexMap = new HashMap<>();
			for (String field : searchableFields) {
				for (int i = 0; i < headers.size(); i++) {
					String header = headers.get(i);
					if (header.equals(field)) {
						indexMap.put(field, i);
					}
				}
			}
	
			BooleanQuery.Builder builder = new BooleanQuery.Builder();
			for (String field : searchableFields) {
				Integer index = indexMap.get(field);
				String value = record.get(index);
				if(!"".equals(value)) {
					Query query = new QueryParser(field, this.analyzer).parse(QueryParser.escape(value.replace("?", "")));
					builder.add(query, BooleanClause.Occur.SHOULD);
				}
			}
	
			TopDocs topDocs = isearcher.search(builder.build(), 10, Sort.RELEVANCE, true, true);
			Float maxScore = topDocs.getMaxScore();
			ScoreDoc[] scores = topDocs.scoreDocs;
			String consensus = null;
			Map<String, Integer> conflicts = new HashMap<>();
			Guess.Status currentStatus = Guess.Status.SUCCESS;
		    for (int i = 0; i < scores.length; i++) {
		    	ScoreDoc score = scores[i];
		    	float normalizedScore = score.score / maxScore;
		    	LOG.debug("Score: " + normalizedScore);
		    	if(normalizedScore > MINIMUM_SCORE) {
		    		Document hitDoc = isearcher.doc(scores[i].doc);
		    		if(consensus != null && !consensus.equals(hitDoc.get(GUESS_FIELD))) {
		    			currentStatus = Guess.Status.CONFLICT;
		    			Integer count = conflicts.getOrDefault(hitDoc.get(GUESS_FIELD), 0);
		    			conflicts.put(hitDoc.get(GUESS_FIELD), count + 1);
		    		} else if (consensus == null && hitDoc.get(GUESS_FIELD) != null) {
		    			conflicts.put(hitDoc.get(GUESS_FIELD), 1);
		    		}
		    		consensus = hitDoc.get(GUESS_FIELD);
		    		if (consensus != null) {
		    			LOG.debug("Consensus so far is " + consensus);
		    			LOG.debug("\t" + hitDoc.toString());
		    		}
		    	}
		    }
	
	    	if (consensus == null) {
				LOG.debug("No high confidence matches");
				currentStatus = Status.NO_GUESS;
		    }
	
	    	switch (currentStatus) {
	    	case SUCCESS:
	    		return new Guess(consensus, currentStatus);
	    	case CONFLICT:
	    		StringBuilder errMsgBuilder = new StringBuilder("There are conflicting guesses: ");
	    		boolean first = true;
	    		for (Map.Entry<String, Integer> conflictEntry : conflicts.entrySet()) {
	    			if (!first) {
	    				errMsgBuilder.append(", ");
	    				first = false;
	    			}
	    			errMsgBuilder.append(conflictEntry.getValue());
	    			errMsgBuilder.append(" for ");
	    			errMsgBuilder.append(conflictEntry.getKey());
	    		}
	    		errMsgBuilder.append(".");
	    		return new Guess(currentStatus, errMsgBuilder.toString());
	    	case NO_GUESS:
	    	default:
	    		return new Guess(currentStatus, "There was not enough information to produce a guess.");
	    	}
		} catch (IOException e) {
			String errMsg = "Unknown IOException reading from the lucene index making a disposition guess.";
			LOG.warn(errMsg, e);
			throw new GuessException(errMsg, e);
		} catch (ParseException e) {
			String errMsg = "Exception parsing the query to make a disposition guess.";
			LOG.warn(errMsg, e);
			throw new GuessException(errMsg, e);
		}
	}

	@Override
	public void shutDown() {
		try {
			directory.close();
		} catch (IOException e) {
			String errMsg = "Exception shutting down Lucene index.";
			LOG.error(errMsg, e);
		}
	}

	@Override
	public void recordGuess(Map<String, String> record) throws GuessException {
		try (IndexWriter iwriter = new IndexWriter(this.directory, new IndexWriterConfig(this.analyzer))) {
			if (!initialized) { this.initialize(); }
			
			Document doc = new Document();
			for (Map.Entry<String, String> recordEntry : record.entrySet()) {
				doc.add(new Field(recordEntry.getKey(), recordEntry.getValue(), TextField.TYPE_STORED));
			}
			iwriter.addDocument(doc);
		} catch (IOException e) {
			String errMsg = "Exception interacting with the on-disk lucene index.";
			LOG.warn(errMsg, e);
			throw new GuessException(errMsg, e);
		}
	}

	@Override
	public boolean isInitialized() {
		return this.initialized;
	}

	@Override
	public boolean isIndexed(String columnName) {
		boolean returnValue = false;
		for (String field : this.searchableFields) {
			if (field.equals(columnName)) {
				returnValue = true;
				break;
			}
		}
		return returnValue;
	}
}
