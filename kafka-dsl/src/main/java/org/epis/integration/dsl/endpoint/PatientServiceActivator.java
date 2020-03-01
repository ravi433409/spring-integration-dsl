package org.epis.integration.dsl.endpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
public class PatientServiceActivator {

	private final Logger logger = LoggerFactory.getLogger(PatientServiceActivator.class);

	@ServiceActivator(inputChannel = "consumerChannel", outputChannel= "requestChannel")
	public void getPatientData(String message, @Header("CARGO_BATCH_ID") long batchId) {
		  System.out.println("success activator message");

		logger.debug("Message in Batch[" + batchId + "] is received with payload : " + message);
	}

}
