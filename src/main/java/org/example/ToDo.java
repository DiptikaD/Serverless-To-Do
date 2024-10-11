package org.example;

import javax.persistence.Id;
import lombok.Data;

public class ToDo {

   @Data
    public class Todo {
       @Id
       private String id;
       private String title;
       private String description;
       private boolean completed;
   }
}
