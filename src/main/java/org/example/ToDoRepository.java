package org.example;

import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.GetItemResponse;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

import org.springframework.data.repository.CrudRepository;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.util.Collections;
import java.util.Map;


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

    public ToDo findById(String id){
        GetItemRequest request = GetItemRequest.builder()
                .tableName("your_table_name")
                .key(Collections.singletonMap("id", AttributeValue.builder().s(id).build()))
                .build();
        GetItemResponse response = dynamoDBClient.getItem(request);

        if (response.hasItem()){
            Map<String, AttributeValue> item = response.item();
            return fromAttributeMap(item);
        }
        return null;
    }

    public ToDo fromAttributeMap(Map<String, AttributeValue> item){
        String id = item.get("id").s();
        String title = item.get("title").s();
        String description = item.get("description").s();
        boolean completed = Boolean.parseBoolean(item.get("completed").bool().toString());

        return new ToDo(id, title,description, completed);
    }
}
