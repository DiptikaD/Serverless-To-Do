package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/todos")
public class ToDoController {

    @Autowired
    private ToDoService toDoService;

    @PostMapping
    public ToDo createToDo(@RequestBody ToDo todo){
        return toDoService.createToDo(todo);
    }

//    @GetMapping
//    public ResponseEntity<ToDo> getToDoById(@PathVariable String id){
//        Optional<ToDo> toDoOptional = toDoService.getToDoByID(id);
//        return toDoOptional.map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    @GetMapping
//    public Iterable<ToDo> getAllToDos(){
//        return toDoService.getAllToDo();
//    }

    @PutMapping("/{id}")
    public ResponseEntity<ToDo> updateToDo(@PathVariable String id, @RequestBody ToDo toDo){
        toDo.setId(id);
        ToDo updatedToDo = toDoService.updateToDo(toDo);
        return ResponseEntity.ok(updatedToDo);
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<ToDo> deleteToDo(@PathVariable String id){
//        toDoService.deleteToDoByID(id);
//        return ResponseEntity.noContent().build();
//    }
}
