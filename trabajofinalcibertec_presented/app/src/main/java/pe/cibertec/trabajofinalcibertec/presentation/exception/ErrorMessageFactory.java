package pe.cibertec.trabajofinalcibertec.presentation.exception;

import android.content.Context;

import pe.cibertec.trabajofinalcibertec.R;
import pe.cibertec.trabajofinalcibertec.data.exception.NetworkException;


public class ErrorMessageFactory {

    private ErrorMessageFactory() {
    }

    public static String create(Context context, Exception exception) {
        String message = context.getString(R.string.exception_generic);

        if (exception instanceof NetworkException) {
            message = context.getString(R.string.exception_network);
        }

        return message;
    }
}
