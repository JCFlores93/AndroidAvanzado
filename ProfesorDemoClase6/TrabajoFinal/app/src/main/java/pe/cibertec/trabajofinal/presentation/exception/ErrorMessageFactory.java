package pe.cibertec.trabajofinal.presentation.exception;

import android.content.Context;

import pe.cibertec.trabajofinal.R;
import pe.cibertec.trabajofinal.data.exception.NetworkException;

/**
 * Created by USUARIO on 1/06/2017.
 */

public class ErrorMessageFactory {

    private ErrorMessageFactory(){}

    public static String create(Context context,Exception e){
        String message = context.getString(R.string.exception_generic);
        if (e instanceof NetworkException) {
            message = context.getString(R.string.exception_network);
        }
        return message;
    }
}
