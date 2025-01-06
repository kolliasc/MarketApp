package api;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Δοκιμές για την κλάση {@code OrderItem}.
 */
class OrderItemTest {

    @Test
    void testValidOrderItem() {
        // Δημιουργία ενός έγκυρου προϊόντος
        Product product = new Product("Product 1", "Description", "Category", "Subcategory", 10.0, 100);

        // Δημιουργία ενός έγκυρου OrderItem
        OrderItem orderItem = new OrderItem(product, 5); // 5 τεμάχια

        // Έλεγχος αν το OrderItem είναι σωστά αρχικοποιημένο
        assertEquals(product, orderItem.getProduct(), "Το προϊόν δεν είναι σωστό.");
        assertEquals(5, orderItem.getQuantity(), "Η ποσότητα δεν είναι σωστή.");
    }

    @Test
    void testGetTotalCost() {
        // Δημιουργία ενός έγκυρου προϊόντος
        Product product = new Product("Product 1", "Description", "Category", "Subcategory", 20.0, 100);

        // Δημιουργία ενός OrderItem
        OrderItem orderItem = new OrderItem(product, 3); // 3 τεμάχια

        // Έλεγχος αν το συνολικό κόστος είναι σωστό
        double expectedTotalCost = 20.0 * 3; // 20 * 3 = 60.0
        assertEquals(expectedTotalCost, orderItem.getTotalCost(), "Το συνολικό κόστος δεν υπολογίζεται σωστά.");
    }

    @Test
    void testInvalidQuantityZero() {
        // Δημιουργία ενός προϊόντος
        Product product = new Product("Product 1", "Description", "Category", "Subcategory", 10.0, 100);

        // Έλεγχος αν ρίχνεται εξαίρεση όταν η ποσότητα είναι μη έγκυρη (0)
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new OrderItem(product, 0); // Ποσότητα 0
        });
        assertEquals("Η ποσότητα πρέπει να είναι μεγαλύτερη από το μηδέν.", exception.getMessage(), "Η εξαίρεση δεν είναι σωστή.");
    }

    @Test
    void testInvalidQuantityNegative() {
        // Δημιουργία ενός προϊόντος
        Product product = new Product("Product 1", "Description", "Category", "Subcategory", 10.0, 100);

        // Έλεγχος αν ρίχνεται εξαίρεση όταν η ποσότητα είναι μη έγκυρη (αρνητική)
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new OrderItem(product, -1); // Ποσότητα αρνητική
        });
        assertEquals("Η ποσότητα πρέπει να είναι μεγαλύτερη από το μηδέν.", exception.getMessage(), "Η εξαίρεση δεν είναι σωστή.");
    }
}