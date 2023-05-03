package com.noor.user;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.Map;

@Table(name = "user")
@Document
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userID;
    @Column
    private Map<Integer, String> colsName;
    private String userName;
    private Object password;
    private String property;


    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Map<Integer, String> getColsName() {
        return colsName;
    }

    public void setColsName(Map<Integer, String> colsName) {
        this.colsName = colsName;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Object getPassword() {
        return password;
    }

    public void setPassword(Object password) {
        this.password = password;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }
}
