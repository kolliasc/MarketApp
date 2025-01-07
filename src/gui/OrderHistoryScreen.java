package gui;

import javax.swing.*;
import java.util.ArrayList;
import api.Order;
import api.OrderItem;


public class OrderHistoryScreen extends JFrame {
    private JPanel panel1;
    private JList<Order> orderHistoryList;
    private DefaultListModel<Order> orderHistoryListModel;

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

    private void loadOrderHistory() {

        ArrayList<OrderItem> orderItems1 = new ArrayList<>();
        Order order1 = new Order(orderItems1);
        orderHistoryListModel.addElement(order1);

    }
}
