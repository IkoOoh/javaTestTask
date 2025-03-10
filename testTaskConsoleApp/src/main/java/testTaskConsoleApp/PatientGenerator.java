package testTaskConsoleApp;

import java.time.LocalDate;

public class PatientGenerator {

	private DateGenerator dateGenerator;
	
	private NameGenerator nameGenerator;
	
	public PatientGenerator(){
		this.dateGenerator = new DateGenerator();
		this.nameGenerator = new NameGenerator();
	}
	
	public Patient getPatient(){
		Patient patient = new Patient();
		LocalDate start = LocalDate.of(1950, 1, 1);
	    LocalDate end = LocalDate.of(2024, 12, 31);
		patient.setBirthDate(dateGenerator.getRandomDate(start, end).atStartOfDay());
		String fullName = nameGenerator.getName();
	    String[] parts = fullName.split(":");
	    patient.setName(parts[0]);
	    patient.setGender(parts[1]);
		return patient;
	}
}
