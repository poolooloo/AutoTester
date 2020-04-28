package com.android.factorytest;
import com.alibaba.fastjson.JSON;

public class JsonInfoCreator {
    public void createJsonInfoObj(){
        JsonInfoGroup group = new JsonInfoGroup();
        group.setId(0L);
        group.setName("admin");

        JsonInfo guestUser = new JsonInfo();
        guestUser.setId(2L);
        guestUser.setName("guest");

        JsonInfo rootUser = new JsonInfo();
        rootUser.setId(3L);
        rootUser.setName("root");

        group.addUser(guestUser);
        group.addUser(rootUser);

        String jsonString = JSON.toJSONString(group);

        System.out.println(jsonString);
    }

}

