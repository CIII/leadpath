package com.tapquality.db.deserializers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.pony.lead.LeadType;
import com.pony.leadtypes.Pony;
import com.pony.publisher.PublisherList;
import com.pony.publisher.Status;

@SuppressWarnings("serial")
public class PublisherListDeserializer extends StdDeserializer<PublisherList> {
	Long id;
	LeadType leadType;
	String name;
	String externalId;
	Integer maxLeadUnits;
	Boolean direct;
	Status status;
	List<Integer> orders;
	List<Integer> publishers;

	public PublisherListDeserializer() {
		super(PublisherList.class);
		resetValues();
	}
	
	/**
	 * This method is in case the instance is reused. The values need to be reset to keep this stateless.
	 */
	private void resetValues() {
		id = null;
		leadType = new Pony();
		name = null;
		externalId = null;
		maxLeadUnits = null;
		direct = null;
		status = null;
		orders = null;
		publishers = null;
	}

	@Override
	public PublisherList deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {

		JsonToken currentToken;
		String tokenName;
		PublisherListParserState defaultListState = new PublisherListParserState() {

			@Override
			public void parseString(JsonParser p) throws IOException {
				String tokenName = p.getCurrentName();
				switch (tokenName) {
				case "name":
					name = p.getValueAsString();
					break;
				case "ext_list_id":
					externalId = p.getValueAsString();
					break;
				}
			}

			@Override
			public void parseInteger(JsonParser p) throws IOException {
				String tokenName = p.getCurrentName();
				switch (tokenName) {
				case "id":
					id = p.getValueAsLong();
					break;
				case "max_lead_units":
					maxLeadUnits = p.getIntValue();
					break;
				case "status":
					Integer tempStatus = p.getIntValue();
					switch (tempStatus) {
					case 1:
						status = Status.OPEN;
						break;
					default:
						status = Status.PAUSED;
						break;
					}
					break;
				}				
			}

			@Override
			public void parseBoolean(JsonParser p) throws IOException {
				direct = p.getBooleanValue();
			}
			
		};
		PublisherListParserState ordersArrayListState = new PublisherListParserState() {
			@Override
			public void parseString(JsonParser p) {}
			@Override
			public void parseBoolean(JsonParser p) {}
			@Override
			public void parseInteger(JsonParser p) throws IOException {
				orders.add(p.getIntValue());
			}
		};
		this.orders = new ArrayList<>();
		this.publishers = new ArrayList<>();
		PublisherListParserState publisherArrayListState = new PublisherListParserState() {
			@Override
			public void parseString(JsonParser p) {}
			@Override
			public void parseBoolean(JsonParser p) {}
			@Override
			public void parseInteger(JsonParser p) throws IOException {
				publishers.add(p.getIntValue());
			}
		};
		PublisherListParserState currentState = defaultListState;
		while((currentToken = p.nextToken()) != null) {
			switch(currentToken) {
			case VALUE_NUMBER_INT:
				currentState.parseInteger(p);
				break;
			case VALUE_STRING:
				currentState.parseString(p);
				break;
			case VALUE_FALSE:
			case VALUE_TRUE:
				currentState.parseBoolean(p);
				break;
			case START_ARRAY:
				tokenName = p.getCurrentName();
				switch (tokenName) {
				case "orders":
					currentState = ordersArrayListState;
					break;
				case "publishers":
					currentState = publisherArrayListState;
					break;
				default:
					break;
				}
				break;
			case END_ARRAY:
				currentState = defaultListState;
				break;
			default:
				break;
			}
		}
		
		// TODO: Defaults?
		
		PublisherList returnValue = new PublisherList(id, leadType, name, externalId, status, maxLeadUnits, direct, orders, publishers);
		resetValues();
		return returnValue;
	}
	
	private interface PublisherListParserState {
		public void parseString(JsonParser p) throws IOException;
		public void parseInteger(JsonParser p) throws IOException;
		public void parseBoolean(JsonParser p) throws IOException;
	}
}
