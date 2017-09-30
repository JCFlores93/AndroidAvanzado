package pe.cibertec.cleanarquitecturedemo.domain.repository;

/**
 * Created by Android on 27/05/2017.
 */

public interface RepositoryCallback <T>{

    void onSuccess(T t);

    void onError(Throwable excepetion);
}
