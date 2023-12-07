package model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Catagories
{
    private StringProperty productID;
    private StringProperty name;
    private StringProperty type;
    private DoubleProperty price;
    private StringProperty status;
    public Catagories()
    {
        this.productID = new SimpleStringProperty();
        this.name = new SimpleStringProperty();
        this.type = new SimpleStringProperty();
        this.price = new SimpleDoubleProperty();
        this.status = new SimpleStringProperty();
    }
    // productID
    public String getProductID()
    {
        return productID.get();
    }
    public void setProductID(String productID)
    {
        this.productID.set(productID);
    }
    public StringProperty productIDProperty()
    {
        return productID;
    }
    // name
    public String getName()
    {
        return this.name.get();
    }
    public void setName(String name)
    {
        this.name.set(name);
    }
    public StringProperty nameProperty()
    {
        return name;
    }
    // type
    public String getType()
    {
        return this.type.get();
    }
    public void setType(String type)
    {
        this.type.set(type);
    }
    public StringProperty typeProperty()
    {
        return type;
    }
    // price
    public double getPrice()
    {
        return this.price.get();
    }
    public void setPrice(double price)
    {
        this.price.set(price);
    }
    public DoubleProperty priceProperty()
    {
        return price;
    }
    // status
    public String getStatus()
    {
        return this.status.get();
    }
    public void setStatus(String type)
    {
        this.status.set(type);
    }
    public StringProperty statusProperty()
    {
        return status;
    }
}
