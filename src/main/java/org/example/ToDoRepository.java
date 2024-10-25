package org.example;

import software.amazon.awssdk.services.dynamodb.model.*;

import org.springframework.data.repository.CrudRepository;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;


public class ToDoRepository {
    private final DynamoDbClient dynamoDBClient;

    public ToDoRepository(DynamoDbClient dynamoDBClient){
        this.dynamoDBClient = dynamoDBClient;
    }

    public ToDo save(ToDo toDo){
        PutItemRequest request = PutItemRequest.builder()
                .tableName("your_table_name")
                .item(toDo.toAttributeMap())
                .build();
        dynamoDBClient.putItem(request);
        return toDo;
    }

    public Optional<ToDo> findById(String id){
        GetItemRequest request = GetItemRequest.builder()
                .tableName("your_table_name")
                .key(Collections.singletonMap("id", AttributeValue.builder().s(id).build()))
                .build();
        GetItemResponse response = dynamoDBClient.getItem(request);

        if (response.hasItem()){
            Map<String, AttributeValue> item = response.item();
            return Optional.ofNullable(fromAttributeMap(item));
        }
        return Optional.empty();
    }

    public ToDo fromAttributeMap(Map<String, AttributeValue> item){
        String id = item.get("id").s();
        String title = item.get("title").s();
        String description = item.get("description").s();
        boolean completed = Boolean.parseBoolean(item.get("completed").bool().toString());

        return new ToDo(id, title,description, completed);
    }
}
