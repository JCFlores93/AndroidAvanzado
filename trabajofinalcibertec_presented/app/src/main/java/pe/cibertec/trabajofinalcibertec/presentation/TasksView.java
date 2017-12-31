package pe.cibertec.trabajofinalcibertec.presentation;

import java.util.List;

import pe.cibertec.trabajofinalcibertec.presentation.LoadingView;
import pe.cibertec.trabajofinalcibertec.presentation.model.TaskModel;

public interface TasksView extends LoadingView {
    void getTasks();
    void renderTasks(List<TaskModel> taskModelList);
}
