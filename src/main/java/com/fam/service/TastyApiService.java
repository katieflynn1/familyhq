package com.fam.service;

import org.springframework.stereotype.Service;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class TastyApiService {

    private static final String BASE_URL = "https://tasty.p.rapidapi.com";
    private static final String RAPIDAPI_KEY = "f92c49d8f5mshdaa9bab64119cefp10cafdjsna230d39abeb8";
    private static final String RAPIDAPI_HOST = "tasty.p.rapidapi.com";
    private final HttpClient client = HttpClient.newHttpClient();

    // CALL TASTYAPI
    public String callTastyApi(String path) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + path))
                .header("Accept", "application/json")
                .header("X-RapidAPI-Key", RAPIDAPI_KEY)
                .header("X-RapidAPI-Host", RAPIDAPI_HOST)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 204) {
            return "{}"; // Return an empty JSON object for 204 status code
        } else if (response.statusCode() != 200) {
            throw new Exception("Unexpected response code: " + response.statusCode());
        }

        if (path.startsWith("/recipes/get-more-info")) {
            // If the endpoint is /recipes/get-more-info, no need to extract the 'data' field from the response JSON
            return response.body();
        }

        return response.body();
    }
}