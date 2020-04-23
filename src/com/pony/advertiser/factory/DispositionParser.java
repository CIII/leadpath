package com.pony.advertiser.factory;

import java.util.HashMap;
import java.util.Map;

import com.pony.PonyException;
import com.pony.advertiser.Advertiser;
import com.pony.advertiser.Disposition;

public interface DispositionParser {
	Map<String, Disposition.Status> TYPES = new HashMap<>();

	public Disposition createDisposition(Map<String, String> data, Advertiser advertiser, AdvertiserFactory factory, StringBuilder returnMessage) throws PonyException;
}
