package org.example;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ToDoHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private final DynamoDB dynamoDB = new DynamoDB(AmazonDynamoDBAsyncClientBuilder.defaultClient());
    private final String tableName = "Todo";


    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {
        String httpMethod = request.getHttpMethod();
        String responseBody;
        int statusCode = 200;

        try {
            switch (httpMethod){
                case "POST":
                    responseBody = createToDo(request);
                    break;
                case "GET":
                    responseBody = getAllToDos();
                    break;
                case "PUT":
//                    responseBody = updateToDo();
//                case "DELETE":
//                    responseBody = deleteToDo();
                default:
                    statusCode = 400;
                    responseBody = "Unsupported Method :(";
            }
        } catch (Exception e){
            statusCode = 500;
            responseBody = "Error: " + e.getMessage();
        }

        return new APIGatewayProxyResponseEvent()
                .withStatusCode(statusCode)
                .withBody(responseBody);
    }

    private String createToDo(APIGatewayProxyRequestEvent request) throws JsonProcessingException {
    String body = request.getBody();
    ToDo todo = new ObjectMapper().readValue(body, ToDo.class);
    String id = java.util.UUID.randomUUID().toString();

    Item item = new Item()
            .withPrimaryKey("id", id)
            .withString("title", todo.getTitle())
            .withString("description", todo.getDescription())
            .withBoolean("completed", false);

    Table table = dynamoDB.getTable(tableName);
    table.putItem(item);
    return id;
    }


    private String getAllToDos() {
        List<ToDo> toDoList = new ArrayList<>();

        Table table = dynamoDB.getTable(tableName);

        ScanRequest scanRequest = new ScanRequest().withTableName(tableName);

        ScanResult result = AmazonDynamoDBAsyncClientBuilder.defaultClient().scan(scanRequest);

        for (Map<String, AttributeValue> item : result.getItems()){
            ToDo toDo = new ToDo();
            toDo.changeTitle(item.get("title").getS());
            toDo.changeDescription(item.get("description").getS());
            toDo.getCompletionStatus();

            toDoList.add(toDo);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(toDoList);
        } catch (JsonProcessingException e){
            e.printStackTrace();
        }return "Error converting to dos into JSON format!";
    }

    //other fetching operations
}
