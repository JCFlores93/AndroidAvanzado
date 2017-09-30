package pe.cibertec.cleanarquitecturedemo.domain.executor;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

/**
 * Created by Android on 27/05/2017.
 */

public class UIThread implements PostExecutionThread{
    //No importa donde se creó
    private final Handler uiHandler = new Handler(Looper.getMainLooper());


    @Override
    public void execute(@NonNull Runnable command) {
            this.uiHandler.post(command);
    }
}
