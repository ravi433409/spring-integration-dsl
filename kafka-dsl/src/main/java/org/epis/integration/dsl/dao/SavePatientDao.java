package org.epis.integration.dsl.dao;

import org.epis.integration.dsl.to.PatientDataTO;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

@Component
public class SavePatientDao {
	
	@ServiceActivator(inputChannel="dabaseStoreChannel")
	public void savePatiendDataToDB() {
		PatientDataTO patientData = new PatientDataTO();
				patientData.setDaysSupply("2");
		patientData.setEmployeeid("10002");
				patientData.setRxNumber("rxno1234543");
				patientData.setStorenum("abc123");
			String jsonDocument = 	patientData.toString();
			System.out.println("saving json document to mongodb database"+jsonDocument);
		
	}

}



