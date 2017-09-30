package pe.cibertec.cleanarquitecturedemo.domain.usercase;

import pe.cibertec.cleanarquitecturedemo.domain.executor.PostExecutionThread;
import pe.cibertec.cleanarquitecturedemo.domain.executor.ThreadExecutor;

/**
 * Created by Android on 27/05/2017.
 */
//Nos permite comunicarnos a la caapa de datos

public class UserCase<T> {

    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;
    private CallBack<T> callBack;

    public UserCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    protected void buildUserCase() {

    }

    public void execute (CallBack<T> callBack){
        this.callBack = callBack;
        threadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                buildUserCase();
            }
        });
    }

    public void cancel(){
        this.callBack = null ;
    }

    protected void notifyUseCaseSuccess(final T response){
        this.postExecutionThread.execute(new Runnable() {
            @Override
            public void run() {
                if (callBack != null){
                    callBack.onSuccess(response);
                }
            }
        });
    }

    protected void notifyUseCaseError(final Throwable e){
        this.postExecutionThread.execute(new Runnable() {
            @Override
            public void run() {
                if (callBack != null){
                    callBack.onError(e);
                }
            }
        });
    }

    public interface CallBack<T> {
            void onSuccess(T t);

            void onError(Throwable exception);
        }
}
