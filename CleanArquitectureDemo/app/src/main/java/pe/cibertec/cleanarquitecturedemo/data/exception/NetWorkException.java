package pe.cibertec.cleanarquitecturedemo.data.exception;

/**
 * Created by Android on 27/05/2017.
 */

public class NetWorkException extends Exception {
    public NetWorkException() {
    }

    public NetWorkException(String message) {
        super(message);
    }

    public NetWorkException(String message, Throwable cause) {
        super(message, cause);
    }

    public NetWorkException(Throwable cause) {
        super(cause);
    }
}
