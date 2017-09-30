package pe.cibertec.trabajofinal.data.exception;

/**
 * Created by USUARIO on 1/06/2017.
 */

public class NetworkException extends Exception{
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
