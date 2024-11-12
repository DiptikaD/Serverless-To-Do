package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/todos")
public class ToDoController {

    private ToDoService toDoService;

    @Autowired
    public ToDoController(ToDoService inputtedToDoService){
        toDoService = inputtedToDoService;
    }

    @PostMapping
    public ResponseEntity<ToDo> createToDo(@RequestBody ToDo todo){
        ToDo createdToDo = toDoService.createToDo(todo);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdToDo);
    }

    //user MUST put in id and task name to JSON request
    //my table has a primary key AND range key/sort key, therefore there
    //must be both declared for specific request
    @GetMapping("/{id}/{task}")
    public ResponseEntity<ToDo> getToDoByIdandTask(@PathVariable("id") String id, @PathVariable("task") String task) {
        Optional<ToDo> toDoOptional = toDoService.getToDoByIDandTask(id, task);
        return toDoOptional
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<ToDo> getAllToDos(){
        return toDoService.getAllToDo();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ToDo> updateToDo(@PathVariable("id") String id, @RequestBody ToDo toDo){
        toDo.setId(id);
        ToDo updatedToDo = toDoService.updateToDo(toDo);
        return ResponseEntity.ok(updatedToDo);
    }

    @DeleteMapping("/{id}/{task}")
    public ResponseEntity<ToDo> deleteToDo(@PathVariable("id") String id, @PathVariable("task") String task){
        toDoService.deleteToDoByIDandTask(id, task);
        return ResponseEntity.noContent().build();
    }
}
