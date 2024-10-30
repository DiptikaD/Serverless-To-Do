package org.example;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ToDo {

       private String id;
       private String task;
       private String description;
       private boolean completed;

       public ToDo(String id, String task, String description, boolean completed) {
              this.id = id;
              this.task = task;
              this.description = description;
              this.completed = completed;
       }

       public Map<String, AttributeValue> toAttributeMap(){
              Map<String, AttributeValue> attributeValueMap = new HashMap<>();

              attributeValueMap.put("id", AttributeValue.builder().s(id).build());
              attributeValueMap.put("task", AttributeValue.builder().s(task).build());
              attributeValueMap.put("description", AttributeValue.builder().s(description).build());
              attributeValueMap.put("completed",AttributeValue.builder().bool(completed).build());

              return attributeValueMap;
       }
}
