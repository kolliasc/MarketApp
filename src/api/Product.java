package api;

import java.io.Serializable;

/**
 * Η κλάση {@code Product} αναπαριστά ένα προϊόν με τα χαρακτηριστικά του, όπως
 * τίτλο, περιγραφή, κατηγορία, υποκατηγορία, τιμή και ποσότητα.
 *
 * Κάθε αντικείμενο {@code Product} περιλαμβάνει όλες τις βασικές πληροφορίες
 * που απαιτούνται για να προσδιορίσει ένα προϊόν, και παρέχει μεθόδους
 * για να αποκτήσει ή να τροποποιήσει αυτές τις πληροφορίες.
 */
public class Product implements Serializable {
    private String title;
    private String description;
    private String category;
    private String subCategory;
    private double price;
    private int quantity;

    /**
     * Κατασκευαστής της κλάσης {@code Product}, που αρχικοποιεί όλα τα πεδία του προϊόντος.
     *
     * @param title Ο τίτλος του προϊόντος.
     * @param description Η περιγραφή του προϊόντος.
     * @param category Η κατηγορία του προϊόντος.
     * @param subCategory Η υποκατηγορία του προϊόντος.
     * @param price Η τιμή του προϊόντος.
     * @param quantity Η ποσότητα του προϊόντος στην αποθήκη.
     */
    public Product(String title, String description, String category, String subCategory, double price, int quantity) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.subCategory = subCategory;
        this.price = price;
        this.quantity = quantity;
    }

    /**
     * Επιστρέφει το όνομα του προϊόντος.
     *
     * @return Το όνομα του προϊόντος.
     */
    public String getName() {
        return title;
    }

    /**
     * Επιστρέφει την περιγραφή του προϊόντος.
     *
     * @return Η περιγραφή του προϊόντος.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Επιστρέφει την κατηγορία του προϊόντος.
     *
     * @return Η κατηγορία του προϊόντος.
     */
    public String getCategory() {
        return category;
    }

    /**
     * Επιστρέφει την υποκατηγορία του προϊόντος.
     *
     * @return Η υποκατηγορία του προϊόντος.
     */
    public String getSubcategory() {
        return subCategory;
    }

    /**
     * Επιστρέφει την τιμή του προϊόντος.
     *
     * @return Η τιμή του προϊόντος.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Επιστρέφει την διαθέσιμη ποσότητα του προϊόντος στην αποθήκη.
     *
     * @return Η διαθέσιμη ποσότητα του προϊόντος.
     */
    public int getQuantity() {
        return quantity;
    }

    public int getAvailableStock() {
        return quantity;
    }

    /**
     * Ρυθμίζει τον τίτλο του προϊόντος.
     *
     * @param text Ο τίτλος του προϊόντος.
     */
    public void setName(String text) {
        this.title = title;
    }

    /**
     * Ρυθμίζει την περιγραφή του προϊόντος.
     *
     * @param description Η περιγραφή του προϊόντος.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Ρυθμίζει την κατηγορία του προϊόντος.
     *
     * @param category Η κατηγορία του προϊόντος.
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Ρυθμίζει την υποκατηγορία του προϊόντος.
     *
     * @param subCategory Η υποκατηγορία του προϊόντος.
     */
    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    /**
     * Ρυθμίζει την τιμή του προϊόντος.
     *
     * @param price Η τιμή του προϊόντος.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Ρυθμίζει την διαθέσιμη ποσότητα του προϊόντος στην αποθήκη.
     *
     * @param quantity Η διαθέσιμη ποσότητα του προϊόντος.
     */
    public void setQuantity(int quantity) {this.quantity = quantity;}
    public void setAvailableStock(int quantity) {this.quantity = quantity;}
}
