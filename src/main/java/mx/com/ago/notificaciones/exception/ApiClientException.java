package mx.com.ago.notificaciones.exception;

public class ApiClientException extends RuntimeException {
    private final int statusCode;

    public ApiClientException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public ApiClientException(String message, int statusCode, Throwable cause) {
        super(message, cause);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
