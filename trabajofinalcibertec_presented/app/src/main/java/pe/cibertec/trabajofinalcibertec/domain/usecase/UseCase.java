package pe.cibertec.trabajofinalcibertec.domain.usecase;

import pe.cibertec.trabajofinalcibertec.domain.executor.PostExecutionThread;
import pe.cibertec.trabajofinalcibertec.domain.executor.ThreadExecutor;
import retrofit2.Callback;

/**
 * Created by USUARIO on 3/06/2017.
 */

public abstract class UseCase<T>{

    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;
    private Callback<T> callback;

    public UseCase(ThreadExecutor threadExecutor,
                   PostExecutionThread postExecutionThread) {
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    protected abstract void buildUseCase();

    public void execute(final Callback<T> callback){
        this.callback = callback;
        threadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                buildUseCase();
            }
        });

    }

    protected void notifyUseCaseSuccess(final T response) {
        postExecutionThread.execute(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onSuccess(response);
                }
            }
        });
    }

    protected void notifyUseCaseError(final Throwable e) {
        postExecutionThread.execute(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onError(e);
                }
            }
        });
    }

    public interface Callback<T>{
        void onSuccess(T t);
        void onError(Throwable exception);
    }
}
