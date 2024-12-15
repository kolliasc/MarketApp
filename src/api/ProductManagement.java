package api;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProductManagement {

    private List<Product> products;

    public ProductManagement(){
        products = new ArrayList<>();
    }

    /**
     *
     * @param product the product to add
     */
    public void addProduct(Product product){
        products.add(product);
        System.out.println("Product added: " + product.getName());
    }

    /**
     *
     * @param id the ID pf the product to remove
     */
    public void removeProduct(String id){
        products.removeIf(product -> product.getId().equals(id));
        System.out.println("Product with ID " + id + " removed.");
    }

    /**
     *
     * @param name the name to search for
     * @return A list of products matching the name
     */
    public List<Product> searchByName(String name){
        List<Product> result = new ArrayList<>();
        for (Product product : products){
            if (product.getName().toLowerCase().contains(name.toLowerCase())){result.add(product);}
        }
        return result;
    }

}
