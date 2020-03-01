package org.epis.integration.dsl.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestContoller {

	@GetMapping("/employees")
	String getEmployeeData() {
		return "{\"id\":1,\"content\":\"Hello, World!\"}";
	}

}
