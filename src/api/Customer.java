package api;

import java.util.List;
import java.util.stream.Collectors;

public class Customer extends User {
    private ShoppingCart cart;
    private OrderHistory orderHistory;
    private ProductCatalog productCatalog;

    public Customer(String username, String password) {
        super(username, password, "user");
        this.cart = new ShoppingCart();
        this.orderHistory = new OrderHistory();
    }

    public void addToCart(Product product, int quantity) {
        cart.addProduct(product, quantity);
    }

    public boolean removeFromCart(Product product, int quantity) {
        return cart.removeProduct(product, quantity);
    }

    public void completeOrder() {
        boolean orderSuccess = true;

        for (CartItem item : cart.getCartItems()) {
            Product product = item.getProduct();
            int quantityPurchased = item.getQuantity();

            if (quantityPurchased > product.getAvailableStock()) {
                System.out.println("Δεν επαρκεί το απόθεμα για το προϊόν: " + product.getName());
                orderSuccess = false;
            } else {
                productCatalog.removeProductQuantity(product.getName(), quantityPurchased);
            }
        }

        if (orderSuccess) {
            List<OrderItem> orderItems = cart.getCartItems().stream()
                    .map(cartItem -> new OrderItem(cartItem.getProduct(), cartItem.getQuantity()))
                    .collect(Collectors.toList());

            Order order = new Order(orderItems);
            orderHistory.addOrder(order);
            cart.clearCart();

            System.out.println("Η παραγγελία σας ολοκληρώθηκε επιτυχώς.");
        } else {
            System.out.println("Η παραγγελία δεν ολοκληρώθηκε. Παρακαλώ διορθώστε τα προβλήματα.");
        }
    }

    public OrderHistory getOrderHistory() {
        return orderHistory;
    }
}
