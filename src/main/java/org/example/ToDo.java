package org.example;

import javax.persistence.Id;
import lombok.Data;

@Data
public class ToDo {

       @Id
       private String id;
       private String title;
       private String description;
       private boolean completed;
}
