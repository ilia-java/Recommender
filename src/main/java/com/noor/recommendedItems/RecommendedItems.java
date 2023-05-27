package com.noor.recommendedItems;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

@Table(name = "RecommendedItems")
@Document
public class RecommendedItems {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String dataBaseId;
    private String userId;
    private Integer count;
    private  String scenario;
    private Boolean cascadeCreate;

    public String getDataBaseId() {
        return dataBaseId;
    }

    public void setDataBaseId(String dataBaseId) {
        this.dataBaseId = dataBaseId;
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
