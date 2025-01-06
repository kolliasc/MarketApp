package api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

class FileHandlerTest {

    @BeforeEach
    void setUp() {
        // Clean up the files before each test, ensuring a clean slate.
        FileHandler.saveUsers(new ArrayList<>());
        FileHandler.saveProducts(new ArrayList<>());
        FileHandler.saveOrders(new ArrayList<>());
    }

    @Test
    void testLoadUsers() {
        // Add a user (Admin) and save to the file
        User user = new Admin("testAdmin", "password123");
        FileHandler.addUser(user);

        // Load the users from the file and check
        List<User> users = FileHandler.loadUsers();
        assertEquals(1, users.size());
        assertEquals("testAdmin", users.get(0).getUsername());
    }

    @Test
    void testAddUser() {
        User newUser = new Customer("newCustomer", "newPassword");
        FileHandler.addUser(newUser);

        // Load the users to verify the new user is added
        List<User> users = FileHandler.loadUsers();
        assertTrue(users.stream().anyMatch(user -> user.getUsername().equals("newCustomer")));
    }

    @Test
    void testUserExists() {
        User existingUser = new Admin("existingAdmin", "existingPass");
        FileHandler.addUser(existingUser);

        // Test userExists method
        boolean userFound = FileHandler.userExists("existingAdmin", "existingPass");
        assertTrue(userFound);

        // Test with incorrect password
        boolean userNotFound = FileHandler.userExists("existingAdmin", "wrongPass");
        assertFalse(userNotFound);
    }

    @Test
    void testLoadProducts() {
        Product product = new Product("Test Product", "A test product", "Category", "Subcategory", 10.0, 100);
        FileHandler.addProduct(product);

        // Load products and check
        List<Product> products = FileHandler.loadProducts();
        assertEquals(1, products.size());
        assertEquals("Test Product", products.get(0).getName());
    }

    @Test
    void testAddProduct() {
        Product newProduct = new Product("New Product", "A new test product", "Category", "Subcategory", 20.0, 200);
        FileHandler.addProduct(newProduct);

        // Load products and verify
        List<Product> products = FileHandler.loadProducts();
        assertTrue(products.stream().anyMatch(product -> product.getName().equals("New Product")));
    }

    @Test
    void testInitializeDefaultAdmins() {
        // Initialize default admins
        FileHandler.initializeDefaultAdmins();

        // Verify default admins
        List<User> users = FileHandler.loadUsers();
        boolean admin1Exists = users.stream().anyMatch(user -> user.getUsername().equals("admin1"));
        boolean admin2Exists = users.stream().anyMatch(user -> user.getUsername().equals("admin2"));

        assertTrue(admin1Exists);
        assertTrue(admin2Exists);
    }

    @Test
    void testInitializeDefaultCustomers() {
        // Initialize default customers
        FileHandler.initializeDefaultCustomers();

        // Verify default customers
        List<User> users = FileHandler.loadUsers();
        boolean user1Exists = users.stream().anyMatch(user -> user.getUsername().equals("user1"));
        boolean user2Exists = users.stream().anyMatch(user -> user.getUsername().equals("user2"));

        assertTrue(user1Exists);
        assertTrue(user2Exists);
    }

    @Test
    void testInitializeDefaultProducts() {
        // Initialize default products
        FileHandler.initializeDefaultProducts();

        // Verify default products
        List<Product> products = FileHandler.loadProducts();
        boolean productExists = products.stream().anyMatch(product -> product.getName().equals("Πορτοκάλια 1kg"));
        assertTrue(productExists);
    }

    @Test
    void testUpdateProductQuantity() {
        Product product = new Product("Test Product", "A product for quantity update", "Category", "Subcategory", 10.0, 100);
        FileHandler.addProduct(product);

        // Update the product quantity
        product.setQuantity(50);
        FileHandler.updateProductQuantity(product);

        // Reload and verify the updated quantity
        List<Product> products = FileHandler.loadProducts();
        assertEquals(50, products.get(0).getQuantity());
    }

    @Test
    void testSaveOrder() {
        // Δημιουργία προϊόντος για την παραγγελία
        Product product = new Product("Test Product", "A product for the order", "Category", "Subcategory", 10.0, 100);

        // Δημιουργία OrderItem για το προϊόν και την ποσότητα
        OrderItem orderItem = new OrderItem(product, 2); // 2 προϊόντα

        // Δημιουργία παραγγελίας με την λίστα των OrderItem
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);

        // Δημιουργία παραγγελίας
        Order order = new Order(orderItems);

        // Αποθήκευση της παραγγελίας
        FileHandler.saveOrder(order);

        // Φόρτωση των παραγγελιών και έλεγχος
        List<Order> orders = FileHandler.loadOrders();

        // Επαλήθευση ότι η παραγγελία αποθηκεύτηκε
        assertEquals(1, orders.size());
        assertEquals("Test Product", orders.get(0).getOrderItems().get(0).getProduct().getName());
        assertEquals(2, orders.get(0).getOrderItems().get(0).getQuantity());
        assertEquals(20.0, orders.get(0).getTotalCost(), 0.01); // 10.0 * 2 = 20.0
    }
}