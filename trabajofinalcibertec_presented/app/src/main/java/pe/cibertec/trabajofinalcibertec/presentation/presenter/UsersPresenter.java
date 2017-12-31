package pe.cibertec.trabajofinalcibertec.presentation.presenter;

import android.util.Log;

import pe.cibertec.trabajofinalcibertec.data.entity.mapper.UsersEntityDataMapper;
import pe.cibertec.trabajofinalcibertec.data.repository.UsersDataRepository;
import pe.cibertec.trabajofinalcibertec.data.repository.datasource.UsersDataSourceFactory;
import pe.cibertec.trabajofinalcibertec.domain.executor.JobExecutor;
import pe.cibertec.trabajofinalcibertec.domain.executor.UIThread;
import pe.cibertec.trabajofinalcibertec.domain.model.Users;
import pe.cibertec.trabajofinalcibertec.domain.repository.UserRepository;
import pe.cibertec.trabajofinalcibertec.domain.usecase.LoginUser;
import pe.cibertec.trabajofinalcibertec.domain.usecase.UseCase;
import pe.cibertec.trabajofinalcibertec.presentation.UsersView;
import pe.cibertec.trabajofinalcibertec.presentation.model.UsersModel;
import pe.cibertec.trabajofinalcibertec.presentation.model.mapper.UsersModelDataMapper;

public class UsersPresenter extends BasePresenter<UsersView>{

    private final LoginUser loginUseCase;
    private final UsersModelDataMapper usersModelDataMapper;

    public UsersPresenter(UsersView view) {
        super(view);
        UserRepository userRepository = new UsersDataRepository(
                new UsersDataSourceFactory(view.context()),
                new UsersEntityDataMapper());
            this.loginUseCase = new LoginUser(userRepository,
                    new JobExecutor(),new UIThread());
        this.usersModelDataMapper = new UsersModelDataMapper();

        }


    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.view = null;

    }

    public void login(UsersModel usersModel){
       // showLoadingView();
        loginUseCase.setParams(usersModelDataMapper.sendItTransform(usersModel));
        loginUseCase.execute(new UseCase.Callback<Users>() {
            @Override
            public void onSuccess(Users users) {
                // hideLoadingView();
               /* renderNewsInView(newsList);*/
                Log.i("ITEMS", users.getEmail() + " items");
                Log.i("ITEMS", users.getName() + " items");
                Log.i("ITEMS", users.getUserToken() + " items");
                Log.i("ITEMS", users.getObjectId() + " items");
                renderResponseUser(users);

            }

            @Override
            public void onError(Throwable exception) {
                Log.e("NewsPresenter", "error getting news", exception);
                //hideLoadingView();
                //showErrorMessage((Exception) exception);
            }
        });

    }

    private void renderResponseUser(Users users){
        view.renderResponseLogin(usersModelDataMapper.transform(users));
    }
}
