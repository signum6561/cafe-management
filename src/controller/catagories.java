package controller;

public class catagories 
{
    private String productID;
    private String name;
    private String type;
    private Double price;
    private String status;
    public catagories(String productID, String name, String type, Double price, String status) {
        this.productID = productID;
        this.name = name;
        this.type = type;
        this.price = price;
        this.status = status;
    }
    //#region getters and setters 
    public String getProductID() {
        return productID;
    }
    public void setProductID(String productID) {
        this.productID = productID;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    //#endregion
    


}
