package org.epis.integration.dsl.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestContoller {

	@GetMapping("/rxData")
	String getEmployeeData() {
		return "{employeeid=10002, rxNumber=rxno1234543, storenum=abc123, daysSupply=2}";
	}

}
