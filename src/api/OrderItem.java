package api;

import java.io.Serializable;

/**
 * Η κλάση {@code OrderItem} αναπαριστά ένα προϊόν σε μια παραγγελία,
 * περιλαμβάνοντας την ποσότητα του προϊόντος και την τιμή του.
 *
 * Κάθε αντικείμενο {@code OrderItem} συνδυάζει ένα {@code Product} και μια ποσότητα,
 * και παρέχει υπολογισμούς για το συνολικό κόστος της παραγγελίας για αυτό το προϊόν.
 */
public class OrderItem implements Serializable {
    private Product product; // Το προϊόν της παραγγελίας
    private double quantity; // Η ποσότητα (σε τεμάχια ή κιλά)

    /**
     * Κατασκευαστής της κλάσης {@code OrderItem} που αρχικοποιεί το προϊόν και την ποσότητα.
     * Αν η ποσότητα είναι μικρότερη ή ίση με το μηδέν, θα ρίξει εξαίρεση {@code IllegalArgumentException}.
     *
     * @param product Το προϊόν που είναι μέρος της παραγγελίας.
     * @param quantity Η ποσότητα του προϊόντος (πρέπει να είναι μεγαλύτερη από το μηδέν).
     * @throws IllegalArgumentException Αν η ποσότητα είναι μικρότερη ή ίση με το μηδέν.
     */
    public OrderItem(Product product, double quantity) {
        this.product = product;
        if (quantity <= 0) {
            throw new IllegalArgumentException("Η ποσότητα πρέπει να είναι μεγαλύτερη από το μηδέν.");
        }
        this.quantity = quantity;
    }

    /**
     * Επιστρέφει το προϊόν της παραγγελίας.
     *
     * @return Το προϊόν της παραγγελίας.
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Επιστρέφει την ποσότητα του προϊόντος στην παραγγελία.
     *
     * @return Η ποσότητα του προϊόντος (σε τεμάχια ή κιλά).
     */
    public double getQuantity() {
        return quantity;
    }

    /**
     * Υπολογίζει το συνολικό κόστος για το {@code OrderItem} με βάση την τιμή του προϊόντος και την ποσότητα.
     *
     * @return Το συνολικό κόστος του προϊόντος στην παραγγελία (τιμή * ποσότητα).
     */
    public double getTotalCost() {
        return product.getPrice() * quantity;
    }
}
