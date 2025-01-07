package api;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Η κλάση {@code Customer} αναπαριστά έναν πελάτη στο σύστημα.
 * Ο πελάτης μπορεί να προσθέτει και να αφαιρεί προϊόντα από το καλάθι του,
 * να ολοκληρώνει παραγγελίες και να έχει ιστορικό παραγγελιών.
 */

public class Customer extends User {
    private ShoppingCart cart;
    private OrderHistory orderHistory;
    private ProductCatalog productCatalog;

    /**
     * Κατασκευαστής της κλάσης {@code Customer}.
     * Δημιουργεί έναν νέο πελάτη με όνομα χρήστη και κωδικό πρόσβασης.
     * Επίσης, δημιουργεί ένα νέο καλάθι και ιστορικό παραγγελιών για τον πελάτη.
     *
     * @param username Το όνομα χρήστη του πελάτη.
     * @param password Ο κωδικός πρόσβασης του πελάτη.
     */
    public Customer(String username, String password) {
        super(username, password, "User");
        this.cart = new ShoppingCart();
        this.orderHistory = new OrderHistory();
        this.productCatalog = new ProductCatalog();
    }

    /**
     * Προσθέτει ένα προϊόν στο καλάθι αγορών του πελάτη.
     *
     * @param product  Το προϊόν που θα προστεθεί στο καλάθι.
     * @param quantity Η ποσότητα του προϊόντος που θα προστεθεί.
     */
    public void addToCart(Product product, int quantity) {
        cart.addProduct(product, quantity);
    }

    /**
     * Αφαιρεί ένα προϊόν από το καλάθι αγορών του πελάτη.
     *
     * @param product  Το προϊόν που θα αφαιρεθεί από το καλάθι.
     * @param quantity Η ποσότητα του προϊόντος που θα αφαιρεθεί.
     * @return {@code true} αν η αφαίρεση ήταν επιτυχής, {@code false} αν δεν υπήρχε αρκετό απόθεμα.
     */
    public boolean removeFromCart(Product product, int quantity) {
        return cart.removeProduct(product, quantity);
    }


    /**
     * Ολοκληρώνει την παραγγελία του πελάτη.
     * Ελέγχει το απόθεμα των προϊόντων στο καλάθι και αν είναι διαθέσιμο, καταχωρεί την παραγγελία στο ιστορικό.
     * Αν κάποιο προϊόν δεν έχει επαρκές απόθεμα, η παραγγελία δεν ολοκληρώνεται.
     */
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

    /**
     * Επιστρέφει το ιστορικό παραγγελιών του πελάτη.
     *
     * @return Το ιστορικό παραγγελιών του πελάτη.
     */
    public OrderHistory getOrderHistory() {
        return orderHistory;
    }

    public ShoppingCart getCart(){return cart;}
}