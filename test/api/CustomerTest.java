package api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class CustomerTest {

    private ProductCatalog productCatalog;
    private Customer customer;

    @BeforeEach
    void setUp() {
        // Δημιουργία ενός νέου καταλόγου προϊόντων
        productCatalog = new ProductCatalog();

        // Προσθήκη προϊόντων στον κατάλογο
        Product product1 = new Product(
                "Apple",
                "Fresh red apple",
                "Fruit",
                "Fresh",
                2.5,
                100
        );
        Product product2 = new Product(
                "Banana",
                "Fresh yellow banana",
                "Fruit",
                "Tropical",
                1.8,
                50
        );

        productCatalog.addProduct(product1);
        productCatalog.addProduct(product2);

        // Δημιουργία ενός πελάτη
        customer = new Customer("john_doe", "password123");
    }

    @Test
    void testAddToCart() {
        // Προσθήκη προϊόντων στο καλάθι του πελάτη
        customer.addToCart(productCatalog.searchByName("Apple"), 3);
        customer.addToCart(productCatalog.searchByName("Banana"), 5);

        // Ελέγχουμε αν τα προϊόντα είναι στο καλάθι
        List<CartItem> cartItems = customer.getCart().getCartItems();  // Προσπέλαση του καλαθιού μέσω του πεδίου cart

        // Διασφαλίζουμε ότι το καλάθι περιέχει 2 προϊόντα
        assertEquals(2, cartItems.size(), "Το καλάθι πρέπει να περιέχει 2 προϊόντα.");
        assertEquals(3, cartItems.get(0).getQuantity(), "Η ποσότητα του μήλου στο καλάθι πρέπει να είναι 3.");
        assertEquals(5, cartItems.get(1).getQuantity(), "Η ποσότητα της μπανάνας στο καλάθι πρέπει να είναι 5.");
    }

    @Test
    void testRemoveFromCart() {
        // Δημιουργία καταλόγου προϊόντων και προσθήκη τους
        ProductCatalog productCatalog = new ProductCatalog();
        Product apple = new Product("Apple", "Fresh red apple", "Fruit", "Fresh", 2.5, 100);
        Product banana = new Product("Banana", "Fresh yellow banana", "Fruit", "Tropical", 1.8, 50);

        productCatalog.addProduct(apple);
        productCatalog.addProduct(banana);

        // Δημιουργία πελάτη με τον κατάλογο προϊόντων
        Customer customer = new Customer("john_doe", "password123");

        // Προσθήκη προϊόντων στο καλάθι
        customer.addToCart(apple,3);   // Προσθήκη Apple
        customer.addToCart(banana, 5);   // Προσθήκη Banana

        // Έλεγχος πριν την αφαίρεση προϊόντων
        assertEquals(2, customer.getCart().getCartItems().size());  // Καλάθι περιέχει 2 προϊόντα (apple & banana)

        // Αφαίρεση 2 προϊόντων Apple από το καλάθι
        customer.removeFromCart(apple,2);  // Αφαίρεση 1 Apple

        // Έλεγχος μετά την αφαίρεση
        assertEquals(2, customer.getCart().getCartItems().size());  // Καλάθι πρέπει να περιέχει 2 προϊόντα (5 Banana και 1 Apple)

        long appleCount = customer.getCart().getCartItems().stream().filter(p -> p.getProduct().getName().equals("Apple")).count();
        long bananaCount = customer.getCart().getCartItems().stream().filter(p -> p.getProduct().getName().equals("Banana")).count();

        assertEquals(1, appleCount);  // 1 Apple πρέπει να παραμείνει
        assertEquals(5, bananaCount); // 5 Banana πρέπει να παραμείνουν
    }

    @Test
    void testCompleteOrder() {
        // Προσθήκη προϊόντος στο καλάθι
        customer.addToCart(productCatalog.searchByName("Apple"), 3);
        customer.addToCart(productCatalog.searchByName("Banana"), 5);

        // Ολοκλήρωση παραγγελίας
        customer.completeOrder();

        // Ελέγχουμε ότι το καλάθι έχει αδειάσει
        assertTrue(customer.getCart().getCartItems().isEmpty(), "Το καλάθι πρέπει να είναι άδειο μετά την ολοκλήρωση της παραγγελίας.");

        // Ελέγχουμε ότι το ιστορικό παραγγελιών δεν είναι άδειο
        assertFalse(customer.getOrderHistory().getOrders().isEmpty(), "Το ιστορικό παραγγελιών δεν πρέπει να είναι άδειο.");
    }

    @Test
    void testRemoveProductFromCatalog() {
        // Προσθήκη προϊόντος στον κατάλογο
        productCatalog.addProduct(new Product(
                "Grapes",
                "Fresh green grapes",
                "Fruit",
                "Seedless",
                3.0,
                200
        ));

        // Αφαίρεση προϊόντος από τον κατάλογο
        boolean result = productCatalog.removeProductQuantity("Grapes", 50);

        // Ελέγχουμε αν το προϊόν αφαιρέθηκε με επιτυχία
        assertTrue(result, "Η αφαίρεση προϊόντος πρέπει να είναι επιτυχής.");
    }
}