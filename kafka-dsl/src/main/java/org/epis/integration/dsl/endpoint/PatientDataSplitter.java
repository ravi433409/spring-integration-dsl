package org.epis.integration.dsl.endpoint;

import org.springframework.integration.annotation.Splitter;
import org.springframework.stereotype.Component;

@Component
public class PatientDataSplitter {
	
	  @Splitter(inputChannel = "cargoGWDefaultRequestChannel", 
              outputChannel = "cargoSplitterOutputChannel")
  public String  splitCargoList(String message) {
		  System.out.println("success split message");
      return message;
  }	

}
