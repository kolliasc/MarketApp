package api;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class OrderHistoryTest {

    @Test
    void testAddOrder() {
        // Δημιουργία προϊόντων
        Product product1 = new Product("Product 1", "Description 1", "Category 1", "Subcategory 1", 10.0, 100);
        Product product2 = new Product("Product 2", "Description 2", "Category 2", "Subcategory 2", 15.0, 50);

        // Δημιουργία OrderItems
        OrderItem orderItem1 = new OrderItem(product1, 2); // 2 τεμάχια
        OrderItem orderItem2 = new OrderItem(product2, 1); // 1 τεμάχιο

        // Δημιουργία παραγγελίας
        List<OrderItem> orderItems = List.of(orderItem1, orderItem2);
        Order order = new Order(orderItems);

        // Δημιουργία ιστορικού παραγγελιών
        OrderHistory orderHistory = new OrderHistory();

        // Προσθήκη παραγγελίας στο ιστορικό
        orderHistory.addOrder(order);

        // Έλεγχος αν η παραγγελία προστέθηκε
        List<Order> orders = orderHistory.getOrders();
        assertEquals(1, orders.size(), "Η παραγγελία δεν προστέθηκε στο ιστορικό.");
        assertSame(order, orders.get(0), "Η παραγγελία που προστέθηκε δεν είναι σωστή.");
    }

    @Test
    void testGetOrderDescriptions() {
        // Δημιουργία προϊόντων
        Product product1 = new Product("Product 1", "Description 1", "Category 1", "Subcategory 1", 10.0, 100);
        Product product2 = new Product("Product 2", "Description 2", "Category 2", "Subcategory 2", 15.0, 50);

        // Δημιουργία OrderItems
        OrderItem orderItem1 = new OrderItem(product1, 2); // 2 τεμάχια
        OrderItem orderItem2 = new OrderItem(product2, 1); // 1 τεμάχιο

        // Δημιουργία παραγγελίας
        List<OrderItem> orderItems = List.of(orderItem1, orderItem2);
        Order order = new Order(orderItems);

        // Δημιουργία ιστορικού παραγγελιών και προσθήκη παραγγελίας
        OrderHistory orderHistory = new OrderHistory();
        orderHistory.addOrder(order);

        // Λήψη περιγραφών παραγγελιών
        List<String> descriptions = orderHistory.getOrderDescriptions();

        // Έλεγχος αν η περιγραφή είναι σωστή
        assertEquals(1, descriptions.size(), "Περιγραφή παραγγελίας δεν δημιουργήθηκε σωστά.");
        String expectedDescription = "Ημερομηνία: " + order.getOrderDate() + "\n" +
                "Προϊόντα:\n" +
                "- Product 1 (Ποσότητα: 2, Κόστος: €20.0)\n" +
                "- Product 2 (Ποσότητα: 1, Κόστος: €15.0)\n" +
                "Συνολικό Κόστος: €35.0";
        assertTrue(descriptions.get(0).contains("Ημερομηνία:"), "Η περιγραφή δεν περιλαμβάνει την ημερομηνία.");
        assertTrue(descriptions.get(0).contains("Συνολικό Κόστος: €35.0"), "Η περιγραφή δεν περιλαμβάνει το σωστό συνολικό κόστος.");
    }

    @Test
    void testEmptyOrderHistory() {
        // Δημιουργία ιστορικού παραγγελιών χωρίς παραγγελίες
        OrderHistory orderHistory = new OrderHistory();

        // Λήψη περιγραφών παραγγελιών
        List<String> descriptions = orderHistory.getOrderDescriptions();

        // Έλεγχος αν το ιστορικό είναι άδειο και επιστρέφει το σωστό μήνυμα
        assertEquals(1, descriptions.size(), "Η περιγραφή του ιστορικού δεν είναι σωστή για κενό ιστορικό.");
        assertEquals("Δεν έχετε παραγγελίες στο ιστορικό.", descriptions.get(0), "Το μήνυμα για το άδειο ιστορικό είναι λάθος.");
    }

    @Test
    void testAddMultipleOrders() {
        // Δημιουργία προϊόντων και παραγγελιών
        Product product1 = new Product("Product 1", "Description 1", "Category 1", "Subcategory 1", 10.0, 100);
        Product product2 = new Product("Product 2", "Description 2", "Category 2", "Subcategory 2", 15.0, 50);

        // Δημιουργία OrderItems
        OrderItem orderItem1 = new OrderItem(product1, 2); // 2 τεμάχια
        OrderItem orderItem2 = new OrderItem(product2, 1); // 1 τεμάχιο

        // Δημιουργία παραγγελιών
        List<OrderItem> orderItems1 = List.of(orderItem1, orderItem2);
        Order order1 = new Order(orderItems1);

        List<OrderItem> orderItems2 = List.of(orderItem1);
        Order order2 = new Order(orderItems2);

        // Δημιουργία ιστορικού παραγγελιών και προσθήκη παραγγελιών
        OrderHistory orderHistory = new OrderHistory();
        orderHistory.addOrder(order1);
        orderHistory.addOrder(order2);

        // Έλεγχος αν οι παραγγελίες προστέθηκαν σωστά
        List<Order> orders = orderHistory.getOrders();
        assertEquals(2, orders.size(), "Δεν προστέθηκαν οι παραγγελίες στο ιστορικό.");
        assertSame(order1, orders.get(0), "Η πρώτη παραγγελία δεν είναι σωστή.");
        assertSame(order2, orders.get(1), "Η δεύτερη παραγγελία δεν είναι σωστή.");
    }
}