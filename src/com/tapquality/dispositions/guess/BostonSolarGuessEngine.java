package com.tapquality.dispositions.guess;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;

public class BostonSolarGuessEngine extends LuceneGuessEngine {
	static final Log LOG = LogFactory.getLog(BostonSolarGuessEngine.class);

	protected static String[] SEARCHABLE_FIELDS = {
			"Lead Status",
			"Stage",
			"Appointment #1 Result"
	};
	
	@Inject
	@Named("bostonsolar.index")
	protected String indexLocation;
	
	public BostonSolarGuessEngine() throws GuessException {
		super(SEARCHABLE_FIELDS, false);
	}
	
	public BostonSolarGuessEngine(boolean initialize) throws GuessException {
		super(SEARCHABLE_FIELDS, initialize);
	}
	
	@Override
	public void initialize() throws IOException {
		this.directory = FSDirectory.open((new File(indexLocation)).toPath());
		this.analyzer = new StandardAnalyzer();
		try (IndexWriter iwriter = new IndexWriter(directory, new IndexWriterConfig(this.analyzer))) {
			iwriter.commit();
		}
	}

}
