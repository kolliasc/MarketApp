package api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    private Product product;

    @BeforeEach
    void setUp() {
        // Δημιουργούμε ένα παράδειγμα προϊόν για τις δοκιμές
        product = new Product(
                "Φρέσκο Μήλο",           // Τίτλος
                "Φρέσκα μήλα από τη Χαλκιδική",   // Περιγραφή
                "Φρούτα",               // Κατηγορία
                "Φρούτα Εποχής",        // Υποκατηγορία
                3.50,                   // Τιμή
                100                     // Ποσότητα
        );
    }

    @Test
    void testProductName() {
        assertEquals("Φρέσκο Μήλο", product.getName(), "Το όνομα του προϊόντος πρέπει να είναι 'Φρέσκο Μήλο'.");
    }

    @Test
    void testProductCategory() {
        assertEquals("Φρούτα", product.getCategory(), "Η κατηγορία του προϊόντος πρέπει να είναι 'Φρούτα'.");
    }

    @Test
    void testProductSubCategory() {
        assertEquals("Φρούτα Εποχής", product.getSubcategory(), "Η υποκατηγορία του προϊόντος πρέπει να είναι 'Φρούτα Εποχής'.");
    }

    @Test
    void testProductPrice() {
        assertEquals(3.50, product.getPrice(), "Η τιμή του προϊόντος πρέπει να είναι 3.50.");
    }

    @Test
    void testProductQuantity() {
        assertEquals(100, product.getQuantity(), "Η ποσότητα του προϊόντος πρέπει να είναι 100.");
    }
}