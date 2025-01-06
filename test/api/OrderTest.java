package api;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.time.*;

class OrderTest {

    @Test
    void testCreateOrder() {
        // Δημιουργία προϊόντος
        Product product1 = new Product("Test Product 1", "Description of Product 1", "Category 1", "Subcategory 1", 10.0, 100);
        Product product2 = new Product("Test Product 2", "Description of Product 2", "Category 2", "Subcategory 2", 15.0, 50);

        // Δημιουργία OrderItem για τα προϊόντα
        OrderItem orderItem1 = new OrderItem(product1, 2); // 2 προϊόντα του Test Product 1
        OrderItem orderItem2 = new OrderItem(product2, 1); // 1 προϊόν του Test Product 2

        // Δημιουργία λίστας με OrderItem
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem1);
        orderItems.add(orderItem2);

        // Δημιουργία παραγγελίας με τα προϊόντα
        Order order = new Order(orderItems);

        // Έλεγχος ημερομηνίας παραγγελίας (θα πρέπει να είναι η τρέχουσα ημερομηνία)
        assertNotNull(order.getOrderDate());
        assertEquals(LocalDate.now(), order.getOrderDate(), "Η ημερομηνία παραγγελίας πρέπει να είναι η τρέχουσα ημερομηνία");

        // Έλεγχος αριθμού προϊόντων στην παραγγελία
        assertEquals(2, order.getOrderItems().size(), "Η παραγγελία πρέπει να περιέχει 2 προϊόντα");

        // Έλεγχος συνολικού κόστους (10.0 * 2 + 15.0 * 1 = 35.0)
        assertEquals(35.0, order.getTotalCost(), 0.01, "Το συνολικό κόστος πρέπει να είναι 35.0");
    }

    @Test
    void testTotalCostCalculation() {
        // Δημιουργία προϊόντος και OrderItem
        Product product1 = new Product("Product 1", "Description", "Category", "Subcategory", 20.0, 10);
        OrderItem orderItem1 = new OrderItem(product1, 3); // 3 προϊόντα

        // Δημιουργία παραγγελίας
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem1);
        Order order = new Order(orderItems);

        // Έλεγχος αν το συνολικό κόστος υπολογίζεται σωστά
        double expectedCost = 20.0 * 3; // 20.0 * 3 = 60.0
        assertEquals(expectedCost, order.getTotalCost(), 0.01, "Το συνολικό κόστος πρέπει να είναι 60.0");
    }

    @Test
    void testEmptyOrder() {
        // Δημιουργία κενής παραγγελίας
        List<OrderItem> emptyOrderItems = new ArrayList<>();
        Order emptyOrder = new Order(emptyOrderItems);

        // Έλεγχος αν η παραγγελία είναι κενή και το συνολικό κόστος είναι 0
        assertTrue(emptyOrder.getOrderItems().isEmpty(), "Η παραγγελία πρέπει να είναι κενή");
        assertEquals(0.0, emptyOrder.getTotalCost(), 0.01, "Το συνολικό κόστος πρέπει να είναι 0 για κενή παραγγελία");
    }

    @Test
    void testOrderItems() {
        // Δημιουργία προϊόντων
        Product product1 = new Product("Product A", "Description A", "Category A", "Subcategory A", 5.0, 200);
        Product product2 = new Product("Product B", "Description B", "Category B", "Subcategory B", 10.0, 150);

        // Δημιουργία OrderItems
        OrderItem orderItem1 = new OrderItem(product1, 5); // 5 τεμάχια από Product A
        OrderItem orderItem2 = new OrderItem(product2, 3); // 3 τεμάχια από Product B

        // Δημιουργία παραγγελίας
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem1);
        orderItems.add(orderItem2);
        Order order = new Order(orderItems);

        // Έλεγχος αν τα προϊόντα είναι σωστά στην παραγγελία
        assertEquals("Product A", order.getOrderItems().get(0).getProduct().getName());
        assertEquals(5, order.getOrderItems().get(0).getQuantity());
        assertEquals("Product B", order.getOrderItems().get(1).getProduct().getName());
        assertEquals(3, order.getOrderItems().get(1).getQuantity());
    }
}