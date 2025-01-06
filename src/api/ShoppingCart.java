package api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Η κλάση ShoppingCart αντιπροσωπεύει το καλάθι αγορών ενός χρήστη.
 * Παρέχει λειτουργίες για την προσθήκη, αφαίρεση προϊόντων, την εμφάνιση των στοιχείων του καλαθιού και τον υπολογισμό του συνολικού κόστους.
 * Είναι serializable για σκοπούς αποθήκευσης.
 */
public class ShoppingCart implements Serializable {
    private List<CartItem> cartItems;

    /**
     * Δημιουργεί ένα άδειο καλάθι αγορών.
     */
    public ShoppingCart() {
        this.cartItems = new ArrayList<>();
    }

    /**
     * Προσθέτει ένα προϊόν στο καλάθι αγορών με την καθορισμένη ποσότητα.
     * Αν το προϊόν υπάρχει ήδη στο καλάθι, ενημερώνεται η ποσότητα του προϊόντος.
     * Ελέγχεται αν υπάρχει επαρκές απόθεμα πριν από την προσθήκη.
     *
     * @param product Το προϊόν που προστίθεται στο καλάθι.
     * @param quantity Η ποσότητα του προϊόντος που προστίθεται.
     */
    public void addProduct(Product product, int quantity) {
        for (CartItem item : cartItems) {
            if (item.getProduct().getName().equals(product.getName())) {
                int newQuantity = item.getQuantity() + quantity;
                if (newQuantity <= product.getAvailableStock()) {
                    item.setQuantity(newQuantity);
                    System.out.println("Updated quantity for: " + product.getName());
                } else {
                    System.out.println("Not enough stock available.");
                }
                return;
            }
        }
        if (quantity <= product.getAvailableStock()) {
            cartItems.add(new CartItem(product, quantity));
            System.out.println("Added to cart: " + product.getName());
        } else {
            System.out.println("Not enough stock to add this product.");
        }
    }

    /**
     * Αφαιρεί την καθορισμένη ποσότητα ενός προϊόντος από το καλάθι αγορών.
     * Αν η ποσότητα φτάσει στο μηδέν, το προϊόν αφαιρείται από το καλάθι.
     *
     * @param product Το προϊόν που αφαιρείται από το καλάθι.
     * @param quantity Η ποσότητα του προϊόντος που αφαιρείται.
     * @return true αν η αφαίρεση ήταν επιτυχής, αλλιώς false.
     */
    public boolean removeProduct(Product product, int quantity) {
        for (CartItem item : cartItems) {
            if (item.getProduct().equals(product)) {
                int currentQuantity = item.getQuantity();
                if (currentQuantity >= quantity) {
                    item.setQuantity(currentQuantity - quantity);

                    if (item.getQuantity() == 0) {
                        cartItems.remove(item);
                    }
                    return true;
                }
                break;
            }
        }
        System.out.println("Αποτυχημένη αφαίρεση.");
        return false;
    }

    /**
     * Επιστρέφει τη λίστα με τα αντικείμενα του καλαθιού αγορών.
     *
     * @return Μια λίστα με τα αντικείμενα του καλαθιού.
     */
    public List<CartItem> getCartItems() {
        return cartItems;
    }

    /**
     * Καθαρίζει το καλάθι αγορών, αφαιρώντας όλα τα αντικείμενα.
     */
    public void clearCart() {
        cartItems.clear();
        System.out.println("Cart cleared.");
    }

    /**
     * Υπολογίζει και επιστρέφει το συνολικό κόστος των προϊόντων στο καλάθι αγορών.
     *
     * @return Το συνολικό κόστος των προϊόντων στο καλάθι.
     */
    public double getTotalPrice() {
        return cartItems.stream().mapToDouble(CartItem::getTotalPrice).sum();
    }
}