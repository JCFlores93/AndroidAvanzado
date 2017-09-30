package pe.cibertec.cleanarquitecturedemo.domain.executor;

import android.support.annotation.NonNull;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Android on 27/05/2017.
 */

public class JobExecutor implements ThreadExecutor {

    private static int NUMBER_OF_CORSES =
            Runtime.getRuntime().availableProcessors();

    public static final int KEEP_ALIVE_TIME = 10;

    public static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;

    private final ThreadPoolExecutor threadPoolExecutor;

    public JobExecutor(){
        this.threadPoolExecutor = new ThreadPoolExecutor(
                NUMBER_OF_CORSES,
                NUMBER_OF_CORSES,
                KEEP_ALIVE_TIME,
                KEEP_ALIVE_TIME_UNIT,
                new LinkedBlockingQueue<Runnable>()
        );
    }
    @Override
    public void execute(@NonNull Runnable command) {
            this.threadPoolExecutor.execute(command);
    }

}
