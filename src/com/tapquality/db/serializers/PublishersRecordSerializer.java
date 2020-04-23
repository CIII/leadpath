package com.tapquality.db.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.tapquality.db.tables.records.PublishersRecord;

@SuppressWarnings("serial")
public class PublishersRecordSerializer extends StdSerializer<PublishersRecord> {
	
	public PublishersRecordSerializer() {
		super(PublishersRecord.class);
	}
	
	@Override
	public void serialize(PublishersRecord value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeStartObject();
		gen.writeNumberField("id", value.getId());
		gen.writeStringField("name", value.getName());
		gen.writeStringField("domain_name", value.getDomainName());
		gen.writeStringField("user_name", value.getUserName());
		gen.writeBooleanField("allow_duplicates", value.getAllowDuplicates() == 1 ? true : false);
		gen.writeBooleanField("extended_validation", value.getExtendedValidation() == 1 ? true : false);
		gen.writeEndObject();
	}

}
