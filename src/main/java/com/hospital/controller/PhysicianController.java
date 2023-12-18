package com.hospital.controller;
 
import java.util.List;
 
import javax.validation.Valid;

import com.hospital.entities.Patient;
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
import com.hospital.entities.Physician;
import com.hospital.services.PhysicianService;

@Validated
@RestController
@RequestMapping("api/physician")
public class PhysicianController {
 
	@Autowired
	private PhysicianService service;
 
	@PostMapping
	public ResponseEntity<Physician> savePhysician(@Valid @RequestBody Physician physician) {
		Physician physician1 = new Physician();
		physician1.setEmployeeid(physician.getEmployeeid());
		physician1.setName(physician.getName());
		physician1.setPosition(physician.getPosition());
		physician1.setSsn(physician.getSsn());
		service.registerPhysician(physician1);
		return new ResponseEntity<Physician>(physician1, HttpStatus.OK);
	}

	@GetMapping //Getting a list of Patient
	public ResponseEntity<List<Physician>> getPhysicians() {
		List<Physician> physicians = service.getAll();
		return new ResponseEntity<List<Physician>>(physicians, HttpStatus.OK);
	}
 
	@GetMapping("/name/{name}") 
	public ResponseEntity<Physician> getPhysician(@PathVariable String name) {
		Physician physician = service.getByName(name);
		return new ResponseEntity<Physician>(physician, HttpStatus.OK);
	}
 
	@GetMapping("/position/{position}") 
	public ResponseEntity<List<Physician>> getByPosition(@PathVariable("position") String position) {
		List<Physician> physician = service.getByPosition(position);
			return ResponseEntity.ok(physician);
		}
 
	@GetMapping("/{empid}") 
	public ResponseEntity<Physician> getPhysician(@PathVariable Integer empid) {
		Physician physician = service.getByEmpId(empid);
		return new ResponseEntity<Physician>(physician, HttpStatus.OK);
	}
 
	@PutMapping("/update/position/{empid}") 
	public ResponseEntity<Physician> updatePosition(@Valid @PathVariable Integer empid, @RequestBody Physician physician) {
		Physician physician1 = service.updatePosition(empid, physician.getPosition());
			return new ResponseEntity<Physician>(physician1, HttpStatus.OK);
		}
 
	@PutMapping("/update/name/{empid}") 
	public ResponseEntity<Physician> updateName(@Valid @PathVariable Integer empid, @RequestBody Physician physician) {
		Physician physician1 = service.updateName(empid, physician.getName());
			return new ResponseEntity<Physician>(physician1, HttpStatus.OK);
		}
 
	@PutMapping("/update/ssn/{empid}") 
	public ResponseEntity<Physician> updateSsn(@Valid @PathVariable Integer empid, @RequestBody Physician physician) {
		Physician physician1 = service.updateSSN(empid, physician.getSsn());
			return new ResponseEntity<Physician>(physician1, HttpStatus.OK);
	}
}