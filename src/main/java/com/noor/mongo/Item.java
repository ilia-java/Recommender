package com.noor.mongo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.reflect.Array;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "rec")
public class Item {

    @Id
    private Integer ItemId;
    private String name;
    private Array colNAme;

    public Array getColNAme() {
        return colNAme;
    }

    public void setColNAme(Array colNAme) {
        this.colNAme = colNAme;
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
