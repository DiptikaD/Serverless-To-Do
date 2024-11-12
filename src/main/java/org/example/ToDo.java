package org.example;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class ToDo {

       @JsonProperty
       private String id;
       @JsonProperty
       private String task;
       @JsonProperty
       private String description;
       @JsonProperty
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
