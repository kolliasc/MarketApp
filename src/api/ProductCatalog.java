package api;

import java.io.*;
import java.util.*;

/**
 * Η κλάση ProductCatalog διαχειρίζεται έναν κατάλογο προϊόντων, επιτρέποντας την προσθήκη, αναζήτηση
 * και τροποποίηση των πληροφοριών των προϊόντων στον κατάλογο.
 * Είναι serializable για σκοπούς αποθήκευσης.
 */
public class ProductCatalog implements Serializable{

    private List<Product> products;

    /**
     * Δημιουργεί έναν κενό κατάλογο προϊόντων.
     */
    public ProductCatalog() {
        this.products = new ArrayList<>();
    }

    /**
     * Προσθέτει ένα προϊόν στον κατάλογο και εκτυπώνει μήνυμα επιβεβαίωσης.
     *
     * @param product Το προϊόν που προστίθεται στον κατάλογο.
     */
    public void addProduct(Product product) {
        products.add(product);
        System.out.println("Product added: " + product.getName());
    }

    /**
     * Προσθέτει ένα προϊόν στον κατάλογο χωρίς να εκτυπώνει μήνυμα επιβεβαίωσης.
     *
     * @param product Το προϊόν που προστίθεται στον κατάλογο.
     */
    public void addProductSil(Product product) {
        products.add(product);
    }

    /**
     * Αναζητά ένα προϊόν με βάση το όνομα του προϊόντος.
     *
     * @param name Το όνομα του προϊόντος που αναζητείται.
     * @return Το προϊόν που ταιριάζει με το δοθέν όνομα, ή null αν δεν βρεθεί το προϊόν.
     */
    public Product searchByName(String name) {
        for (Product product : products) {
            if (product.getName().equalsIgnoreCase(name)) {
                return product;
            }
        }
        return null;
    }

    /**
     * Αφαιρεί μια συγκεκριμένη ποσότητα από ένα προϊόν στον κατάλογο, αν υπάρχει διαθέσιμο απόθεμα.
     *
     * @param productName Το όνομα του προϊόντος που αφαιρείται.
     * @param quantity Η ποσότητα του προϊόντος που πρέπει να αφαιρεθεί.
     * @return true αν η ποσότητα αφαιρέθηκε με επιτυχία, αλλιώς false.
     */
    public boolean removeProductQuantity(String productName, int quantity) {
        for (Product product : products) {
            if (product.getName().equalsIgnoreCase(productName)) {
                if (product.getAvailableStock() >= quantity) {
                    product.setAvailableStock(product.getAvailableStock() - quantity);
                    System.out.println("Removed " + quantity + " units from product: " + productName);
                    return true;
                } else {
                    System.out.println("The requested quantity (" + quantity + ") exceeds available stock (" + product.getAvailableStock() + ").");
                    return false;
                }
            }
        }
        System.out.println("Product with name " + productName + " not found in catalog.");
        return false;
    }

    /**
     * Επιστρέφει τη λίστα με όλα τα προϊόντα του καταλόγου.
     *
     * @return Μια λίστα με όλα τα προϊόντα του καταλόγου.
     */
    public List<Product> getProducts() {
        return products;
    }

    /**
     * Αναζητά ένα προϊόν με βάση την κατηγορία και υποκατηγορία του προϊόντος.
     *
     * @param category Η κατηγορία του προϊόντος.
     * @param subcategory Η υποκατηγορία του προϊόντος.
     * @return Το προϊόν που ταιριάζει με την κατηγορία και υποκατηγορία, ή null αν δεν βρεθεί το προϊόν.
     */
    public Product searchByCategory(String category, String subcategory) {
        if (subcategory != null && !subcategory.isEmpty()) {
            for (Product product : products) {
                if (product.getCategory().equalsIgnoreCase(category) &&
                        product.getSubcategory().equalsIgnoreCase(subcategory)) {
                    return product;
                }
            }
        }
        return null;
    }

    /**
     * Αναζητά ένα προϊόν με βάση το όνομα και την κατηγορία του προϊόντος.
     *
     * @param name Το όνομα του προϊόντος.
     * @param category Η κατηγορία του προϊόντος.
     * @return Το προϊόν που ταιριάζει τόσο στο όνομα όσο και στην κατηγορία, ή null αν δεν βρεθεί το προϊόν.
     */
    public Product searchByNameAndCategory(String name, String category) {
        for (Product product : products) {
            if (product.getName().equalsIgnoreCase(name) &&
                    product.getCategory().equalsIgnoreCase(category)) {
                return product;
            }
        }
        return null;
    }
}
