package org.example;

import javax.persistence.Id;

import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ToDo {

       @Id
       private String id;
       private String title;
       private String description;
       private boolean completed;

       public Map<String, AttributeValue> toAttributeMap(){
              Map<String, AttributeValue> attributeValueMap = new HashMap<>();

              attributeValueMap.put("id", AttributeValue.builder().s(id).build());
              attributeValueMap.put("title", AttributeValue.builder().s(title).build());
              attributeValueMap.put("description", AttributeValue.builder().s(description).build());
              attributeValueMap.put("completed",AttributeValue.builder().bool(completed).build());

              return attributeValueMap;
       }
}
