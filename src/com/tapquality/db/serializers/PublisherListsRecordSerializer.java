package com.tapquality.db.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.tapquality.db.tables.records.PublisherListsRecord;

@SuppressWarnings("serial")
public class PublisherListsRecordSerializer extends StdSerializer<PublisherListsRecord> {

	public PublisherListsRecordSerializer() {
		super(PublisherListsRecord.class);
	}

	@Override
	public void serialize(PublisherListsRecord value, JsonGenerator gen, SerializerProvider provider)
			throws IOException {
		gen.writeStartObject();
		gen.writeNumberField("id", value.getId());
		gen.writeStringField("name", value.getName());
		gen.writeBooleanField("is_direct", value.getIsDirect() == 1 ? true : false);
		gen.writeNumberField("max_lead_units", value.getMaxLeadUnits());
		gen.writeNumberField("status", value.getStatus());
		gen.writeStringField("ext_list_id", value.getExtListId());
		gen.writeEndObject();
	}
}
