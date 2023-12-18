package com.hospital.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.entities.Patient;
import com.hospital.services.PatientService;

@Validated
@RestController
@RequestMapping("/api/patient")
public class PatientController {

	@Autowired
	private PatientService service;
    //Adding a patient report to the DB
	@PostMapping 
	public ResponseEntity<String> savePatient(@Valid @RequestBody Patient patient) {
		Patient newPatient = new Patient();
		newPatient.setSsn(patient.getSsn());
		newPatient.setName(patient.getName());
		newPatient.setAddress(patient.getAddress());
		newPatient.setPhone(patient.getPhone());
		newPatient.setInsuranceid(patient.getInsuranceid());
		newPatient.setPcp(patient.getPcp());
		service.addPatient(newPatient);
		return new ResponseEntity<String>("Record Created Successfully", HttpStatus.CREATED);
	}

	@GetMapping //Getting a list of Patient 
	public ResponseEntity<List<Patient>> getPatient() {
		List<Patient> patients = service.getAll();
		return new ResponseEntity<List<Patient>>(patients, HttpStatus.OK);
	}

	@GetMapping("/{pcp}")//Getting a list of patient who is check by a particular physicians
	public ResponseEntity<List<Patient>> getPatientBy(@PathVariable Integer pcp) {
		List<Patient> patients = service.getByPcp(pcp);
		return new ResponseEntity<List<Patient>>(patients, HttpStatus.OK);
	}
 
	@GetMapping("/{pcp}/{ssn}")//Getting detail of patient checked by a particular physician
	public ResponseEntity<Patient> getPatientByPhysician(@PathVariable Integer pcp, @PathVariable Integer ssn ) {
		Patient patient = service.getDetailsByPcp(pcp, ssn);
			return new ResponseEntity<Patient>(patient, HttpStatus.OK);
	}

	@GetMapping("/insurance/{ssn}")//Getting the insuranceid of patient
	public ResponseEntity<Integer> getInsuranceId(@PathVariable Integer ssn) {
			Integer insurance = service.getInsuranceIdBy(ssn);
			return new ResponseEntity<Integer>(insurance, HttpStatus.OK);
	}

	@PutMapping("/address/{ssn}")//Update the address of a patient by using SSN
	public ResponseEntity<Patient> updateAddress(@PathVariable Integer ssn, @RequestBody Patient patient) {
		
			Patient patiente = service.updateAddressBy(ssn, patient.getAddress());
			return new ResponseEntity<Patient>(patiente, HttpStatus.OK);
		
	}

	@PutMapping("/phone/{ssn}")//Update the phone of a patient by using SSN
	public ResponseEntity<Patient> updatePhone(@PathVariable Integer ssn, @RequestBody Patient patient) {
		System.out.println("uodating phone");
		System.out.println(patient.getPhone());
			Patient patiente = service.updatePhoneBy(ssn, patient.getPhone());
			return new ResponseEntity<Patient>(patiente, HttpStatus.OK);
	
	}

}
