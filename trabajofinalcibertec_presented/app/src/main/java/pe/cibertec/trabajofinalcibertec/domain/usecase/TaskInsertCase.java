package pe.cibertec.trabajofinalcibertec.domain.usecase;

import pe.cibertec.trabajofinalcibertec.domain.executor.PostExecutionThread;
import pe.cibertec.trabajofinalcibertec.domain.executor.ThreadExecutor;
import pe.cibertec.trabajofinalcibertec.domain.model.Tasks;
import pe.cibertec.trabajofinalcibertec.domain.repository.RepositoryCallback;
import pe.cibertec.trabajofinalcibertec.domain.repository.TasksRepository;

/**
 * Created by USUARIO on 10/06/2017.
 */

public class TaskInsertCase  extends UseCase<Tasks> {
    private final TasksRepository tasksRepository;
    private Tasks tasks;
    private String user_token;

    public TaskInsertCase( TasksRepository tasksRepository,
            ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.tasksRepository = tasksRepository;
    }

    public void setParams(String user_token,Tasks tasks){
        this.tasks = tasks;
        this.user_token = user_token;
    }


    @Override
    protected void buildUseCase() {
        this.tasksRepository.newTask(this.user_token, this.tasks, new RepositoryCallback<Tasks>() {
            @Override
            public void onSuccess(Tasks response) {
                notifyUseCaseSuccess(response);
            }

            @Override
            public void onError(Throwable exception) {
                notifyUseCaseError(exception);
            }
        });

    }
}
