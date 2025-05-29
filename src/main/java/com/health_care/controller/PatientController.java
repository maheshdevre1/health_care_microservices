package com.health_care.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.health_care.entity.Patient;
import com.health_care.model.PatientDetails;
import com.health_care.model.PatientModel;
import com.health_care.model.ResponseBean;
import com.health_care.service.PatientService;

@RestController
@RequestMapping("/patient")
public class PatientController {

	@Autowired
	private PatientService patientService;

	@PostMapping("/register")
	public ResponseEntity<?> registerPatient(@RequestBody PatientModel patient) {
		ResponseBean bean = patientService.registerPatient(patient);
		return ResponseEntity.ok(bean);
	}
	
	
	@PostMapping("/getAllAPatientDetails")
	public ResponseEntity<?> getAllAPatientDetails(@RequestBody PatientDetails patient) {
		ResponseBean bean = patientService.getAllAPatientDetails(patient);
		return ResponseEntity.ok(bean);
	}
	

}
