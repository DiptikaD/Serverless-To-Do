package org.example;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ToDoRepository extends CrudRepository<ToDo, String> {
    List<ToDo> findByCompleted(boolean completed);
}
