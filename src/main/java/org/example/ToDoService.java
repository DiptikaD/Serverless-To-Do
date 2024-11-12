package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ToDoService {

    private ToDoRepository toDoRepository;

    @Autowired
    public ToDoService (ToDoRepository inputtedRepository){
        toDoRepository = inputtedRepository;
    }

    public ToDo createToDo(ToDo todo){
        todo.setId(java.util.UUID.randomUUID().toString());
        return toDoRepository.save(todo);
    }

    public Optional<ToDo> getToDoByIDandTask(String id, String task){
        return toDoRepository.findByIdandTask(id, task);
    }

    public List<ToDo> getAllToDo(){
        return toDoRepository.findAll();
    }

    public ToDo updateToDo(ToDo todo){
        return toDoRepository.save(todo);
    }

    public void deleteToDoByIDandTask(String id, String task){
        toDoRepository.deleteByIdandTask(id, task);
    }

}
