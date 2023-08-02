package com.noor.user;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.Map;

@Table(name = "user")
@Document
public class User {
    @Id
    private String _id;
    private String userId;
    @Column
    private Map<String, String> colsName;
    private String userName;
    private String password;
    private String databaseId;


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Map<String, String> getColsName() {
        return colsName;
    }

    public void setColsName(Map<String, String> colsName) {
        this.colsName = colsName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDatabaseId() {
        return databaseId;
    }

    public void setDatabaseId(String databaseId) {
        this.databaseId = databaseId;
    }
}