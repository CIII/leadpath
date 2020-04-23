package com.pony.advertiser.factory;

import com.pony.advertiser.AdvertiserWriter;
import com.tapquality.dispositions.guess.GuessEngine;

public interface AdvertiserFactory {
	ExternalIdMatcher getExternalIdMatcher();
	AdvertiserWriter getWriter();
	GuessEngine getDispositionGuessEngine();
	DispositionParser getDispositionDataParser();
}
