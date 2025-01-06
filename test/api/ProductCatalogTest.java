package api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class ProductCatalogTest {

    private ProductCatalog catalog;
    private Product product1;
    private Product product2;
    private Product product3;

    @BeforeEach
    public void setUp() {
        // Δημιουργία ενός νέου καταλόγου προϊόντων
        catalog = new ProductCatalog();

        // Δημιουργία προϊόντων
        product1 = new Product("Πορτοκάλια 1kg", "Φρέσκα πορτοκάλια, ιδανικά για χυμό ή κατανάλωση.", "Φρούτα", "Εσπεριδοειδή", 1.20, 200);
        product2 = new Product("Καρότα 1kg", "Τραγανά καρότα, κατάλληλα για σαλάτες και μαγείρεμα.", "Λαχανικά", "Αγκινάρες", 1.00, 150);
        product3 = new Product("Φιλέτο Σολομού 300g", "Φρέσκος σολομός φιλέτο έτοιμος για μαγείρεμα.", "Ψάρια", "Φρέσκος", 12.00, 50);

        // Προσθήκη προϊόντων στον κατάλογο
        catalog.addProduct(product1);
        catalog.addProduct(product2);
        catalog.addProduct(product3);
    }

    @Test
    public void testAddProduct() {
        // Ελέγχουμε ότι το προϊόν προστέθηκε στον κατάλογο
        assertEquals(3, catalog.getProducts().size());
    }

    @Test
    public void testSearchByName() {
        // Αναζητούμε το προϊόν με όνομα "Πορτοκάλια 1kg"
        Product foundProduct = catalog.searchByName("Πορτοκάλια 1kg");

        // Ελέγχουμε ότι το προϊόν βρέθηκε και έχει τις σωστές πληροφορίες
        assertNotNull(foundProduct);
        assertEquals("Πορτοκάλια 1kg", foundProduct.getName());
        assertEquals("Φρέσκα πορτοκάλια, ιδανικά για χυμό ή κατανάλωση.", foundProduct.getDescription());
    }

    @Test
    public void testSearchByCategory() {
        // Αναζητούμε το προϊόν με κατηγορία "Φρούτα" και υποκατηγορία "Εσπεριδοειδή"
        Product foundProduct = catalog.searchByCategory("Φρούτα", "Εσπεριδοειδή");

        // Ελέγχουμε ότι το προϊόν βρέθηκε και έχει τις σωστές πληροφορίες
        assertNotNull(foundProduct);
        assertEquals("Πορτοκάλια 1kg", foundProduct.getName());
    }

    @Test
    public void testSearchByNameAndCategory() {
        // Αναζητούμε το προϊόν με όνομα "Φιλέτο Σολομού 300g" και κατηγορία "Ψάρια"
        Product foundProduct = catalog.searchByNameAndCategory("Φιλέτο Σολομού 300g", "Ψάρια");

        // Ελέγχουμε ότι το προϊόν βρέθηκε και έχει τις σωστές πληροφορίες
        assertNotNull(foundProduct);
        assertEquals("Φιλέτο Σολομού 300g", foundProduct.getName());
    }

    @Test
    public void testRemoveProductQuantity() {
        // Αφαίρεση 50 μονάδων από το προϊόν "Πορτοκάλια 1kg"
        boolean result = catalog.removeProductQuantity("Πορτοκάλια 1kg", 50);

        // Ελέγχουμε ότι η αφαίρεση ήταν επιτυχής
        assertTrue(result);

        // Ελέγχουμε ότι το απόθεμα του προϊόντος μειώθηκε κατά 50
        Product updatedProduct = catalog.searchByName("Πορτοκάλια 1kg");
        assertEquals(150, updatedProduct.getAvailableStock());
    }

    @Test
    public void testRemoveProductQuantityInsufficientStock() {
        // Προσπαθούμε να αφαιρέσουμε περισσότερες μονάδες από το απόθεμα
        boolean result = catalog.removeProductQuantity("Πορτοκάλια 1kg", 300);

        // Ελέγχουμε ότι η αφαίρεση απέτυχε λόγω ανεπαρκούς αποθέματος
        assertFalse(result);

        // Το απόθεμα πρέπει να παραμείνει το ίδιο
        Product updatedProduct = catalog.searchByName("Πορτοκάλια 1kg");
        assertEquals(200, updatedProduct.getAvailableStock());
    }

    @Test
    public void testRemoveNonExistentProduct() {
        // Προσπαθούμε να αφαιρέσουμε προϊόν που δεν υπάρχει στον κατάλογο
        boolean result = catalog.removeProductQuantity("Μήλα 1kg", 50);

        // Ελέγχουμε ότι η αφαίρεση απέτυχε και το προϊόν δεν βρέθηκε
        assertFalse(result);
    }
}