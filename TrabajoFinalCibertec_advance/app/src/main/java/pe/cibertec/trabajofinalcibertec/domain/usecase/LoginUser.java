package pe.cibertec.trabajofinalcibertec.domain.usecase;

import android.util.Log;

import pe.cibertec.trabajofinalcibertec.domain.executor.PostExecutionThread;
import pe.cibertec.trabajofinalcibertec.domain.executor.ThreadExecutor;
import pe.cibertec.trabajofinalcibertec.domain.model.Users;
import pe.cibertec.trabajofinalcibertec.domain.repository.RepositoryCallback;
import pe.cibertec.trabajofinalcibertec.domain.repository.UserRepository;

/**
 * Created by USUARIO on 3/06/2017.
 */

public class LoginUser extends UseCase<Users>{

    private final UserRepository userRepository;
    private Users users;

    public LoginUser(UserRepository userRepository,
                     ThreadExecutor threadExecutor,
                     PostExecutionThread postExecutionThread
                     ) {
        super(threadExecutor, postExecutionThread);
        this.userRepository = userRepository;
    }

    public void setParams(Users users){
        this.users = users;
    }

    @Override
    protected void buildUseCase() {
        this.userRepository.userLogin(this.users, new RepositoryCallback<Users>() {
            @Override
            public void onSuccess(Users response) {
                Log.i("Response UseCase :",""+response.getEmail());
                notifyUseCaseSuccess(response);
            }

            @Override
            public void onError(Throwable exception) {
                notifyUseCaseError(exception);
            }
        });

    }
}
