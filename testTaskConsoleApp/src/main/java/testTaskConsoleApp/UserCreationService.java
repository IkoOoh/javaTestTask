package testTaskConsoleApp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class UserCreationService {

    public static void main(String[] args) throws Exception {
        String tokenUrl = "http://localhost:8080/auth/token";
        String patientUrl = "http://localhost:8080/patient";

        String patientName = "practitioner";
        String patientPassword = "1234";
        String patientJson = "{\"username\": \"" + patientName + "\", \"password\": \"" + patientPassword + "\"}";

        String accessToken = getAccessToken(tokenUrl, patientJson);
        if (accessToken != null) {
            System.out.println("Access Token: " + accessToken);

            String patientDataJson = "{\"name\": \"Иванов Иван Иванович\", \"gender\": \"male\", \"birthDate\": \"2024-01-13T18:25:43\"}";
            createPatient(patientUrl, accessToken, patientDataJson);
        } else {
            System.out.println("Failed to get access token");
        }
    }

    public static String getAccessToken(String urlString, String jsonInputString) throws Exception {
    	URI uri = new URI(urlString);
    	URL url = uri.toURL();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);

        try (OutputStream os = con.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                return parseAccessToken(response.toString());
            }
        } else {
            System.out.println("POST request to " + urlString + " failed with response code: " + responseCode);
            return null;
        }
    }

    public static void createPatient(String urlString, String accessToken, String jsonInputString) throws Exception {
    	URI uri = new URI(urlString);
    	URL url = uri.toURL();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Authorization", "Bearer " + accessToken);
        con.setDoOutput(true);

        try (OutputStream os = con.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_CREATED) {
            System.out.println("Patient created successfully.");
        } else {
            System.out.println("POST request to " + urlString + " failed with response code: " + responseCode);
        }
    }

    public static String parseAccessToken(String jsonResponse) {
        // Assuming the JSON response format is {"access_token": "your_access_token"}
        int startIndex = jsonResponse.indexOf(":\"") + 2;
        int endIndex = jsonResponse.indexOf("\"", startIndex);
        return jsonResponse.substring(startIndex, endIndex);
    }
}
