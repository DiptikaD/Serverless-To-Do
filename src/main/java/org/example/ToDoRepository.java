package org.example;

import software.amazon.awssdk.services.dynamodb.model.*;

import org.springframework.data.repository.CrudRepository;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


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

    public void deleteById(String id){
        DeleteItemRequest request = DeleteItemRequest.builder()
                .tableName("your_table_name")
                .key(Collections.singletonMap("id", AttributeValue.builder().s(id).build()))
                .build();

        dynamoDBClient.deleteItem(request);
    }

    public void update (ToDo toDo){
        UpdateItemRequest request = UpdateItemRequest.builder()
                .tableName("your_table_name")
                .key(Collections.singletonMap("id", AttributeValue.builder().s(toDo.getId()).build()))
                .updateExpression("SET #title =:title")
                .expressionAttributeNames(Collections.singletonMap("#name", "name"))
                .expressionAttributeValues(Collections.singletonMap(":name", AttributeValue.builder().s(toDo.getTitle()).build()))
                .build();

        dynamoDBClient.updateItem(request);
    }

    public ToDo fromAttributeMap(Map<String, AttributeValue> item){
        String id = item.get("id").s();
        String title = item.get("title").s();
        String description = item.get("description").s();
        boolean completed = Boolean.parseBoolean(item.get("completed").bool().toString());

        return new ToDo(id, title,description, completed);
    }

    public List<ToDo> findAll() {
        ScanRequest request = ScanRequest.builder()
                .tableName("your_table_name")
                .build();

        ScanResponse response = dynamoDBClient.scan(request);

        return response.items().stream()
                .map(this::fromAttributeMap)
                .collect(Collectors.toList());

    }
}
