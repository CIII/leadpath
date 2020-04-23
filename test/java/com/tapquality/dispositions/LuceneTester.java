package com.tapquality.dispositions;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.junit.Test;

public class LuceneTester {

	@Test
	public void test() throws Exception {
	    Analyzer analyzer = new StandardAnalyzer();

	    // Store the index in memory:
	    Directory directory = new RAMDirectory();
	    // To store an index on disk, use this instead:
	    //Directory directory = FSDirectory.open("/tmp/testindex");
	    IndexWriterConfig config = new IndexWriterConfig(analyzer);
	    IndexWriter iwriter = new IndexWriter(directory, config);
	    
	    // Get the records
	    File repository = new File("C:/Users/joncard/Desktop/mediaalpha_2017_09_01_2.csv");
	    Reader inputReader = new FileReader(repository);
	    Iterable<CSVRecord> records = CSVFormat.EXCEL
	    		.withIgnoreSurroundingSpaces()
	    		.parse(inputReader);

	    CSVRecord headerRecord = null;
	    for(CSVRecord record : records) {
	    	if (headerRecord == null) {
	    		headerRecord = record;
	    	} else {
	    		Document doc = new Document();
	    		for (int i = 0; i < headerRecord.size(); i++) {
	    			doc.add(new Field(headerRecord.get(i), record.get(i), TextField.TYPE_STORED));
	    		}
    			iwriter.addDocument(doc);
	    	}
	    }
	    
	    iwriter.close();
	    
	    // Now search the index:
	    DirectoryReader ireader = DirectoryReader.open(directory);
	    IndexSearcher isearcher = new IndexSearcher(ireader);
	    // Parse a simple query that searches for "text":
	    QueryParser parser = new QueryParser("Lead Status", analyzer);
	    Query query1 = parser.parse("Unqualified");
	    Query query2 = new QueryParser("Unqualified Reason", analyzer).parse("Possible Fraudulent Lead");
	    //Query query3 = new QueryParser("Stage", analyzer).parse("");
	    BooleanQuery.Builder builder = new BooleanQuery.Builder();
	    //Query term1 = new TermQuery(new Term("Lead Status", "existing"));
	    //Query term11 = new TermQuery(new Term("Lead Status", "lead"));
	    //Query term2 = new TermQuery(new Term("Unqualified Reason", ""));
	    //Query term3 = new TermQuery(new Term("Stage", ""));
	    builder.add(new BooleanClause(query1, BooleanClause.Occur.SHOULD));
	    builder.add(new BooleanClause(query2, BooleanClause.Occur.SHOULD));
	    //builder.add(new BooleanClause(query3, BooleanClause.Occur.SHOULD));
	    BooleanQuery query = builder.build();
	    ScoreDoc[] hits = isearcher.search(query, 1000).scoreDocs;
	    assertNotEquals(0, hits.length);
	    // Iterate through the results:
    	String consensus = null;
    	System.out.println(hits.length);
	    for (int i = 0; i < hits.length; i++) {
	    	ScoreDoc score = hits[i];
	    	if(score.score > .9) {
	    		Document hitDoc = isearcher.doc(hits[i].doc);
	    		if(consensus != null && !hitDoc.get("Status").equals(consensus)) {
	    			System.out.println("Conflict! " + consensus + " " + hitDoc.get("Status"));
	    			continue;
	    		}
	    		consensus = hitDoc.get("Status");
		    	System.out.println("Consensus so far is " + consensus);
		    	System.out.println("\t" + hitDoc.toString());
	    	} else {
	    		System.out.println("Document too unsure to count.");
	    	}
	    }
    	if (consensus == null) {
			System.out.println("No high confidence matches");
	    } else {
	    	System.out.println("Consensus is '" + consensus + "'");
	    }
	    //assertEquals("This is the text to be indexed.", hitDoc.get("fieldname"));
	    ireader.close();
	    directory.close();
	}

}
