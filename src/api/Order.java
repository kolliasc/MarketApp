package api;

import java.util.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Η κλάση {@code Order} αναπαριστά μια παραγγελία με τα προϊόντα της, την ημερομηνία παραγγελίας και το συνολικό κόστος.
 *
 * Κάθε παραγγελία περιλαμβάνει μια λίστα {@link OrderItem} που καθορίζει τα προϊόντα της παραγγελίας
 * και η ημερομηνία παραγγελίας ρυθμίζεται αυτόματα στην τρέχουσα ημερομηνία κατά τη δημιουργία της παραγγελίας.
 */
public class Order implements Serializable {
    private LocalDate orderDate;
    private List<OrderItem> orderItems;

    /**
     * Δημιουργεί μια νέα παραγγελία με την τρέχουσα ημερομηνία και τη λίστα των προϊόντων.
     *
     * @param orderItems Η λίστα των προϊόντων της παραγγελίας.
     */
    public Order(List<OrderItem> orderItems) {
        this.orderDate = LocalDate.now(); // Ρύθμιση της ημερομηνίας στη δημιουργία της παραγγελίας
        this.orderItems = orderItems;
    }

    /**
     * Επιστρέφει την ημερομηνία της παραγγελίας.
     *
     * @return Η ημερομηνία παραγγελίας.
     */
    public LocalDate getOrderDate() {
        return orderDate;
    }

    /**
     * Επιστρέφει τη λίστα των προϊόντων της παραγγελίας.
     *
     * @return Η λίστα {@link OrderItem} με τα προϊόντα της παραγγελίας.
     */
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    /**
     * Υπολογίζει το συνολικό κόστος της παραγγελίας. Το συνολικό κόστος υπολογίζεται ως το άθροισμα
     * των συνολικών τιμών των επιμέρους προϊόντων.
     *
     * @return Το συνολικό κόστος της παραγγελίας.
     */
    public double getTotalCost() {
        double totalCost = 0;
        for (OrderItem item : orderItems) {
            totalCost += item.getTotalCost();
        }
        return totalCost;
    }
}

