package api;
import java.util.List;


public class Admin extends User{
    private ProductManagement productManager;

        /**
         *
         * @param username  username of Admin
         * @param password password of Admin
         * creates a ProductManagement object for managing products
         */

    public Admin(String username,String password) {
        super(username,password,"admin");
        this.productManager = new ProductManagement();
    }

        /**
         * Method to add a product
         * @param product the product which we add
         */
    public void addProduct(Product product){
        productManager.addProduct(product);
    }


        /**
         * Method to remove a product by ID
         * @param id the id of the product which we remove
         */
    public void removeProduct(String id){
        productManager.removeProduct(id);
    }

        /**
         * Method to search a product
         * @param name the name or a part of the name
         * @return the name of the product
         */
    public List<Product> searchByName(String name){
        reutrn productManager.searchByName(name);
    }
}