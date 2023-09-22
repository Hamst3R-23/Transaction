package main.transaction.service;

import main.transaction.exception.HttpRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Service
public class CBRService {

    @Value("${http.request.url}")
    private String datasourceUrl;

    public String getDailyVolute() {
        try {
            java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder(new URI(datasourceUrl))
                    .timeout(Duration.of(5, ChronoUnit.SECONDS))
                    .build();

            HttpClient client = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1)
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return (response.body());
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new HttpRequestException("Http request exeption");
        }
    }
}
