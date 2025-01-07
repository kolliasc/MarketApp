package gui;

import api.Order;
import api.OrderItem;
import api.Product;
import api.FileHandler;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

/**
 * Η κλάση CustomerScreen αντιπροσωπεύει την οθόνη του πελάτη με δυνατότητες αναζήτησης προϊόντων,
 * διαχείρισης καλαθιού και παραγγελιών, και αποσύνδεσης.
 */
public class CustomerScreen extends JFrame {

    private JPanel panel1;
    private JButton searchProductButton;
    private JButton manageCartButton;
    private JButton manageOrdersButton;
    private JButton logoutButton;

    private List<Product> cart;

    /**
     * Κατασκευαστής για την οθόνη πελάτη. Ορίζει τα οπτικά στοιχεία και τις ενέργειες των κουμπιών.
     */
    public CustomerScreen() {
        cart = new ArrayList<>();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(600, 400);
        setTitle("Πίνακας Πελάτη");
        setContentPane(panel1);
        setLocationRelativeTo(null);
        setVisible(true);

        searchProductButton.addActionListener(e -> searchProduct());
        manageCartButton.addActionListener(e -> manageCart());
        manageOrdersButton.addActionListener(e -> manageOrders());
        logoutButton.addActionListener(e -> {
            new Login();
            dispose();
        });
    }

    /**
     * Μέθοδος για αναζήτηση προϊόντων με βάση τίτλο, κατηγορία και υποκατηγορία.
     * Παρουσιάζει τα αποτελέσματα της αναζήτησης στον χρήστη.
     */
    private void searchProduct() {
        List<Product> products = FileHandler.loadProducts();

        Set<String> categories = new HashSet<>();
        Set<String> subcategories = new HashSet<>();
        for (Product product : products) {
            categories.add(product.getCategory());
            subcategories.add(product.getSubcategory());
        }

        JTextField titleField = new JTextField();
        JComboBox<String> categoryBox = new JComboBox<>(categories.toArray(new String[0]));
        categoryBox.insertItemAt("Όλες οι Κατηγορίες", 0);
        categoryBox.setSelectedIndex(0);

        JComboBox<String> subcategoryBox = new JComboBox<>();
        subcategoryBox.addItem("Όλες οι Υποκατηγορίες");

        categoryBox.addActionListener(e -> {
            subcategoryBox.removeAllItems();
            subcategoryBox.addItem("Όλες οι Υποκατηγορίες");
            String selectedCategory = (String) categoryBox.getSelectedItem();
            for (Product product : products) {
                if (product.getCategory().equals(selectedCategory) || selectedCategory.equals("Όλες οι Κατηγορίες")) {
                    subcategoryBox.addItem(product.getSubcategory());
                }
            }
        });

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("Τίτλος:"));
        panel.add(titleField);
        panel.add(new JLabel("Κατηγορία:"));
        panel.add(categoryBox);
        panel.add(new JLabel("Υποκατηγορία:"));
        panel.add(subcategoryBox);

        int result = JOptionPane.showConfirmDialog(this, panel, "Αναζήτηση Προϊόντος", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String title = titleField.getText().trim().toLowerCase();
            String category = (String) categoryBox.getSelectedItem();
            String subcategory = (String) subcategoryBox.getSelectedItem();

            List<Product> filteredProducts = new ArrayList<>();

            for (Product product : products) {
                boolean matchesTitle = title.isEmpty() || product.getName().toLowerCase().contains(title);
                boolean matchesCategory = category.equals("Όλες οι Κατηγορίες") || product.getCategory().equalsIgnoreCase(category);
                boolean matchesSubcategory = subcategory.equals("Όλες οι Υποκατηγορίες") || product.getSubcategory().equalsIgnoreCase(subcategory);

                if (matchesTitle && matchesCategory && matchesSubcategory) {
                    filteredProducts.add(product);
                }
            }

            if (filteredProducts.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Δεν βρέθηκαν προϊόντα.");
            } else {
                showSearchResults(filteredProducts);
            }
        }
    }

    /**
     * Εμφανίζει τα αποτελέσματα αναζήτησης προϊόντων και επιτρέπει στον χρήστη να επιλέξει ένα προϊόν για προβολή.
     * @param products Η λίστα με τα προϊόντα που πληρούν τα κριτήρια αναζήτησης.
     */
    private void showSearchResults(List<Product> products) {
        String[] productNames = products.stream().map(Product::getName).toArray(String[]::new);
        String selectedProduct = (String) JOptionPane.showInputDialog(this,
                "Επιλέξτε προϊόν για προβολή:",
                "Αποτελέσματα Αναζήτησης",
                JOptionPane.PLAIN_MESSAGE,
                null,
                productNames,
                productNames[0]);

        if (selectedProduct != null) {
            for (Product product : products) {
                if (product.getName().equals(selectedProduct)) {
                    showProductDetails(product);
                    break;
                }
            }
        }
    }

    /**
     * Εμφανίζει τις λεπτομέρειες ενός επιλεγμένου προϊόντος και επιτρέπει στον χρήστη να προσθέσει ποσότητα στο καλάθι του.
     * @param product Το προϊόν για το οποίο εμφανίζονται οι λεπτομέρειες.
     */
    private void showProductDetails(Product product) {
        JTextField quantityField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Προϊόν:"));
        panel.add(new JLabel(product.getName()));
        panel.add(new JLabel("Περιγραφή:"));
        panel.add(new JLabel(product.getDescription()));
        panel.add(new JLabel("Τιμή:"));
        panel.add(new JLabel(String.valueOf(product.getPrice()) + "€"));
        panel.add(new JLabel("Διαθέσιμη Ποσότητα:"));
        panel.add(new JLabel(String.valueOf(product.getQuantity())));

        panel.add(new JLabel("Ποσότητα (τεμάχια/κιλά):"));
        panel.add(quantityField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Προβολή Προϊόντος", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                int quantity = Integer.parseInt(quantityField.getText().trim());

                if (quantity <= 0) {
                    JOptionPane.showMessageDialog(this, "Παρακαλώ εισάγετε μια έγκυρη ποσότητα.");
                } else if (quantity > product.getQuantity()) {
                    JOptionPane.showMessageDialog(this, "Η ζητούμενη ποσότητα δεν είναι διαθέσιμη.");
                } else {
                    product.setQuantity(product.getQuantity() - quantity);
                    cart.add(new Product(product.getName(), product.getDescription(), product.getCategory(), product.getSubcategory(), product.getPrice(), quantity));
                    JOptionPane.showMessageDialog(this, "Το προϊόν προστέθηκε στο καλάθι.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Παρακαλώ εισάγετε έγκυρη ποσότητα.");
            }
        }
    }

    /**
     * Διαχειρίζεται το καλάθι του χρήστη, επιτρέποντας την ολοκλήρωση της παραγγελίας.
     */
    private void manageCart() {
        if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Το καλάθι σας είναι άδειο.");
        } else {
            StringBuilder cartContents = new StringBuilder("Το καλάθι σας περιέχει:\n");

            List<Product> purchasedProducts = new ArrayList<>();
            double totalCost = 0;

            for (Product product : cart) {
                cartContents.append("- ").append(product.getName())
                        .append(" (Ποσότητα: ").append(product.getQuantity()).append(")\n");

                purchasedProducts.add(product);
                totalCost += product.getPrice() * product.getQuantity();
            }

            int result = JOptionPane.showConfirmDialog(this,
                    cartContents.toString() + "\nΘέλετε να ολοκληρώσετε την παραγγελία σας;",
                    "Διαχείριση Καλαθιού",
                    JOptionPane.YES_NO_OPTION);

            if (result == JOptionPane.YES_OPTION) {
                for (Product product : cart) {
                    FileHandler.updateProductQuantity(product);
                }

                cart.clear();

                saveOrderToHistory(purchasedProducts, totalCost);

                JOptionPane.showMessageDialog(this, "Η παραγγελία ολοκληρώθηκε.");
            }
        }
    }

    /**
     * Αποθηκεύει την παραγγελία στο ιστορικό παραγγελιών.
     * @param purchasedProducts Η λίστα με τα προϊόντα που αγοράστηκαν.
     * @param totalCost Το συνολικό κόστος της παραγγελίας.
     */
    private void saveOrderToHistory(List<Product> purchasedProducts, double totalCost) {
        LocalDate orderDate = LocalDate.now();

        List<OrderItem> orderItems = new ArrayList<>();
        for (Product product : purchasedProducts) {
            orderItems.add(new OrderItem(product, 1));
        }

        Order order = new Order(orderItems);

        FileHandler.saveOrder(order);
    }

    /**
     * Διαχειρίζεται το ιστορικό παραγγελιών του χρήστη.
     */
    private void manageOrders() {
        List<Order> orders = FileHandler.loadOrders();

        if (orders.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Δεν έχετε κάνει παραγγελίες.");
        } else {
            StringBuilder orderHistory = new StringBuilder("Ιστορικό Παραγγελιών:\n");

            for (Order order : orders) {
                orderHistory.append("Ημερομηνία: ").append(order.getOrderDate()).append("\n");
                orderHistory.append("Προϊόντα: \n");
                for (OrderItem orderItem : order.getOrderItems()) {
                    Product product = orderItem.getProduct();
                    orderHistory.append("- ").append(product.getName())
                            .append(" (Ποσότητα: ").append(product.getQuantity()).append(")\n");
                }
                orderHistory.append("Συνολικό κόστος: ").append(order.getTotalCost()).append("€\n\n");
            }

            JOptionPane.showMessageDialog(this, orderHistory.toString());
        }
    }
}
