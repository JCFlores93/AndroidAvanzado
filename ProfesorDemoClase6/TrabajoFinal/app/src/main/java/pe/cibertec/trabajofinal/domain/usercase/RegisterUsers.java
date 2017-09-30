package pe.cibertec.trabajofinal.domain.usercase;

import pe.cibertec.trabajofinal.domain.executor.PostExecutionThread;
import pe.cibertec.trabajofinal.domain.executor.ThreadExecutor;
import pe.cibertec.trabajofinal.domain.model.Users;

/**
 * Created by USUARIO on 1/06/2017.
 */

public class RegisterUsers extends UseCase<Users>{


    public RegisterUsers(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
    }

    @Override
    protected void buildUseCase() {

    }
}
