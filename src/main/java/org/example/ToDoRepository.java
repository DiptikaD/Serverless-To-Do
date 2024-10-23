package org.example;

import org.springframework.data.repository.CrudRepository;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;


public class ToDoRepository {
    private final DynamoDbClient dynamoDBClient;

    public ToDoRepository(DynamoDbClient dynamoDBClient){
        this.dynamoDBClient = dynamoDBClient;
    }

}
