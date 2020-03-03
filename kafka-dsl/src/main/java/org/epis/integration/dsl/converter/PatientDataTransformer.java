package org.epis.integration.dsl.converter;

import org.springframework.integration.annotation.Transformer;
import org.springframework.stereotype.Component;

@Component
public class PatientDataTransformer {
	@Transformer(inputChannel = "requestChannel")
	public String transformInternationalCargo(String message) {
		System.out.println("success transformed message" + message);
		return message;
	}

}
