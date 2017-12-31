package pe.cibertec.trabajofinalcibertec.domain.executor;


import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

public class UIThread implements PostExecutionThread {

    private Handler uiHandler = new Handler(Looper.getMainLooper());

    @Override
    public void execute(@NonNull Runnable command) {
        uiHandler.post(command);
    }
}
