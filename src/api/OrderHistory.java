package api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Η κλάση {@code OrderHistory} αναπαριστά το ιστορικό παραγγελιών ενός πελάτη.
 *
 * Περιέχει μια λίστα παραγγελιών και παρέχει μεθόδους για την προσθήκη νέας παραγγελίας,
 * την επιστροφή των παραγγελιών και την εξαγωγή περιγραφής των παραγγελιών με όλες τις λεπτομέρειες.
 */
class OrderHistory implements Serializable {
    private List<Order> orders;


    /**
     * Κατασκευαστής της κλάσης {@code OrderHistory} που αρχικοποιεί τη λίστα παραγγελιών ως κενή.
     */
    public OrderHistory() {
        this.orders = new ArrayList<>();

    }

    /**
     * Προσθέτει μια νέα παραγγελία στο ιστορικό.
     *
     * @param order Η παραγγελία που θα προστεθεί.
     */
    public void addOrder(Order order) {
        if (order != null) {
            orders.add(order);
        }
    }

    /**
     * Επιστρέφει το ιστορικό των παραγγελιών ως λίστα.
     * Η λίστα επιστρέφεται ως αντίγραφο για την ασφάλεια των δεδομένων.
     *
     * @return Η λίστα των παραγγελιών του ιστορικού.
     */
    public List<Order> getOrders() {
        return new ArrayList<>(orders); // Επιστρέφει αντίγραφο για ασφάλεια.
    }

    /**
     * Επιστρέφει τις περιγραφές των παραγγελιών στο ιστορικό.
     * Κάθε περιγραφή περιλαμβάνει την ημερομηνία της παραγγελίας, τα προϊόντα και το συνολικό κόστος.
     * Αν δεν υπάρχουν παραγγελίες, επιστρέφει μήνυμα που αναφέρει ότι το ιστορικό είναι άδειο.
     *
     * @return Λίστα με περιγραφές των παραγγελιών.
     */
    public List<String> getOrderDescriptions() {
        List<String> descriptions = new ArrayList<>();
        if (orders.isEmpty()) {
            descriptions.add("Δεν έχετε παραγγελίες στο ιστορικό.");
        } else {
            for (Order order : orders) {
                StringBuilder orderDescription = new StringBuilder();
                orderDescription.append("Ημερομηνία: ").append(order.getOrderDate()).append("\n");
                orderDescription.append("Προϊόντα:\n");
                for (OrderItem item : order.getOrderItems()) {
                    orderDescription.append("- ")
                            .append(item.getProduct().getName())
                            .append(" (Ποσότητα: ").append(item.getQuantity())
                            .append(", Κόστος: €").append(item.getTotalCost()).append(")\n");
                }
                orderDescription.append("Συνολικό Κόστος: €").append(order.getTotalCost());
                descriptions.add(orderDescription.toString());
            }
        }
        return descriptions;
    }
}
