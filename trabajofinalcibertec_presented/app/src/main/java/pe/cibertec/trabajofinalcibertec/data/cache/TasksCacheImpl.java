package pe.cibertec.trabajofinalcibertec.data.cache;

import java.util.Arrays;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import pe.cibertec.trabajofinalcibertec.data.entity.TasksEntity;
import pe.cibertec.trabajofinalcibertec.data.entity.UsersEntity;

/**
 * Created by USUARIO on 3/06/2017.
 */

public class TasksCacheImpl implements TasksCache{
    @Override
    public void list(Callback<List<TasksEntity>> callback) {
        final Realm realm = Realm.getDefaultInstance();
        RealmQuery<TasksEntity> query = realm.where(TasksEntity.class);
        RealmResults<TasksEntity> results = query.findAll();
        callback.onSuccess(Arrays.asList(
                results.toArray(new TasksEntity[results.size()])
        ));
    }

    @Override
    public void put(final List<TasksEntity> tasksEntityList) {
        final Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm bgrealm) {
                bgrealm.copyToRealmOrUpdate(tasksEntityList);
            }
        });
        realm.close();
    }
}
