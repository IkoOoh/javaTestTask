package com.prj.testTask.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prj.testTask.dto.ErrorResponse;
import com.prj.testTask.entity.Patient;
import com.prj.testTask.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Operation(
        summary = "Get Patient by ID",
        description = "Retrieve patient details by their id",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Patient found",
            content = @Content(schema = @Schema(implementation = Patient.class))),
        @ApiResponse(description = "Error",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getPatientById(@PathVariable UUID id) {
        Patient patient = patientService.getPatientById(id);
        if (patient == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("404", "Resource not found"));
        }
        return ResponseEntity.ok(patient);
    }

    @Operation(
        summary = "Create a new patient",
        description = "Adds a new patient to the system",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Patient successfully created",
            content = @Content(schema = @Schema(implementation = Patient.class))),
        @ApiResponse(description = "Error",
        	content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<Patient> createPatient(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Patient details",
            required = true,
            content = @Content(schema = @Schema(implementation = Patient.class))
        )
        @RequestBody Patient patient) {  
        Patient savedPatient = patientService.createPatient(patient);
        return ResponseEntity.status(201).body(savedPatient);
    }

    @Operation(
        summary = "Update Patient by ID",
        description = "Update the details of an existing patient using their id",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Patient successfully updated",
            content = @Content(schema = @Schema(implementation = Patient.class))),
        @ApiResponse(description = "Error",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePatient(
        @PathVariable UUID id,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Updated patient details",
            required = true,
            content = @Content(schema = @Schema(implementation = Patient.class))
        )
        @RequestBody Patient patient 
    ) {
        Patient updatedPatient = patientService.updatePatient(id, patient);
        if (updatedPatient == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("404", "Resource not found"));
        }
        return ResponseEntity.ok(updatedPatient);
    }

    @Operation(
        summary = "Delete Patient by ID",
        description = "Attempt to delete a patient from the database by id",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Request result"),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Patient> deletePatient(@PathVariable UUID id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
    
    @Operation(
        summary = "Search Patients by Parameters",
        description = "Retrieve a list of patients by partial match of name, gender, and date of birth",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Patients found",
            content = @Content(schema = @Schema(implementation = Patient[].class))),
        @ApiResponse(description = "Error",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/search")
    public ResponseEntity<?> searchPatients(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
            List<Patient> patients = patientService.searchPatientByParams(name, gender, startDate, endDate);
            if (patients.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("404", "No patients found"));
            }
            return ResponseEntity.ok(patients);
        }
}
