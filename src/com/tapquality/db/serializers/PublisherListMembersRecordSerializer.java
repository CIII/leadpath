package com.tapquality.db.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.tapquality.db.tables.records.PublisherListMembersRecord;

@SuppressWarnings("serial")
public class PublisherListMembersRecordSerializer extends StdSerializer<PublisherListMembersRecord> {

	public PublisherListMembersRecordSerializer() {
		super(PublisherListMembersRecord.class);
	}

	@Override
	public void serialize(PublisherListMembersRecord value, JsonGenerator gen, SerializerProvider provider)
			throws IOException {
		gen.writeStartObject();
		gen.writeNumberField("id", value.getId());
		gen.writeNumberField("publisher_id", value.getPublisherId());
		gen.writeEndObject();
	}
}
