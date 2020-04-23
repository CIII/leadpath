package com.tapquality.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.inject.multibindings.MapBinder;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.name.Names;
import com.google.inject.servlet.ServletModule;
import com.pony.advertiser.AdvertiserServlet;
import com.pony.advertiser.AdvertiserWriter;
import com.pony.advertiser.dispositioner.Dispositioner;
import com.pony.advertiser.factory.AdvertiserFactory;
import com.pony.publisher.PublisherServlet;
import com.pony.rules.Rule;
import com.tapquality.email.subscribe.SubscriptionServlet;

public class TapQualityServletModule extends ServletModule {
	private static final String LEADPATH_PROPERTIES_URL_KEY = "LEADPATH_PROPERTIES_URL";
	private static final String DEFAULT_URL = "/guice.properties";
	private static final Log LOG = LogFactory.getLog(TapQualityServletModule.class);
	
	@Override
	protected void configureServlets() {
		super.configureServlets();
		
		serve("/post").with(PublisherServlet.class);
		serve("/repost").with(PublisherServlet.class);
		serve("/ping").with(PublisherServlet.class);
		serve("/poll").with(PublisherServlet.class);
		serve("/disposition").with(AdvertiserServlet.class);
        serve("/lead-status").with(AdvertiserServlet.class);
        serve("/subscribe").with(SubscriptionServlet.class);
        serve("*-page").with(StaticPageServlet.class);
        serve("/admin/advertisers").with(AdvertisersAdminServlet.class);
        serve("/admin/advertisers/*").with(AdvertisersAdminServlet.class);
        serve("/admin/leads").with(LeadsAdminServlet.class);
        serve("/admin/leads/*").with(LeadsAdminServlet.class);
        serve("/admin/publishers").with(PublishersAdminServlet.class);
        serve("/admin/publishers/*").with(PublishersAdminServlet.class);
        serve("/admin/orders").with(OrdersAdminServlet.class);
        serve("/admin/orders/*").with(OrdersAdminServlet.class);
        serve("/admin/publisher-lists").with(PublisherListsAdminServlet.class);
        serve("/admin/publisher-lists/*").with(PublisherListsAdminServlet.class);
		serve("/lead-disposition/*").with(AdvertiserServlet.class);
		
		Properties guiceProperties = new Properties();
		InputStream propertiesStream = null;
		try {
			String url = System.getenv(LEADPATH_PROPERTIES_URL_KEY);
			LOG.debug("Guice properties URL: " + url);
			File file = null;
			if(url != null) {
				file = new File(url);
			} 
			if(file != null && file.exists()) {
				LOG.debug("Loading guice properties from external file");
				propertiesStream = new FileInputStream(new File(url));
			} else {
				LOG.debug("Attempting to load guice properties from classpath.");
				propertiesStream = getClass().getResourceAsStream(DEFAULT_URL);
			}

			guiceProperties.load(propertiesStream);
		} catch (IOException e) {
			LOG.error("Error getting guice.properties); advertisers will not be contacted.", e);
		} finally {
			if(propertiesStream != null) {
				try {
					propertiesStream.close();
				} catch (IOException e) {
					LOG.info("Error closing the guice properties stream. Moving on.");
				}
			}
		}
		
		// Initialize advertiser writer bindings
		Map<Integer, String> ruleMap = new TreeMap<>(); // Using a TreeMap ensures that keySet is sorted.
		Map<Integer, String> dispositionerMap = new TreeMap<>();
		MapBinder<String, AdvertiserFactory> factoryBinder = MapBinder.newMapBinder(binder(), String.class, AdvertiserFactory.class);
		Map<String, String> parameters = new HashMap<>();
		MapBinder<String, AdvertiserWriter> writerBinder = MapBinder.newMapBinder(binder(), String.class, AdvertiserWriter.class);
		
		Pattern advertiserWriterPropertiesPattern = Pattern.compile("^advertiser\\.writer\\.([^\\.]+)\\.(.*)$");
		Properties advertiserWriterProperties = new Properties();
		Pattern advertiserFactoryPattern = Pattern.compile("^advertiser\\.factory\\.([^\\.]+)$");
		for(String propertyName : guiceProperties.stringPropertyNames()) {
			Matcher matcher;
			if((matcher = advertiserWriterPropertiesPattern.matcher(propertyName)).matches()) {
				String writerName = matcher.group(1);
				String attributeName = matcher.group(2);
				String attributeValue = guiceProperties.getProperty(propertyName);
				advertiserWriterProperties.put(writerName + attributeName, attributeValue);
			} else if(propertyName.startsWith("advertiser.writer.")) {
				try {
					String writerClassName = guiceProperties.getProperty(propertyName);
					if(writerClassName == null) { LOG.debug("No property value for name " + propertyName); continue; }
					Class<AdvertiserWriter> writerClass = (Class<AdvertiserWriter>)Class.forName(writerClassName.trim());
					writerBinder.addBinding(propertyName.substring(18)).to(writerClass);
					LOG.debug("Added writer class");
				} catch (ClassNotFoundException e) {
					LOG.error("Class not found for advertiser writer " + propertyName + ". That advertiser will not be contacted properly.");
				}
			} else if(propertyName.startsWith("advertiser.dispositioner.")) {
				dispositionerMap.put(Integer.parseInt(propertyName.substring(25)), guiceProperties.getProperty(propertyName));
			} else if((matcher = advertiserFactoryPattern.matcher(propertyName)).matches()) {
				try {
					String advertiserFactoryName = matcher.group(1);
					String factoryClassName = guiceProperties.getProperty(propertyName);
					if(factoryClassName == null) { LOG.debug("No property value for name " + propertyName); continue; }
					Class<AdvertiserFactory> factoryClass = (Class<AdvertiserFactory>)Class.forName(factoryClassName);
					factoryBinder.addBinding(advertiserFactoryName).to(factoryClass);
				} catch (ClassNotFoundException e) {
					LOG.error("Class not found for advertiser factory " + propertyName + ". That factory will not be instantiated properly.");
				}
			} else if(propertyName.startsWith("rule.")) {
				ruleMap.put(Integer.parseInt(propertyName.substring(5)), guiceProperties.getProperty(propertyName));
			} else if(propertyName.startsWith("parameter.")) {
				parameters.put(propertyName.substring(10), guiceProperties.getProperty(propertyName));
			} else {
				try {
					Class clazz = (Class<Object>)Class.forName(propertyName);
					binder().bind(clazz).to((Class<Object>)Class.forName(guiceProperties.getProperty(propertyName)));
				} catch (ClassNotFoundException e) {
					LOG.error("Class not found for " + propertyName + " during injection.");
				}
			}
		}
		if(advertiserWriterProperties.size() > 0) {
			bind(Properties.class).annotatedWith(Names.named("writerProperties")).toInstance(advertiserWriterProperties);
		}
		
		// Initialize rule bindings
		Multibinder<Rule> rulesBinder = Multibinder.newSetBinder(binder(), Rule.class);
		Set<Integer> ruleIndexSet = ruleMap.keySet();
		for(Integer ruleIndex : ruleIndexSet) {
			try {
				rulesBinder.addBinding().to((Class<Rule>)Class.forName(ruleMap.get(ruleIndex)));
			} catch (ClassNotFoundException e) {
				LOG.error("Class not found for rule " + ruleIndex + ". That rule will not be utilized.");
			}
		}
		
		Multibinder<Dispositioner> dispositionersBinder = Multibinder.newSetBinder(binder(), Dispositioner.class);
		Set<Integer> dispositionerIndexSet = dispositionerMap.keySet();
		for(Integer dispositionerIndex : dispositionerIndexSet) {
			try {
				dispositionersBinder.addBinding().to((Class<Dispositioner>)Class.forName(dispositionerMap.get(dispositionerIndex)));
			} catch (ClassNotFoundException e) {
				LOG.error("Class not found for dispositioner " + dispositionerIndex + ". That dispositioner will not be utilized.");
			}
		}
		
		for(Map.Entry<String, String>parameter : parameters.entrySet()) {
			bind(String.class).annotatedWith(Names.named(parameter.getKey())).toInstance(parameter.getValue());
		}
	}

}
