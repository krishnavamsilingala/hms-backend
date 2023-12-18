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
import com.hospital.entities.Procedure;
import com.hospital.services.ProcedureService;

@Validated
@RestController
@RequestMapping("api/procedure")
public class ProcedureController {

	@Autowired
	private ProcedureService service;

	@PostMapping
	public ResponseEntity<String> addTreatment(@Valid @RequestBody Procedure procedure) {
		Procedure procedure1 = new Procedure();
		procedure1.setCode(procedure.getCode());
		procedure1.setName(procedure.getName());
		procedure1.setCost(procedure.getCost());
		service.addTreatment(procedure1);
		return new ResponseEntity<String>("Record Created Successfully", HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<Procedure>> getAllProcedures() {
		List<Procedure> procedures = service.getAll();
		return new ResponseEntity<List<Procedure>>(procedures, HttpStatus.OK);
	}

	@GetMapping("/cost/{id}")
	public ResponseEntity<Procedure> getCostById(@PathVariable Integer id) {
		Procedure procedure = service.getCostBy(id);
			return ResponseEntity.ok(procedure);
	}

	@GetMapping("/cost1/{name}")
	public ResponseEntity<Procedure> getCostByName(@PathVariable String name) {
		Procedure procedure = service.getCostByName(name);
			return ResponseEntity.ok(procedure);
	}

	@PutMapping("/cost/id/{id}")
	public ResponseEntity<Procedure> updateCostById(@PathVariable Integer id, @RequestBody Procedure procedure) {
		Procedure procedure1 = service.updateCostBy(id, procedure.getCost());
			return ResponseEntity.ok(procedure1);
	}

	@PutMapping("/name/{id}")
	public ResponseEntity<Procedure> updateNameById(@PathVariable Integer id, @RequestBody Procedure procedured) {
		Procedure procedure1 = service.updateNameBy(id, procedured.getName());
			return ResponseEntity.ok(procedure1);
	}
}
