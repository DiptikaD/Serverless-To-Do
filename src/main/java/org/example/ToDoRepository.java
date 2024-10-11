package org.example;

import org.springframework.data.repository.CrudRepository;

public interface ToDoRepository extends CrudRepository<ToDo.Todo, String> {
    //crudrepository covers get put post delete
    // additional operation could be getallTodos
}
