package pe.cibertec.trabajofinalcibertec.presentation;

import pe.cibertec.trabajofinalcibertec.presentation.model.UsersModel;

/**
 * Created by USUARIO on 3/06/2017.
 */

public interface UsersView extends LoadingView{

    void renderResponseLogin(UsersModel userModel);

}
