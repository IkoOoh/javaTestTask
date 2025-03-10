package com.prj.testTask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.prj.testTask.entity.Patient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface PatientRepository extends JpaRepository<Patient, UUID> {
	@Query("SELECT p FROM Patient p WHERE " +
		       "(LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')) OR :name IS NULL) AND " +
		       "(p.gender = :gender OR :gender IS NULL) AND " +
		       "(p.birthDate BETWEEN COALESCE(:startDate, cast('1900-01-01T00:00:00' as timestamp)) AND COALESCE(:endDate, cast('9999-12-31T23:59:59' as timestamp)))")
		List<Patient> findByNameAndGenderAndBirthDate(
		        @Param("name") String name,
		        @Param("gender") String gender,
		        @Param("startDate") LocalDateTime startDate,
		        @Param("endDate") LocalDateTime endDate);

}
