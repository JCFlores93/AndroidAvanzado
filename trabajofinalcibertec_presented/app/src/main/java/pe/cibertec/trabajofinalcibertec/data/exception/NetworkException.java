package pe.cibertec.trabajofinalcibertec.data.exception;

/**
 * Created by Android on 27/05/2017.
 */

public class NetworkException extends Exception {

    public NetworkException() {
    }

    public NetworkException(String message) {
        super(message);
    }

    public NetworkException(String message, Throwable cause) {
        super(message, cause);
    }

    public NetworkException(Throwable cause) {
        super(cause);
    }
}
