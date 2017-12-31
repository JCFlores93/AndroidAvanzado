package pe.cibertec.trabajofinalcibertec.data.entity.mapper;

import java.util.ArrayList;
import java.util.List;

import pe.cibertec.trabajofinalcibertec.data.entity.TasksEntity;
import pe.cibertec.trabajofinalcibertec.data.entity.UsersEntity;
import pe.cibertec.trabajofinalcibertec.domain.model.Tasks;
import pe.cibertec.trabajofinalcibertec.domain.model.Users;

/**
 * Created by USUARIO on 3/06/2017.
 */

public class TasksEntityDataMapper {

    public TasksEntityDataMapper() {
    }

    public Tasks transform(TasksEntity usersEntity) {
        Tasks tasks = new Tasks();
        tasks.setObjectId(usersEntity.getObjectId());
        tasks.setTitle(usersEntity.getTitle());
        tasks.setRemember(usersEntity.isRemember());
        tasks.setDateTime(usersEntity.getDateTime());
        return tasks;
    }


    public List<Tasks> transform(List<TasksEntity> tasksEntityList) {
        List<Tasks> tasksList = new ArrayList<>();
        for (TasksEntity tasksEntity : tasksEntityList) {
            tasksList.add(transform(tasksEntity));
        }
        return tasksList;
    }

    //Así cambiamos la data obtenida puesto que estamos enviando toda la clase y no sólo parámetos
    public TasksEntity sendItTransform(Tasks tasks) {
        TasksEntity tasksEntity = new TasksEntity();
        tasksEntity.setObjectId(tasks.getObjectId());
        tasksEntity.setTitle(tasks.getTitle());
        tasksEntity.setRemember(tasks.isRemember());
        tasksEntity.setDateTime(tasks.getDateTime());
        return tasksEntity;
    }
    //De igual manera pasamos la lista a Entity
    public List<TasksEntity> sendItTransform(List<Tasks> tasksList) {
        List<TasksEntity> tasksEntityList = new ArrayList<>();
        for (Tasks tasks: tasksList) {
            tasksEntityList.add(sendItTransform(tasks));
        }
        return tasksEntityList;
    }
}
