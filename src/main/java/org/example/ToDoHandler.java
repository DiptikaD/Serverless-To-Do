package org.example;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

public class ToDoHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private final DynamoDB dynamoBD = new DynamoDB(AmazonDynamoDBAsyncClientBuilder.defaultClient());
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
                    responseBody = getToDo();
                    break;
                case "PUT":
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

    private String getToDo() {
    }

    private String createToDo(APIGatewayProxyRequestEvent request) {
    }
}
