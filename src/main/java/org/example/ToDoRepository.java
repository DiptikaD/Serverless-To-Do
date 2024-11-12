package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.dynamodb.model.*;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class ToDoRepository {
    private final DynamoDbClient dynamoDBClient;
    private final String tableName = "ToDo";

    @Autowired
    public ToDoRepository(@Qualifier("dbClient") DynamoDbClient dynamoDBClient){
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

    public Optional<ToDo> findByIdandTask(String id, String task){
        Map<String, AttributeValue> key = new HashMap<>();
        key.put("id", AttributeValue.builder().s(id).build());
        key.put("task", AttributeValue.builder().s(task).build());

        GetItemRequest request = GetItemRequest.builder()
                .tableName(tableName)
                .key(key)
                .build();
        GetItemResponse response = dynamoDBClient.getItem(request);

        if (response.hasItem()){
            Map<String, AttributeValue> item = response.item();
            return Optional.ofNullable(fromAttributeMap(item));
        }
        return Optional.empty();
    }


    public void deleteByIdandTask(String id, String task){
        Map<String, AttributeValue> key = new HashMap<>();
        key.put("id", AttributeValue.builder().s(id).build());
        key.put("task", AttributeValue.builder().s(task).build());

        DeleteItemRequest request = DeleteItemRequest.builder()
                .tableName(tableName)
                .key(key)
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
