package com.pony.advertiser.factory;

import com.pony.advertiser.AdvertiserWriter;
import com.tapquality.dispositions.guess.GuessEngine;

public class TestFactory implements AdvertiserFactory {
	protected ExternalIdMatcher matcher = new DefaultIdMatcher();
	
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
		return null;
	}

	@Override
	public DispositionParser getDispositionDataParser() {
		return null;
	}

}
