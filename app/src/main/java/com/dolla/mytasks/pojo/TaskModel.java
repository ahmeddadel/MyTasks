package com.dolla.mytasks.pojo;

import java.io.Serializable;

public class TaskModel implements Serializable {
    private String title;
    private String body;

    public TaskModel(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
