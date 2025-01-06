package api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartTest {

    private ShoppingCart cart;
    private Product apple;
    private Product banana;

    @BeforeEach
    void setUp() {
        // Δημιουργία προϊόντων
        apple = new Product("Apple", "Fresh red apple", "Fruit", "Fresh", 2.5, 100);
        banana = new Product("Banana", "Fresh yellow banana", "Fruit", "Tropical", 1.8, 50);

        // Δημιουργία του καλαθιού
        cart = new ShoppingCart();
    }

    @Test
    void testAddProductNew() {
        // Προσθήκη 3 μήλων στο καλάθι
        cart.addProduct(apple, 3);

        // Ελέγχουμε αν το καλάθι περιέχει 3 μήλα
        assertEquals(3, cart.getCartItems().stream()
                .filter(item -> item.getProduct().getName().equals("Apple"))
                .findFirst().get().getQuantity());
    }

    @Test
    void testAddProductExisting() {
        // Προσθήκη 3 μήλων και 2 μπανάνες στο καλάθι
        cart.addProduct(apple, 3);
        cart.addProduct(banana, 2);

        // Ελέγχουμε ότι το καλάθι περιέχει 3 μήλα και 2 μπανάνες
        assertEquals(3, cart.getCartItems().stream()
                .filter(item -> item.getProduct().getName().equals("Apple"))
                .findFirst().get().getQuantity());
        assertEquals(2, cart.getCartItems().stream()
                .filter(item -> item.getProduct().getName().equals("Banana"))
                .findFirst().get().getQuantity());

        // Προσθήκη 2 ακόμη μήλων
        cart.addProduct(apple, 2);

        // Ελέγχουμε ότι η ποσότητα των μήλων είναι 5
        assertEquals(5, cart.getCartItems().stream()
                .filter(item -> item.getProduct().getName().equals("Apple"))
                .findFirst().get().getQuantity());
    }

    @Test
    void testAddProductNotEnoughStock() {
        // Προσθήκη 101 μήλων (υπερβαίνει το απόθεμα)
        cart.addProduct(apple, 101);

        // Ελέγχουμε ότι το καλάθι δεν περιέχει μήλα, καθώς δεν υπάρχει επαρκές απόθεμα
        assertEquals(0, cart.getCartItems().stream()
                .filter(item -> item.getProduct().getName().equals("Apple"))
                .findFirst().map(CartItem::getQuantity).orElse(0));
    }

    @Test
    void testRemoveProduct() {
        // Προσθήκη 3 μήλων στο καλάθι
        cart.addProduct(apple, 3);

        // Αφαίρεση 2 μήλων
        boolean result = cart.removeProduct(apple, 2);
        assertTrue(result);

        // Ελέγχουμε ότι το καλάθι έχει 1 μήλο
        assertEquals(1, cart.getCartItems().stream()
                .filter(item -> item.getProduct().getName().equals("Apple"))
                .findFirst().get().getQuantity());
    }

    @Test
    void testRemoveProductMoreThanAvailable() {
        // Προσθήκη 2 μήλων στο καλάθι
        cart.addProduct(apple, 2);

        // Αφαίρεση 3 μήλων (περισσότερο από την διαθέσιμη ποσότητα)
        boolean result = cart.removeProduct(apple, 3);
        assertFalse(result);  // Η αφαίρεση πρέπει να αποτύχει

        // Ελέγχουμε ότι το καλάθι περιέχει ακόμα 2 μήλα
        assertEquals(2, cart.getCartItems().stream()
                .filter(item -> item.getProduct().getName().equals("Apple"))
                .findFirst().get().getQuantity());
    }

    @Test
    void testClearCart() {
        // Προσθήκη προϊόντων στο καλάθι
        cart.addProduct(apple, 3);
        cart.addProduct(banana, 2);

        // Καθαρισμός του καλαθιού
        cart.clearCart();

        // Ελέγχουμε ότι το καλάθι είναι άδειο
        assertTrue(cart.getCartItems().isEmpty());
    }

    @Test
    void testGetTotalPrice() {
        // Προσθήκη 3 μήλων και 2 μπανάνες στο καλάθι
        cart.addProduct(apple, 3);
        cart.addProduct(banana, 2);

        // Υπολογισμός του συνολικού κόστους
        double totalPrice = cart.getTotalPrice();
        double expectedPrice = (3 * apple.getPrice()) + (2 * banana.getPrice());

        // Ελέγχουμε ότι το συνολικό κόστος είναι σωστό
        assertEquals(expectedPrice, totalPrice, 0.001);
    }
}