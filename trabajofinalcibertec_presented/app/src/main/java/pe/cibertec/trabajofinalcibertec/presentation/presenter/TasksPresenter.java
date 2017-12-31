package pe.cibertec.trabajofinalcibertec.presentation.presenter;

import android.util.Log;

import java.util.List;

import pe.cibertec.trabajofinalcibertec.data.entity.mapper.TasksEntityDataMapper;
import pe.cibertec.trabajofinalcibertec.data.repository.TasksDataRepository;
import pe.cibertec.trabajofinalcibertec.data.repository.datasource.TasksDataSourceFactory;
import pe.cibertec.trabajofinalcibertec.domain.executor.JobExecutor;
import pe.cibertec.trabajofinalcibertec.domain.executor.UIThread;
import pe.cibertec.trabajofinalcibertec.domain.model.Tasks;
import pe.cibertec.trabajofinalcibertec.domain.repository.TasksRepository;
import pe.cibertec.trabajofinalcibertec.domain.usecase.TaskDeleteCase;
import pe.cibertec.trabajofinalcibertec.domain.usecase.TaskInsertCase;
import pe.cibertec.trabajofinalcibertec.domain.usecase.TaskListUseCase;
import pe.cibertec.trabajofinalcibertec.domain.usecase.TaskUpdateCase;
import pe.cibertec.trabajofinalcibertec.domain.usecase.UseCase;
import pe.cibertec.trabajofinalcibertec.presentation.TasksView;
import pe.cibertec.trabajofinalcibertec.presentation.model.TaskModel;
import pe.cibertec.trabajofinalcibertec.presentation.model.mapper.TasksModelDataMapper;

/**
 * Created by Jove on 10/06/2017.
 */

public class TasksPresenter extends BasePresenter<TasksView> {
    private final TaskListUseCase taskListUseCase;
    private final TasksModelDataMapper tasksModelDataMapper;
    private final TaskDeleteCase taskDeleteCase;
    private final TaskUpdateCase taskUpdateCase;
    private final TaskInsertCase taskInsertCase;

    public TasksPresenter(TasksView view) {
        super(view);


        TasksRepository tasksRepository = new TasksDataRepository(
                new TasksDataSourceFactory(view.context()),
                new TasksEntityDataMapper());
        this.taskListUseCase = new TaskListUseCase(tasksRepository, new JobExecutor(), new UIThread());
        this.tasksModelDataMapper = new TasksModelDataMapper();
        this.taskDeleteCase = new TaskDeleteCase(tasksRepository,new JobExecutor(),new UIThread());
        this.taskUpdateCase = new TaskUpdateCase(tasksRepository,new JobExecutor(),new UIThread());
        this.taskInsertCase =new  TaskInsertCase(tasksRepository,new JobExecutor(),new UIThread());
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

    public void listar(String user_token){
        showLoadingView();
        taskListUseCase.setParams(user_token);
        taskListUseCase.execute(new UseCase.Callback<List<Tasks>>() {
            @Override
            public void onSuccess(List<Tasks> tasks) {
                hideLoadingView();
                renderResponseTasks(tasks);
            }

            @Override
            public void onError(Throwable exception) {
                Log.e("NewsPresenter", "error getting tasks", exception);
                hideLoadingView();
                //showErrorMessage((Exception) exception);
            }
        });
    }
    public void eliminar(String user, TaskModel tasks){
        taskDeleteCase.setParams(user,tasksModelDataMapper.sendItTransform(tasks));
        taskDeleteCase.execute(new UseCase.Callback<Tasks>() {
            @Override
            public void onSuccess(Tasks tasks) {
                //hideLoadingView();
            }

            @Override
            public void onError(Throwable exception) {
                Log.e("NewsPresenter", "error getting tasks", exception);
                //hideLoadingView();
                //showErrorMessage((Exception) exception);
            }
        });

    }

    public void update(String user_token, TaskModel tasks){
        taskUpdateCase.setParams(user_token,tasksModelDataMapper.sendItTransform(tasks));
        taskUpdateCase.execute(new UseCase.Callback<Tasks>() {
            @Override
            public void onSuccess(Tasks tasks) {

            }

            @Override
            public void onError(Throwable exception) {
                Log.e("NewsPresenter", "error getting tasks", exception);
            }
        });

    }

    public void insert(String user_token, TaskModel tasks){
        taskInsertCase.setParams(user_token,tasksModelDataMapper.sendItTransform(tasks));
        taskInsertCase.execute(new UseCase.Callback<Tasks>() {
            @Override
            public void onSuccess(Tasks tasks) {

            }

            @Override
            public void onError(Throwable exception) {
                Log.e("NewsPresenter", "error getting tasks", exception);
            }
        });

    }

    public void renderResponseTasks(List<Tasks> tasksList){
        view.renderTasks(tasksModelDataMapper.transform(tasksList));
    }

    private void showLoadingView() {
        view.showLoading();
    }

    private void hideLoadingView() {
        view.hideLoading();
    }
}