package pe.cibertec.trabajofinalcibertec.domain.repository;

/**
 * Created by USUARIO on 3/06/2017.
 */

public interface RepositoryCallback <T>{

    void onSuccess(T response);

    void onError(Throwable exception);
}
