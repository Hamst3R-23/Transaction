package main.transaction.exception;

import java.io.IOException;
import java.net.URISyntaxException;

public class HttpRequestException extends RuntimeException {
    public HttpRequestException(String message) {
        super(message);
    }
}
