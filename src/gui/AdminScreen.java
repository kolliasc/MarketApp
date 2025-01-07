package gui;

import api.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * Οθόνη Διαχειριστή που επιτρέπει στους διαχειριστές να υποβάλουν, να επεξεργάζονται και να αναζητούν προϊόντα.
 * Περιλαμβάνει δυνατότητες προβολής στατιστικών στοιχείων και logout.
 */
public class AdminScreen extends JFrame {

    private JPanel panel1;
    private JButton editProductsButton;
    private JButton searchProductsButton;
    private JButton submitProductsButton;
    private JButton showStatisticsButton;
    private JButton logout;

    /**
     * Δημιουργεί την οθόνη διαχειριστή με τα αντίστοιχα κουμπιά και ενέργειες.
     */
    public AdminScreen() {
        setTitle("Admin Screen");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel1 = new JPanel();
        panel1.setLayout(new GridLayout(5, 1));
        panel1.add(editProductsButton);
        panel1.add(searchProductsButton);
        panel1.add(submitProductsButton);
        panel1.add(showStatisticsButton);
        panel1.add(logout);




        add(panel1);
        submitProductsButton.addActionListener(e -> {
            searchProduct();
        });
        logout.addActionListener(e -> {
            new Login();
            dispose();
        });

        searchProductsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String currentUsername = getCurrentUserUsername(); // Get the current logged-in user
                if (!FileHandler.isAdmin(currentUsername)) {
                    JOptionPane.showMessageDialog(null, "Σφάλμα.");
                    return;
                }

                showProductEditForm();
            }
        });


        editProductsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check if the current user is an admin
                String currentUsername = getCurrentUserUsername();
                if (!FileHandler.isAdmin(currentUsername)) {
                    JOptionPane.showMessageDialog(null, "Μόνο οι διαχειριστές μπορούν να καταχωρήσουν προϊόντα.");
                    return;
                }

                showProductSubmissionForm();
            }
        });
        showStatisticsButton.addActionListener(e -> showStatistics());

        setVisible(true);
    }

    /**
     * Επιστρέφει το όνομα χρήστη του τρέχοντος συνδεδεμένου χρήστη.
     * @return Το όνομα χρήστη του τρέχοντος χρήστη.
     */
    private String getCurrentUserUsername() {

        return "admin1";
    }

    /**
     * Εμφανίζει τη φόρμα υποβολής νέου προϊόντος.
     */
    private void showProductSubmissionForm() {
        // Create a new frame for the product submission form
        JFrame productFormFrame = new JFrame("Καταχώρηση προϊόντος");
        productFormFrame.setSize(400, 300);
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(6, 2));

        JLabel nameLabel = new JLabel("Όνομα προϊόντος:");
        JTextField nameField = new JTextField();
        JLabel descriptionLabel = new JLabel("Περιγραφή:");
        JTextField descriptionField = new JTextField();
        JLabel categoryLabel = new JLabel("Κατηγορία:");
        JComboBox<String> categoryComboBox = new JComboBox<>(new String[]{"Φρέσκα Τρόφιμα", "Κατεψυγμένα τρόφιμα", "Προϊόντα ψυγείου", "Αλλαντικά", "Αλκοολούχα ποτά"});
        JLabel subcategoryLabel = new JLabel("Subcategory:");
        JComboBox<String> subcategoryComboBox = new JComboBox<>(new String[]{"Φρούτα", "Κατεψυγμένα λαχανικά","Τυριά", "Ζαμπόν", "Μπύρα"});
        JLabel priceLabel = new JLabel("Τιμή:");
        JTextField priceField = new JTextField();
        JLabel quantityLabel = new JLabel("Ποσότητα:");
        JTextField quantityField = new JTextField();

        JButton submitButton = new JButton("Επιβεβαίωση προϊόντος");
        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(descriptionLabel);
        formPanel.add(descriptionField);
        formPanel.add(categoryLabel);
        formPanel.add(categoryComboBox);
        formPanel.add(subcategoryLabel);
        formPanel.add(subcategoryComboBox);
        formPanel.add(priceLabel);
        formPanel.add(priceField);
        formPanel.add(quantityLabel);
        formPanel.add(quantityField);

        formPanel.add(submitButton);

        productFormFrame.add(formPanel);
        productFormFrame.setVisible(true);


        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String description = descriptionField.getText();
                String category = (String) categoryComboBox.getSelectedItem();
                String subcategory = (String) subcategoryComboBox.getSelectedItem();
                double price;
                int quantity;

                try {
                    price = Double.parseDouble(priceField.getText());
                    quantity = Integer.parseInt(quantityField.getText());
                    if (price <= 0 || quantity <= 0) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Λάθος τιμή ή ποσότητα.");
                    return;
                }

                if (name.isEmpty() || description.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Όλα τα πεδία πρέπει να συμπληρωθούν.");
                    return;
                }

                Product newProduct = new Product(name, description, category, subcategory, price, quantity);
                FileHandler.addProduct(newProduct);

                JOptionPane.showMessageDialog(null, "Το προϊόν καταχωρήθηκε επιτυχώς.");
                productFormFrame.dispose();
            }
        });
    }

    /**
     * Εκτελεί την αναζήτηση προϊόντων με φίλτρα για τίτλο, κατηγορία και υποκατηγορία.
     */
    private void searchProduct() {

        java.util.List<Product> products = FileHandler.loadProducts();

        Set<String> categories = new HashSet<>();
        Set<String> subcategories = new HashSet<>();
        for (Product product : products) {
            categories.add(product.getCategory());
            subcategories.add(product.getSubcategory());
        }


        JTextField titleField = new JTextField();
        JComboBox<String> categoryBox = new JComboBox<>(categories.toArray(new String[0]));
        categoryBox.insertItemAt("Όλες οι Κατηγορίες", 0); // Add "All Categories" option
        categoryBox.setSelectedIndex(0);

        JComboBox<String> subcategoryBox = new JComboBox<>();
        subcategoryBox.addItem("Όλες οι Υποκατηγορίες"); // Default option for all subcategories

        categoryBox.addActionListener(e -> {
            subcategoryBox.removeAllItems();
            subcategoryBox.addItem("Όλες οι Υποκατηγορίες"); // Default option for all subcategories
            String selectedCategory = (String) categoryBox.getSelectedItem();

            // Add subcategories based on the selected category
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

            java.util.List<Product> filteredProducts = new ArrayList<>();


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
     * Εμφανίζει τα αποτελέσματα της αναζήτησης προϊόντων.
     * @param products Τα προϊόντα που αντιστοιχούν στα κριτήρια αναζήτησης.
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
     * Εμφανίζει τις λεπτομέρειες ενός επιλεγμένου προϊόντος.
     * @param product Το προϊόν για το οποίο θέλουμε να δείξουμε τις λεπτομέρειες.
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




        int result = JOptionPane.showConfirmDialog(this, panel, "Προβολή Προϊόντος", JOptionPane.OK_CANCEL_OPTION);

    }

    /**
     * Εκτελεί την λειτουργία επεξεργασία ενός προϊόντος (όνομα, περιγραφή κλπ)
     */
    private void showProductEditForm() {

        List<Product> products = FileHandler.loadProducts();

        String[] productNames = products.stream().map(Product::getName).toArray(String[]::new);


        String selectedProductName = (String) JOptionPane.showInputDialog(this,
                "Select a product to edit:",
                "Edit Product",
                JOptionPane.PLAIN_MESSAGE,
                null,
                productNames,
                productNames[0]);

        if (selectedProductName != null) {

            Product selectedProduct = null;
            for (Product product : products) {
                if (product.getName().equals(selectedProductName)) {
                    selectedProduct = product;
                    break;
                }
            }

            if (selectedProduct != null) {

                JFrame editFormFrame = new JFrame("Eπεξεργασία προϊόντος");
                editFormFrame.setSize(400, 300);
                JPanel formPanel = new JPanel();
                formPanel.setLayout(new GridLayout(6, 2));


                JLabel nameLabel = new JLabel("Όνομα προϊόντος:");
                JTextField nameField = new JTextField(selectedProduct.getName());
                JLabel descriptionLabel = new JLabel("Περιγραφή:");
                JTextField descriptionField = new JTextField(selectedProduct.getDescription());
                JLabel categoryLabel = new JLabel("Κατηγορία:");
                JComboBox<String> categoryComboBox = new JComboBox<>(new String[]{"Φρέσκα Τρόφιμα", "Κατεψυγμένα τρόφιμα", "Προϊόντα ψυγείου", "Αλλαντικά", "Αλκοολούχα ποτά"});
                categoryComboBox.setSelectedItem(selectedProduct.getCategory());
                JLabel subcategoryLabel = new JLabel("Υποκατηγορία:");
                JComboBox<String> subcategoryComboBox = new JComboBox<>(new String[]{"Φρούτα", "Κατεψυγμένα λαχανικά","Τυριά", "Ζαμπόν", "Μπύρα"});
                subcategoryComboBox.setSelectedItem(selectedProduct.getSubcategory());
                JLabel priceLabel = new JLabel("Τιμή:");
                JTextField priceField = new JTextField(String.valueOf(selectedProduct.getPrice()));
                JLabel quantityLabel = new JLabel("Ποσότητα:");
                JTextField quantityField = new JTextField(String.valueOf(selectedProduct.getQuantity()));

                JButton updateButton = new JButton("Ενημέρωση προϊόντος");
                formPanel.add(nameLabel);
                formPanel.add(nameField);
                formPanel.add(descriptionLabel);
                formPanel.add(descriptionField);
                formPanel.add(categoryLabel);
                formPanel.add(categoryComboBox);
                formPanel.add(subcategoryLabel);
                formPanel.add(subcategoryComboBox);
                formPanel.add(priceLabel);
                formPanel.add(priceField);
                formPanel.add(quantityLabel);
                formPanel.add(quantityField);

                formPanel.add(updateButton);

                editFormFrame.add(formPanel);
                editFormFrame.setVisible(true);


                Product finalSelectedProduct = selectedProduct;
                updateButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        String name = nameField.getText();
                        String description = descriptionField.getText();
                        String category = (String) categoryComboBox.getSelectedItem();
                        String subcategory = (String) subcategoryComboBox.getSelectedItem();
                        double price;
                        int quantity;

                        try {
                            price = Double.parseDouble(priceField.getText());
                            quantity = Integer.parseInt(quantityField.getText());
                            if (price <= 0 || quantity < 0) {
                                throw new NumberFormatException();
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Λάθος τιμή ή ποσότητα.");
                            return;
                        }


                        finalSelectedProduct.setName(name);
                        finalSelectedProduct.setDescription(description);
                        finalSelectedProduct.setCategory(category);
                        finalSelectedProduct.setSubCategory(subcategory);
                        finalSelectedProduct.setPrice(price);
                        finalSelectedProduct.setQuantity(quantity);


                        FileHandler.saveProducts(products);

                        JOptionPane.showMessageDialog(null, "Το προϊόν ενημερώθηκε επιτυχώς.");
                        editFormFrame.dispose(); // Close the form
                    }
                });
            }
        }
    }

    /**
     * Εμφανίζει τις στατιστικές του συστήματος (π.χ. τον αριθμό προϊόντων, τη συνολική τιμή κλπ).
     */
    private void showStatistics() {

        List<Product> products = FileHandler.loadProducts();
        List<Order> orders = FileHandler.loadOrders();


        List<Product> unavailableProducts = getUnavailableProducts(products);


        List<Product> mostOrderedProducts = getMostOrderedProducts(orders, products);


        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));


        panel.add(new JLabel("Μη διαθέσιμα προϊόντα:"));
        if (unavailableProducts.isEmpty()) {
            panel.add(new JLabel("Δεν υπάρχουν μη διαθέσιμα προϊόντα."));
        } else {
            for (Product product : unavailableProducts) {
                panel.add(new JLabel(product.getName() + " - " + product.getCategory() + " - " + product.getSubcategory()));
            }
        }


        panel.add(new JLabel("Προϊόντα με τις περισσότερες παραγγελίες:"));
        if (mostOrderedProducts.isEmpty()) {
            panel.add(new JLabel("Δεν υπάρχουν προϊόντα με παραγγελίες."));
        } else {
            for (Product product : mostOrderedProducts) {
                panel.add(new JLabel(product.getName() + " - " + product.getCategory() + " - " + product.getSubcategory()));
            }
        }

        JOptionPane.showMessageDialog(this, panel, "Στατιστικά Προϊόντων", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     *
     * @param products τα προϊόντα που δεν ειναι διαθεσιμα
     * @return επιστρεφει την λίστα με τα προϊόντα που δεν ειναι διαθέσιμα
     */
    private List<Product> getUnavailableProducts(List<Product> products) {
        List<Product> unavailableProducts = new ArrayList<>();
        for (Product product : products) {
            if (product.getQuantity() == 0) {
                unavailableProducts.add(product);
            }
        }
        return unavailableProducts;
    }

    /**
     *
     * @param orders λίστα με παραγγελίες
     * @param products λίστα με τα προϊόντα
     * @return επιστρέφει μία λίστα με τα προϊόντα που έχουν γίνει περισσότερες φορές παραγγελία
     */
    private List<Product> getMostOrderedProducts(List<Order> orders, List<Product> products) {
        Map<String, Integer> productOrderCount = new HashMap<>();


        for (Order order : orders) {
            for (OrderItem ord : order.getOrderItems()) {
                String productKey = ord.getProduct().getName() + " - " + ord.getProduct().getCategory() + " - " + ord.getProduct().getSubcategory();
                productOrderCount.put(productKey, productOrderCount.getOrDefault(productKey, 0) + 1);
            }
        }


        List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(productOrderCount.entrySet());
        sortedEntries.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));


        List<Product> mostOrderedProducts = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : sortedEntries) {
            for (Product product : products) {
                String productKey = product.getName() + " - " + product.getCategory() + " - " + product.getSubcategory();
                if (productKey.equals(entry.getKey())) {
                    mostOrderedProducts.add(product);
                    break;
                }
            }
        }
        return mostOrderedProducts;
    }

}
