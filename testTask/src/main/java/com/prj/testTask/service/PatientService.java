package com.prj.testTask.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prj.testTask.entity.Patient;
import com.prj.testTask.repository.PatientRepository;
@Service
public class PatientService {
	
	@Autowired
	private PatientRepository patientRepository;
	
	public Patient getPatientById(UUID id) {
	    return patientRepository.findById(id).orElse(null);
	}

	public Patient createPatient(Patient patient) {
	    return patientRepository.save(patient);
	}

	public Patient updatePatient(UUID id, Patient patientDetails) {
	    Patient patient = patientRepository.findById(id).orElse(null);
	    if (patient != null) {
	        patient.setName(patientDetails.getName());
	        patient.setGender(patientDetails.getGender());
	        patient.setBirthDate(patientDetails.getBirthDate());
	        return patientRepository.save(patient);
	    }
	    return null;
	}
	
	public List<Patient> searchPatientByParams(String name, String gender, LocalDateTime startDate, LocalDateTime endDate) {
        return patientRepository.findByNameAndGenderAndBirthDate(name, gender, startDate, endDate);
    }
	
	public void deletePatient(UUID id) {
	    patientRepository.deleteById(id);
	}
}
