package com.noor.mongo;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "rec")
public class Item {
    @Id
    private String ItemId;
    private String name;

    public String getItemId() {
        return ItemId;
    }

    public void setItemId(String itemId) {
        this.ItemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
