package item;

import javax.persistence.*;

@Entity(name = "item")
public class Items {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    int Id;

    @Column()
    String content;
    @Column
    Object Property;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public Object getProperty() {
        return Property;
    }

    public void setProperty(Object property) {
        Property = property;
    }

    @Override
    public String toString() {
        return "Items{" +
                "Id=" + Id +
                ", content=" + content +
                ", Property=" + Property +
                '}';
    }
}
