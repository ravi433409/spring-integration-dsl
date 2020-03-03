package org.epis.integration.dsl.splitter;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.integration.annotation.Splitter;
import org.springframework.stereotype.Component;

@Component
public class PatientDataSplitter {
	
	  @Splitter(inputChannel = "cargoGWDefaultRequestChannel", 
              outputChannel = "cargoSplitterOutputChannel")
  public Collection<String>  splitCargoList(String message) {
		  System.out.println("success split message"+message);
		  String[] patientIds = message.split(" ");
		  
		  List<String> result = Arrays.asList(patientIds);


      return result;
  }	

}
