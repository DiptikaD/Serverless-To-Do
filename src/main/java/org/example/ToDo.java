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


    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getCompletionStatus(){
        return completed;
    }

    public Boolean changeCompleted(){
        completed = !completed;
        return completed;
    }

    public void changeTitle(String newTitle){
        title = newTitle;
    }

    public void changeDescription(String newDescription){
        description = newDescription;
    }

    //write tests
}
