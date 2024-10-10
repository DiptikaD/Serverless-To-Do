package org.example;

public class ToDo {

    private String title, description;
    private boolean completed;

    public ToDo(){
        title = "untitiled";
        description = "no description";
        completed = false;
    }

    public ToDo (String inputTitle, String inputDescription, Boolean inputCompleted){
        title = inputTitle;
        description = inputDescription;
        completed = inputCompleted;
    }


}
