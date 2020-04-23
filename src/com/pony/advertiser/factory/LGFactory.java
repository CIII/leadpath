package com.pony.advertiser.factory;

import javax.inject.Inject;

import com.pony.advertiser.AdvertiserWriter;
import com.tapquality.dispositions.guess.GuessEngine;
import com.tapquality.dispositions.guess.MediaAlphaLuceneGuessEngine;

public class LGFactory implements AdvertiserFactory {
	protected ExternalIdMatcher matcher = new DefaultIdMatcher();
	@Inject	
	protected MediaAlphaLuceneGuessEngine guessEngine;
	public LGFactory() {}

	@Override
	public ExternalIdMatcher getExternalIdMatcher() {
		return matcher;
	}

	@Override
	public AdvertiserWriter getWriter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GuessEngine getDispositionGuessEngine() {
		return guessEngine;
	}

	@Override
	public DispositionParser getDispositionDataParser() {
		return new MediaAlphaDispositionParser();
	}

}
