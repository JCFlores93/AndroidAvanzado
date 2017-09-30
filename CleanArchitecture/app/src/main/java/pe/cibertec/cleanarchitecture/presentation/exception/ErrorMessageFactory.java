package pe.cibertec.cleanarchitecture.presentation.exception;

import android.content.Context;

import pe.cibertec.cleanarchitecture.R;
import pe.cibertec.cleanarchitecture.data.exception.NetworkException;

/**
 * Created by Android on 27/05/2017.
 */

public class ErrorMessageFactory {

    private ErrorMessageFactory() {
    }

    public static String create(Context context, Exception e) {
        String message = context.getString(R.string.exception_generic);
        if (e instanceof NetworkException) {
            message = context.getString(R.string.exception_network);
        }
        return message;
    }
}
