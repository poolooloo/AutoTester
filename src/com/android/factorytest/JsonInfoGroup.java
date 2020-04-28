package com.android.factorytest;

import java.util.ArrayList;
import java.util.List;

public class JsonInfoGroup {
    private Long       id;
    private String     name;
    private List<JsonInfo> users = new ArrayList<JsonInfo>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<JsonInfo> getUsers() {
        return users;
    }

    public void setUsers(List<JsonInfo> users) {
        this.users = users;
    }

    public void addUser(JsonInfo user) {
        users.add(user);
    }
}
