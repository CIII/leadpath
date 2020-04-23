package com.tapquality.db.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.tapquality.db.tables.records.OrdersRecord;

@SuppressWarnings("serial")
public class OrdersRecordSerializer extends StdSerializer<OrdersRecord> {

	public OrdersRecordSerializer() {
		super(OrdersRecord.class);
	}

	@Override
	public void serialize(OrdersRecord value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeStartObject();
		gen.writeNumberField("id", value.getId());
		gen.writeStringField("code", value.getCode());
		gen.writeEndObject();
	}
}
