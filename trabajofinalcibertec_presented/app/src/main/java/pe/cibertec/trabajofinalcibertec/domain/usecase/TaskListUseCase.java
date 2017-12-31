package pe.cibertec.trabajofinalcibertec.domain.usecase;

import java.util.List;

import pe.cibertec.trabajofinalcibertec.domain.executor.PostExecutionThread;
import pe.cibertec.trabajofinalcibertec.domain.executor.ThreadExecutor;
import pe.cibertec.trabajofinalcibertec.domain.model.Tasks;
import pe.cibertec.trabajofinalcibertec.domain.repository.RepositoryCallback;
import pe.cibertec.trabajofinalcibertec.domain.repository.TasksRepository;

/**
 * Created by Jove on 10/06/2017.
 */

public class TaskListUseCase extends UseCase<List<Tasks>> {
    private final TasksRepository tasksRepository;
    private String user_token;

    public TaskListUseCase(TasksRepository tasksRepository,
                           ThreadExecutor threadExecutor,
                           PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.tasksRepository = tasksRepository;
    }

    public void setParams(String user_token){
        this.user_token = user_token;
    }

    @Override
    protected void buildUseCase() {
        this.tasksRepository.getTaskList(user_token, new RepositoryCallback<List<Tasks>>() {
            @Override
            public void onSuccess(List<Tasks> response) {
                notifyUseCaseSuccess(response);
            }

            @Override
            public void onError(Throwable exception) {
                notifyUseCaseError(exception);
            }
        });
    }
}
