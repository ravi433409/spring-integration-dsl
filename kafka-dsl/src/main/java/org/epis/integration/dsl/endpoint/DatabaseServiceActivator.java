/*package org.epis.integration.dsl.endpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
public class DatabaseServiceActivator {
	
	private final Logger logger = LoggerFactory.getLogger(DatabaseServiceActivator.class);


    @ServiceActivator(inputChannel = "storeChannel")
    public void getCargo(String  cargoMessage, @Header("CARGO_BATCH_ID") long batchId) {
        logger.debug("Message in Batch[" + batchId + "] is received with payload : " + cargoMessage);
    }

}
*/