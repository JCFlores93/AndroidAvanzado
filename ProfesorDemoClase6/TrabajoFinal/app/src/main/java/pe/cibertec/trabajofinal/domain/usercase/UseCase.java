package pe.cibertec.trabajofinal.domain.usercase;


import pe.cibertec.trabajofinal.domain.executor.PostExecutionThread;
import pe.cibertec.trabajofinal.domain.executor.ThreadExecutor;

/**
 * Created by Android on 27/05/2017.
 */

public abstract class UseCase<T> {

    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;
    private Callback<T> callback;

    public UseCase(ThreadExecutor threadExecutor,
                   PostExecutionThread postExecutionThread) {
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    protected abstract void buildUseCase();

    public void execute(Callback<T> callback) {
        this.callback = callback;
        threadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                buildUseCase();
            }
        });
    }

    public void cancel() {
        this.callback = null;
    }

    protected void notifyUseCaseSuccess(final T response) {
        this.postExecutionThread.execute(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onSuccess(response);
                }
            }
        });
    }

    protected void notifyUseCaseError(final Throwable e) {
        this.postExecutionThread.execute(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onError(e);
                }
            }
        });
    }

    public interface Callback<T> {

        void onSuccess(T t);

        void onError(Throwable exception);
    }
}
