package org.example;

import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.dynamodb.model.*;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ToDoRepository {
    private final DynamoDbClient dynamoDBClient;
    private final String tableName = "ToDo";

    public ToDoRepository(DynamoDbClient dynamoDBClient){
        this.dynamoDBClient = dynamoDBClient;
    }

    public ToDo save(ToDo toDo){
        PutItemRequest request = PutItemRequest.builder()
                .tableName(tableName)
                .item(toDo.toAttributeMap())
                .build();
        dynamoDBClient.putItem(request);
        return toDo;
    }

    public Optional<ToDo> findById(String id){
        GetItemRequest request = GetItemRequest.builder()
                .tableName(tableName)
                .key(Collections.singletonMap("id", AttributeValue.builder().s(id).build()))
                .build();
        GetItemResponse response = dynamoDBClient.getItem(request);

        if (response.hasItem()){
            Map<String, AttributeValue> item = response.item();
            return Optional.ofNullable(fromAttributeMap(item));
        }
        return Optional.empty();
    }

    public void deleteById(String id){
        DeleteItemRequest request = DeleteItemRequest.builder()
                .tableName(tableName)
                .key(Collections.singletonMap("id", AttributeValue.builder().s(id).build()))
                .build();

        dynamoDBClient.deleteItem(request);
    }

    public ToDo update (ToDo toDo){
        UpdateItemRequest request = UpdateItemRequest.builder()
                .tableName(tableName)
                .key(Collections.singletonMap("id", AttributeValue.builder().s(toDo.getId()).build()))
                .updateExpression("SET #task =:task, #description = :description, #completed = :completed")
                .expressionAttributeNames(Map.of("#task", "task", "#description", "description", "#completed", "completed"))
                .expressionAttributeValues(Map.of(
                        ":task", AttributeValue.builder().s(toDo.getTask()).build(),
                        ":description", AttributeValue.builder().s(toDo.getDescription()).build(),
                        ":completed", AttributeValue.builder().bool(toDo.isCompleted()).build()))
                .build();

        dynamoDBClient.updateItem(request);
        return toDo;
    }

    public ToDo fromAttributeMap(Map<String, AttributeValue> item){
        String id = item.get("id").s();
        String task = item.get("task").s();
        String description = item.get("description").s();
        boolean completed = item.get("completed").bool();

        return new ToDo(id, task,description, completed);
    }

    public List<ToDo> findAll() {
        ScanRequest request = ScanRequest.builder()
                .tableName(tableName)
                .build();

        ScanResponse response = dynamoDBClient.scan(request);

        return response.items().stream()
                .map(this::fromAttributeMap)
                .collect(Collectors.toList());

    }
}
