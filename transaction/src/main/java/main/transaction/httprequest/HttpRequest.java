package main.transaction.httprequest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class HttpRequest {

    public static String getRequest() {
        try {
            java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder(new URI("http://www.cbr.ru/scripts/XML_daily.asp"))
                    .timeout(Duration.of(5, ChronoUnit.SECONDS))
                    .build();

            HttpClient client = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1)
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return (response.body());
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
