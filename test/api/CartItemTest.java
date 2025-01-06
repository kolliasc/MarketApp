package api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CartItemTest {

    private Product product;
    private CartItem cartItem;

    @BeforeEach
    void setUp() {
        // Δημιουργούμε ένα παράδειγμα προϊόν για τις δοκιμές με πιο ρεαλιστικά δεδομένα
        product = new Product(
                "Φρέσκο Μήλο",               // Τίτλος
                "Φρέσκα μήλα από τη Χαλκιδική", // Περιγραφή
                "Φρούτα",                    // Κατηγορία
                "Φρούτα Εποχής",             // Υποκατηγορία
                3.50,                        // Τιμή (σε ευρώ)
                100                          // Ποσότητα (σε απόθεμα)
        );

        // Δημιουργούμε ένα παράδειγμα CartItem με 2 ποσότητες του προϊόντος
        cartItem = new CartItem(product, 2);
    }

    @Test
    void getProduct() {
        // Ελέγχουμε ότι το προϊόν επιστρέφεται σωστά
        assertEquals(product, cartItem.getProduct(), "Το προϊόν πρέπει να επιστρέφεται σωστά.");
    }

    @Test
    void getQuantity() {
        // Ελέγχουμε ότι η ποσότητα επιστρέφεται σωστά
        assertEquals(2, cartItem.getQuantity(), "Η ποσότητα πρέπει να είναι 2.");
    }

    @Test
    void setQuantity() {
        // Αλλάζουμε την ποσότητα και ελέγχουμε την αλλαγή
        cartItem.setQuantity(5);
        assertEquals(5, cartItem.getQuantity(), "Η ποσότητα πρέπει να αλλάξει σε 5.");
    }

    @Test
    void getTotalPrice() {
        // Ελέγχουμε τον συνολικό υπολογισμό τιμής
        double expectedTotal = product.getPrice() * 2;
        assertEquals(expectedTotal, cartItem.getTotalPrice(), 0.001, "Η συνολική τιμή πρέπει να είναι σωστή.");
    }
}