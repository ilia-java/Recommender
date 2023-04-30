package com.noor.item;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.Map;

@Table(name = "item")
@Document
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer ItemId;
    private String name;
    @Column
    private Map<Integer, String> colsName;


    public Map<Integer, String> getColsName() {
        return colsName;
    }

    public void setColsName(Map<Integer, String> colsName) {
        this.colsName = colsName;
    }

    public Integer getItemId() {
        return ItemId;
    }

    public void setItemId(Integer itemId) {
        ItemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
