package testTaskConsoleApp;


public class Main {
	public static void main(String[] args) throws Exception{
		PatientGenerator patientGenerator = new PatientGenerator();
		
		String tokenUrl = "http://localhost:1234/auth/token";
        String patientUrl = "http://localhost:1234/patient";

        String name = "practitioner";
        String password = "1234";
        String userJson = "{\"username\": \"" + name + "\", \"password\": \"" + password + "\"}";
        
        String accessToken = UserCreationService.getAccessToken(tokenUrl, userJson);
        
        if (accessToken != null) {
            System.out.println("Access Token: " + accessToken);
            for (int i = 0; i < 100; i++) {
            	Patient p = patientGenerator.getPatient();

            	String patientDataJson = String.format("{\"name\": \"%s\", \"gender\": \"%s\", \"birthDate\": \"%s\"}",
            	                                        p.getName(), p.getGender(), p.getBirthDate());
            	UserCreationService.createPatient(patientUrl, accessToken, patientDataJson);
            	System.out.println(p.getName());
            	System.out.println(p.getGender());
            	System.out.println(p.getBirthDate());
    			System.out.println("-----------------------");
    		}
        } else {
            System.out.println("Failed to get access token");
        }
        
		
	}
}
