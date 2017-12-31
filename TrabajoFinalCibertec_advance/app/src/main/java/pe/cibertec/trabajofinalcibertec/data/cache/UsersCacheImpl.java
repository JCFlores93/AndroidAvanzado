package pe.cibertec.trabajofinalcibertec.data.cache;

import java.util.Arrays;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import pe.cibertec.trabajofinalcibertec.data.entity.UsersEntity;

/**
 * Created by USUARIO on 2/06/2017.
 */

public class UsersCacheImpl implements UsersCache {
    @Override
    public void list(Callback<List<UsersEntity>> callback) {
        final Realm realm = Realm.getDefaultInstance();
        RealmQuery<UsersEntity> query = realm.where(UsersEntity.class);
        RealmResults<UsersEntity> results = query.findAll();
        callback.onSuccess(Arrays.asList(
                results.toArray(new UsersEntity[results.size()])
        ));
    }

    @Override
    public void put(final List<UsersEntity> usersEntityList) {
        final Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(usersEntityList);
            }
        });

    }
}
