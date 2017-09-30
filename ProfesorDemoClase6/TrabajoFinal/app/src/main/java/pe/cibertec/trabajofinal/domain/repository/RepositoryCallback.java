package pe.cibertec.trabajofinal.domain.repository;

/**
 * Created by USUARIO on 1/06/2017.
 */

public interface RepositoryCallback<T> {

    void onSuccess(T t);
    void onError(Throwable excepetion);

}
