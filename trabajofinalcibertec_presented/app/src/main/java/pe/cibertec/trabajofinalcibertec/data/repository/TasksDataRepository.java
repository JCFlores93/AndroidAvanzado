package pe.cibertec.trabajofinalcibertec.data.repository;

import java.util.List;

import pe.cibertec.trabajofinalcibertec.data.entity.TasksEntity;
import pe.cibertec.trabajofinalcibertec.data.entity.mapper.TasksEntityDataMapper;
import pe.cibertec.trabajofinalcibertec.data.entity.mapper.UsersEntityDataMapper;
import pe.cibertec.trabajofinalcibertec.data.repository.datasource.DataSourceCallback;
import pe.cibertec.trabajofinalcibertec.data.repository.datasource.TasksDataSource;
import pe.cibertec.trabajofinalcibertec.data.repository.datasource.TasksDataSourceFactory;
import pe.cibertec.trabajofinalcibertec.data.repository.datasource.UsersDataSourceFactory;
import pe.cibertec.trabajofinalcibertec.domain.model.Tasks;
import pe.cibertec.trabajofinalcibertec.domain.repository.RepositoryCallback;
import pe.cibertec.trabajofinalcibertec.domain.repository.TasksRepository;

/**
 * Created by USUARIO on 4/06/2017.
 */

public class TasksDataRepository implements TasksRepository{

    private final TasksDataSourceFactory tasksDataSourceFactory;
    private final TasksEntityDataMapper tasksEntityDataMapper ;

    public TasksDataRepository(TasksDataSourceFactory tasksDataSourceFactory, TasksEntityDataMapper tasksEntityDataMapper) {
        this.tasksDataSourceFactory = tasksDataSourceFactory;
        this.tasksEntityDataMapper = tasksEntityDataMapper;
    }

    @Override
    public void getTaskList(String user_token, final RepositoryCallback<List<Tasks>> callback) {
     final TasksDataSource tasksDataSource = tasksDataSourceFactory.createNetworkDataSource();
        tasksDataSource.tasksEntityList(user_token, new DataSourceCallback<List<TasksEntity>>() {
            @Override
            public void onSuccess(List<TasksEntity> tasksEntityList) {
                callback.onSuccess(tasksEntityDataMapper.transform(tasksEntityList));
            }

            @Override
            public void onError(Throwable exception) {

            }
        });
    }

    @Override
    public void getTask(String user_token, Tasks tasks, final RepositoryCallback<Tasks> callback) {
        final TasksDataSource tasksDataSource = tasksDataSourceFactory.createNetworkDataSource();
        tasksDataSource.tasksEntity(user_token,tasksEntityDataMapper.sendItTransform(tasks), new DataSourceCallback<TasksEntity>() {
            @Override
            public void onSuccess(TasksEntity tasksEntity) {
                callback.onSuccess(tasksEntityDataMapper.transform(tasksEntity));
            }

            @Override
            public void onError(Throwable exception) {

            }
        });

    }

    @Override
    public void newTask(String user_token, Tasks tasks, final RepositoryCallback<Tasks> callback) {
        final TasksDataSource tasksDataSource = tasksDataSourceFactory.createNetworkDataSource();
        tasksDataSource.tasksEntityInsert(user_token, tasksEntityDataMapper.sendItTransform(tasks), new DataSourceCallback<TasksEntity>() {
            @Override
            public void onSuccess(TasksEntity tasksEntity) {
                callback.onSuccess(tasksEntityDataMapper.transform(tasksEntity));
            }

            @Override
            public void onError(Throwable exception) {

            }
        });
    }

    @Override
    public void updateTask(String user_token, Tasks tasks, final RepositoryCallback<Tasks> callback) {
        final TasksDataSource tasksDataSource = tasksDataSourceFactory.createNetworkDataSource();
        tasksDataSource.tasksEntityUpdate(user_token, tasksEntityDataMapper.sendItTransform(tasks), new DataSourceCallback<TasksEntity>() {
            @Override
            public void onSuccess(TasksEntity tasksEntity) {
                callback.onSuccess(tasksEntityDataMapper.transform(tasksEntity));
            }

            @Override
            public void onError(Throwable exception) {

            }
        });
    }

    @Override
    public void deleteTask(String user_token, Tasks tasks, final RepositoryCallback<Tasks> callback) {
        final TasksDataSource tasksDataSource = tasksDataSourceFactory.createNetworkDataSource();
        tasksDataSource.tasksEntityDelete(user_token, tasksEntityDataMapper.sendItTransform(tasks), new DataSourceCallback<TasksEntity>() {
            @Override
            public void onSuccess(TasksEntity tasksEntity) {
                callback.onSuccess(tasksEntityDataMapper.transform(tasksEntity));
            }

            @Override
            public void onError(Throwable exception) {

            }
        });
    }
}
