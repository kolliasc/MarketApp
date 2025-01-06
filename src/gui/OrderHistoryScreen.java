package gui;

import javax.swing.*;
import java.util.ArrayList;
import api.Order;
import api.OrderItem;

/**
 * Η κλάση OrderHistoryScreen παρέχει την οθόνη εμφάνισης του ιστορικού παραγγελιών του χρήστη.
 * Η οθόνη αυτή δείχνει τις παραγγελίες που έχει κάνει ο χρήστης στο παρελθόν.
 */
public class OrderHistoryScreen extends JFrame {
    private JPanel panel1;
    private JList<Order> orderHistoryList;
    private DefaultListModel<Order> orderHistoryListModel;

    /**
     * Κατασκευαστής για την οθόνη ιστορικού παραγγελιών.
     * Δημιουργεί και διαχειρίζεται τα οπτικά στοιχεία της οθόνης.
     */
    public OrderHistoryScreen() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(400, 300);
        setTitle("Order History");
        setContentPane(panel1);
        setLocationRelativeTo(null);
        setVisible(true);

        orderHistoryListModel = new DefaultListModel<>();
        orderHistoryList.setModel(orderHistoryListModel);

        loadOrderHistory();
    }

    /**
     * Φορτώνει το ιστορικό παραγγελιών από την πηγή δεδομένων.
     * Αυτή η μέθοδος προσθέτει δείγματα παραγγελιών στη λίστα παραγγελιών.
     */
    private void loadOrderHistory() {

        ArrayList<OrderItem> orderItems1 = new ArrayList<>();
        Order order1 = new Order(orderItems1);
        orderHistoryListModel.addElement(order1);

    }
}
