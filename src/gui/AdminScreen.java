package gui;

import api.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class AdminScreen extends JFrame {

    private JPanel panel1;
    private JButton submitProductsButton;
    private JButton editProductsButton;
    private JButton searchProductsButton;
    private JButton showStatisticsButton;
    private JButton logout;

    public AdminScreen() {
        setTitle("Admin Screen");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel1 = new JPanel();
        panel1.setLayout(new GridLayout(5, 1));
        panel1.add(submitProductsButton);
        panel1.add(editProductsButton);
        panel1.add(searchProductsButton);
        panel1.add(showStatisticsButton);
        panel1.add(logout);




        add(panel1);
        searchProductsButton.addActionListener(e -> {
            searchProduct();
        });
        logout.addActionListener(e -> {
            new Login();
            dispose();
        });

        editProductsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String currentUsername = getCurrentUserUsername(); // Get the current logged-in user
                if (!FileHandler.isAdmin(currentUsername)) {
                    JOptionPane.showMessageDialog(null, "Only admins can edit products.");
                    return;
                }

                showProductEditForm();
            }
        });


        submitProductsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check if the current user is an admin
                String currentUsername = getCurrentUserUsername(); // Assuming you have a method to get the current user
                if (!FileHandler.isAdmin(currentUsername)) {
                    JOptionPane.showMessageDialog(null, "Only admins can submit products.");
                    return;
                }

                showProductSubmissionForm();
            }
        });
        showStatisticsButton.addActionListener(e -> showStatistics());

        setVisible(true);
    }

    private String getCurrentUserUsername() {
        // This should return the username of the currently logged-in user
        return "admin1"; // Example hardcoded username
    }

    private void showProductSubmissionForm() {
        // Create a new frame for the product submission form
        JFrame productFormFrame = new JFrame("Submit Product");
        productFormFrame.setSize(400, 300);
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(6, 2));

        JLabel nameLabel = new JLabel("Product Name:");
        JTextField nameField = new JTextField();
        JLabel descriptionLabel = new JLabel("Description:");
        JTextField descriptionField = new JTextField();
        JLabel categoryLabel = new JLabel("Category:");
        JComboBox<String> categoryComboBox = new JComboBox<>(new String[]{"Fresh Foods", "Frozen Foods", "Dairy", "Meats", "Beverages"});
        JLabel subcategoryLabel = new JLabel("Subcategory:");
        JComboBox<String> subcategoryComboBox = new JComboBox<>(new String[]{"Fruits", "Vegetables", "Fish", "Meats"});
        JLabel priceLabel = new JLabel("Price:");
        JTextField priceField = new JTextField();
        JLabel quantityLabel = new JLabel("Quantity:");
        JTextField quantityField = new JTextField();

        JButton submitButton = new JButton("Submit Product");
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
                    JOptionPane.showMessageDialog(null, "Invalid price or quantity.");
                    return;
                }

                if (name.isEmpty() || description.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "All fields are required.");
                    return;
                }

                Product newProduct = new Product(name, description, category, subcategory, price, quantity);
                FileHandler.addProduct(newProduct);

                JOptionPane.showMessageDialog(null, "Product submitted successfully.");
                productFormFrame.dispose();
            }
        });
    }

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

                JFrame editFormFrame = new JFrame("Edit Product");
                editFormFrame.setSize(400, 300);
                JPanel formPanel = new JPanel();
                formPanel.setLayout(new GridLayout(6, 2));


                JLabel nameLabel = new JLabel("Product Name:");
                JTextField nameField = new JTextField(selectedProduct.getName());
                JLabel descriptionLabel = new JLabel("Description:");
                JTextField descriptionField = new JTextField(selectedProduct.getDescription());
                JLabel categoryLabel = new JLabel("Category:");
                JComboBox<String> categoryComboBox = new JComboBox<>(new String[]{"Fresh Foods", "Frozen Foods", "Dairy", "Meats", "Beverages"});
                categoryComboBox.setSelectedItem(selectedProduct.getCategory());
                JLabel subcategoryLabel = new JLabel("Subcategory:");
                JComboBox<String> subcategoryComboBox = new JComboBox<>(new String[]{"Fruits", "Vegetables", "Fish", "Meats"});
                subcategoryComboBox.setSelectedItem(selectedProduct.getSubcategory());
                JLabel priceLabel = new JLabel("Price:");
                JTextField priceField = new JTextField(String.valueOf(selectedProduct.getPrice()));
                JLabel quantityLabel = new JLabel("Quantity:");
                JTextField quantityField = new JTextField(String.valueOf(selectedProduct.getQuantity()));

                JButton updateButton = new JButton("Update Product");
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
                            JOptionPane.showMessageDialog(null, "Invalid price or quantity.");
                            return;
                        }


                        finalSelectedProduct.setName(name);
                        finalSelectedProduct.setDescription(description);
                        finalSelectedProduct.setCategory(category);
                        finalSelectedProduct.setSubCategory(subcategory);
                        finalSelectedProduct.setPrice(price);
                        finalSelectedProduct.setQuantity(quantity);


                        FileHandler.saveProducts(products);

                        JOptionPane.showMessageDialog(null, "Product updated successfully.");
                        editFormFrame.dispose(); // Close the form
                    }
                });
            }
        }
    }
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

    private List<Product> getUnavailableProducts(List<Product> products) {
        List<Product> unavailableProducts = new ArrayList<>();
        for (Product product : products) {
            if (product.getQuantity() == 0) {
                unavailableProducts.add(product);
            }
        }
        return unavailableProducts;
    }

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

