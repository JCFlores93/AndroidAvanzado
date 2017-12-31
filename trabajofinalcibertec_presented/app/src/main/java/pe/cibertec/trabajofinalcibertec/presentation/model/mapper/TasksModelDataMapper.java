package pe.cibertec.trabajofinalcibertec.presentation.model.mapper;

import java.util.ArrayList;
import java.util.List;

import pe.cibertec.trabajofinalcibertec.domain.model.Tasks;
import pe.cibertec.trabajofinalcibertec.presentation.model.TaskModel;
import pe.cibertec.trabajofinalcibertec.presentation.util.Constants;

public class TasksModelDataMapper {

    public TasksModelDataMapper() {
    }

    public TaskModel transform(Tasks tasks){
        TaskModel taskModel = new TaskModel();
        taskModel.setRemember(tasks.isRemember()? Constants.RECORDAR_TRUE:Constants.RECORDAR_FALSE);
        taskModel.setDate_time(tasks.getDateTime());
        taskModel.setTitle(tasks.getTitle());
        taskModel.setId(tasks.getObjectId());
        return taskModel;
    }

    public List<TaskModel> transform(List<Tasks> usersList) {
        List<TaskModel> usersModelList = new ArrayList<>();
        for(Tasks tasks : usersList) {
            usersModelList.add(transform(tasks));
        }
        return usersModelList;
    }

    public Tasks sendItTransform(TaskModel taskModel) {
        Tasks tasks = new Tasks(String.valueOf(taskModel.getId()));
        tasks.setTitle(taskModel.getTitle());
        tasks.setDateTime(taskModel.getDate_time());
        tasks.setRemember(taskModel.getRemember().equals(Constants.RECORDAR_TRUE));

        return tasks;
    }

    public List<Tasks> sendItTransform(List<TaskModel> usersModelList) {

        List<Tasks> taskList = new ArrayList<>();
        for(TaskModel taskModel : usersModelList) {
            taskList.add(sendItTransform(taskModel));
        }
        return taskList;
    }
}
