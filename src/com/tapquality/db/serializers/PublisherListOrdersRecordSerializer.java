package com.tapquality.db.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.tapquality.db.tables.records.PublisherListOrdersRecord;

@SuppressWarnings("serial")
public class PublisherListOrdersRecordSerializer extends StdSerializer<PublisherListOrdersRecord> {

	public PublisherListOrdersRecordSerializer() {
		super(PublisherListOrdersRecord.class);
	}

	@Override
	public void serialize(PublisherListOrdersRecord value, JsonGenerator gen, SerializerProvider provider)
			throws IOException {
		gen.writeStartObject();
		gen.writeNumberField("id", value.getId());
		gen.writeNumberField("order_id", value.getOrderId());
		gen.writeEndObject();
	}
}
