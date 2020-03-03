package org.epis.integration.dsl.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

@Component
public class PatientSoapServiceActivator {

	private final Logger logger = LoggerFactory.getLogger(PatientSoapServiceActivator.class);

//	@ServiceActivator(inputChannel = "consumerChannel")
	@ServiceActivator(inputChannel = "consumerChannel", outputChannel= "dabaseStoreChannel")
	public String getPatientData(String message) {
		  System.out.println(" SOAP Request message"+message);
		  
		  
		String response=   "SOAP Response : :  <Patient><patientlist>11172020	1070473	22	CL" +"\r\n"+
		  "11172020	1070474	23	CL" + "\r\n" +
		  "11172020	1070475	24	CL" + "\r\n" +
		  "11172020	1070476	25	CL" + "\r\n" +
		  "11172020	1070477	26	CL</patientlist></Patient>";
		
		  System.out.println("SOAP Response after parsing "+response);
		  
		  return response;


	//	logger.debug("Message in Batch[" + batchId + "] is received with payload : " + message);
	}

}
