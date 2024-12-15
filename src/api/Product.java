package api;

public abstract class Product {

    private String id;
    private String name;
    private String description;
    private double price;
    private double quantity;
    private String category;
    private String subcategory;

    /**
     * Constructor to initialize a product
     * @param id Unique identifier
     * @param name Name of the product
     * @param description Description of the product
     * @param price Price of the product
     * @param quantity Available quantity of the product
     * @param category Category of the product
     */

    public Product(String id, String name, String description, double price, double quantity, String category, String subcategory){
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.subcategory = subcategory;
    }

    //getters
    public String getId(){return id;}
    public String getName(){return name;}
    public String getDescription(){return description;}
    public double getPrice(){return price;}
    public double getQuantity(){return quantity;}
    public String getCategory(){return category;}
    public String getSubcategory(){return subcategory;}

    //setters
    public void setId(String id){this.id = id;}
    public void setName(String name){this.name = name;}
    public void setDescription(String description){this.description = description;}
    public void setPrice(double price){this.price = price;}
    public void setQuantity(double quantity){this.quantity = quantity;}
    public void setCategory(String category){this.category = category;}
    public void setSubcategory(String subcategory){this.subcategory = subcategory;}

    public abstract void use();

    @Override
    public String toString(){
        return String.format("Product[ID=%s, Title=%s, Description=%s, Price=%.2f, Quantity=%.2f, Category=%s, Subcategory=%s]",
                id, name, description, price, quantity, category, subcategory);
    }
}
