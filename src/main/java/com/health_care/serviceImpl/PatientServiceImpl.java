package com.health_care.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health_care.entity.Patient;
import com.health_care.model.PatientDetails;
import com.health_care.model.PatientModel;
import com.health_care.model.ResponseBean;
import com.health_care.repo.PatientRepository;
import com.health_care.security.JwtTokenUtil;
import com.health_care.service.PatientService;

@Service
public class PatientServiceImpl implements PatientService {

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	public ResponseBean registerPatient(PatientModel patient) {
		ResponseBean bean = new ResponseBean();
		String email = patient.getEmail();
		Patient p = patientRepository.getPatientDetailsByEmail(email);

		if (p != null) {
			bean.setMessage("User is already exist");
			bean.setErrorCode("1");

		} else {
			Patient pat = new Patient();
			pat.setFirstName(patient.getFirstName());
			pat.setLastName(patient.getLastName());
			pat.setAddress(patient.getAddress());
			pat.setEmail(patient.getEmail());
			pat.setContactNumber(patient.getContactNumber());
			pat.setGender(patient.getGender());
			pat.setMedicalHistory(patient.getMedicalHistory());

			Patient patie = patientRepository.save(pat);

			bean.setMessage("User save successfully");
			bean.setResponse(patie);
			bean.setErrorCode("0");

		}

		return bean;
	}

	@Override
	public ResponseBean getAllAPatientDetails(PatientDetails patient) {
		ResponseBean bean = new ResponseBean();
		String type = patient.getType();
		String email = patient.getEmail();

		Object responseData = null;

		if ("A".equals(type)) {//get all details
			responseData = patientRepository.getPatientDetailsByEmail(email);
		} else if ("B".equals(type)) {//get details by email
			responseData = patientRepository.findAll();
		}else if ("C".equals("C")) {//delete by id //update the flag Y to N
			
		}

		if (responseData == null || (responseData instanceof List && ((List<?>) responseData).isEmpty())) {
			bean.setMessage("No data found");
			bean.setErrorCode("1");
		} else {
			bean.setMessage("User Details");
			bean.setResponse(responseData);
			bean.setErrorCode("0");
		}

		return bean;
	}

}
