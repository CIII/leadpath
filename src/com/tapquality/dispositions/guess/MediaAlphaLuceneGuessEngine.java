package com.tapquality.dispositions.guess;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;

@Singleton
public class MediaAlphaLuceneGuessEngine extends LuceneGuessEngine {
	static final Log LOG = LogFactory.getLog(MediaAlphaLuceneGuessEngine.class);
	
	protected static String[] SEARCHABLE_FIELDS = {
				"Lead Status",
				"Unqualified Reason",
				"Stage"
		};

	@Inject @Named("mediaalpha.index") String indexLocation;
	
	public MediaAlphaLuceneGuessEngine() throws GuessException {
		super(SEARCHABLE_FIELDS, false);
	}
	
	public MediaAlphaLuceneGuessEngine(boolean initialize) throws GuessException {
		super(SEARCHABLE_FIELDS, initialize);
	}

	@Override
	public void initialize() throws IOException {
		this.directory = FSDirectory.open((new File(indexLocation)).toPath());
		this.analyzer = new StandardAnalyzer();
		try (IndexWriter iwriter = new IndexWriter(directory, new IndexWriterConfig(this.analyzer))) {
			iwriter.commit();
		}
		super.initialize();
	}

}
