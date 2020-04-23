package com.tapquality.processors;

import java.sql.SQLException;

import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pony.models.ArrivalModelImpl;
import com.pony.publisher.IPublisherContext;
import com.pony.publisher.PublisherException;
import com.pony.validation.ValidationException;
import com.pony.validation.ValidationResponse;
import com.pony.validation.ValidationService;

public class PostValidationTask implements ProcessingTask {
	private static final String VALIDATION_EXCEPTION = "validation_exception";
	protected static final Log LOG = LogFactory.getLog(PostValidationTask.class);
	protected ValidationService validationService;
	
	public PostValidationTask(ValidationService service) {
		this.validationService = service;
	}
	
	
	@Override
	public IPublisherContext process(IPublisherContext context) throws PublisherException {
		try {
			ValidationResponse vResponse = validationService.post(context);
			
            ArrivalModelImpl.touchStatic(context.getArrival(), vResponse);
            context.setValidationResponse(vResponse);
		} catch (ValidationException e) {
			LOG.warn("Validation exception processing.", e);
			context.addErrorCode(VALIDATION_EXCEPTION, e.getMessage());
		} catch (NamingException | SQLException e) {
			LOG.warn("Exception accessing the database while storing the validation response to the arrival.", e);
			context.addErrorCode(VALIDATION_EXCEPTION, e.getMessage());
		}
		
		if(context.getException().getCodes().size() > 0) {
			throw context.getException();
		} else {
			return context;
		}
	}

}
