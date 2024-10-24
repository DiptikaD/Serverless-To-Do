package org.example;

import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

import org.springframework.data.repository.CrudRepository;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;


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
}
