package com.noor.item;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.persistence.*;
import java.util.Map;

@Table(name = "item")
@Document
public class Item {
    @Id
    private String _id;
    private String databaseId;
    private String itemId;
    private String name;
    private Map<String, String> colsName;

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

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getColsName() {
        return colsName;
    }

    public void setColsName(Map<String, String> colsName) {
        this.colsName = colsName;
    }
}
