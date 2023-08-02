package com.noor.recommendedItems;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Table;

@Table(name = "RecommendedItems")
@Document
public class RecommendedItems {
    @Id
    private String _id;
    private String databaseId;
    private String userId;
    private Integer count;
    private String scenario;
    private Boolean cascadeCreate;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getDatabaseId() {
        return databaseId;
    }

    public void setDatabaseId(String databaseId) {
        this.databaseId = databaseId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getScenario() {
        return scenario;
    }

    public void setScenario(String scenario) {
        this.scenario = scenario;
    }

    public Boolean getCascadeCreate() {
        return cascadeCreate;
    }

    public void setCascadeCreate(Boolean cascadeCreate) {
        this.cascadeCreate = cascadeCreate;
    }
}