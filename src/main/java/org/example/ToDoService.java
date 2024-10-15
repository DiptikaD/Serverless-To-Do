package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ToDoService {

    @Autowired
    private ToDoRepository toDoRepository;

//    public ToDo createToDo(ToDo todo){
//        todo.setId(java.util.UUID.randomUUID().toString());
//        return toDoRepository.save(todo);
//    }



    //express crud operations here: create, getall getbyid etc
}
