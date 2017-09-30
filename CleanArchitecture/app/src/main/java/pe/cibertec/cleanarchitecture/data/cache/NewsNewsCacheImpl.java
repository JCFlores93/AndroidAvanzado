package pe.cibertec.cleanarchitecture.data.cache;

import java.util.Arrays;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import pe.cibertec.cleanarchitecture.data.entity.NewsEntity;

/**
 * Created by Android on 27/05/2017.
 */

public class NewsNewsCacheImpl implements NewsCache {

    @Override
    public void list(Callback<List<NewsEntity>> callback) {
        final Realm realm = Realm.getDefaultInstance();
        RealmQuery<NewsEntity> query = realm.where(NewsEntity.class);
        RealmResults<NewsEntity> results = query.findAll();
        callback.onSuccess(Arrays.asList(
                results.toArray(new NewsEntity[results.size()]))
        );
    }

    @Override
    public void put(final List<NewsEntity> newsEntityList) {
        final Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                bgRealm.copyToRealmOrUpdate(newsEntityList);
            }
        });
        realm.close();
    }
}
