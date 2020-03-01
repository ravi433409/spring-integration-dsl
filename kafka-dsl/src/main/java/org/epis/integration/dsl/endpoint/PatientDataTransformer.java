package org.epis.integration.dsl.endpoint;

import org.springframework.integration.annotation.Transformer;
import org.springframework.stereotype.Component;

@Component
public class PatientDataTransformer {
	@Transformer(inputChannel = "requestChannel")
	public String transformInternationalCargo(String message) {
		System.out.println("success transformer message" + message);
		return message;
	}

}
