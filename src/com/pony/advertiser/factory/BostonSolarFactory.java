package com.pony.advertiser.factory;

import javax.inject.Inject;

import com.pony.advertiser.AdvertiserWriter;
import com.tapquality.dispositions.guess.BostonSolarGuessEngine;
import com.tapquality.dispositions.guess.GuessEngine;

public class BostonSolarFactory implements AdvertiserFactory {
	protected ExternalIdMatcher matcher = new SalesforceIdMatcher();
	@Inject
	protected BostonSolarGuessEngine guessEngine;
	
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
		return new BostonSolarDispositionParser();
	}

}
