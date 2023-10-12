package main.transaction.model.exception;

public class ExceptionResponse {

    private String exception;


    public ExceptionResponse(String exception) {
        this.exception = exception;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

}
