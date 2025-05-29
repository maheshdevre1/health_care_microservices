package com.health_care.service;

import com.health_care.entity.Patient;
import com.health_care.model.PatientDetails;
import com.health_care.model.PatientModel;
import com.health_care.model.ResponseBean;

public interface PatientService {
	
	public ResponseBean registerPatient(PatientModel patient);

	public ResponseBean getAllAPatientDetails(PatientDetails patient);

}
