package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ToDoService {

    @Autowired
    private ToDoRepository toDoRepository;

//    public ToDo createToDo(ToDo todo){
//        todo.setId(java.util.UUID.randomUUID().toString());
//        return toDoRepository.save(todo);
//    }

    public Optional<ToDo> getToDoByID(String id){
        return toDoRepository.findById(id);
    }

    public Iterable<ToDo> getAllToDo(){
        return toDoRepository.findAll();
    }

    public ToDo updateToDo(ToDo todo){
        return toDoRepository.save(todo);
    }

    public void deleteToDoByID(String id){
        toDoRepository.deleteById(id);
    }
}
