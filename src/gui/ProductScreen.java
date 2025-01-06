package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import api.FileHandler;
import api.Product;
import java.util.List;

/**
 * Η κλάση ProductScreen παρέχει την οθόνη διαχείρισης προϊόντων για την προσθήκη και επεξεργασία προϊόντων.
 * Οι χρήστες μπορούν να προσθέσουν νέα προϊόντα ή να επεξεργαστούν υπάρχοντα προϊόντα από τη λίστα.
 */
public class ProductScreen extends JFrame {
    private JPanel panel1;
    private JTextField productNameField;
    private JTextField productDescriptionField;
    private JTextField productPriceField;
    private JButton addProductButton;
    private JButton editProductButton;
    private JComboBox<Product> productComboBox;

    /**
     * Κατασκευαστής για την οθόνη διαχείρισης προϊόντων.
     * Δημιουργεί και διαχειρίζεται τα οπτικά στοιχεία της οθόνης.
     */
    public ProductScreen() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(400, 300);
        setTitle("Product Management");
        setContentPane(panel1);
        setLocationRelativeTo(null);
        setVisible(true);

        // Φόρτωση προϊόντων στο comboBox
        loadProducts();

        addProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProduct();
            }
        });

        editProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editProduct();
            }
        });
    }

    /**
     * Φορτώνει τα προϊόντα από την πηγή δεδομένων και τα προσθέτει στο comboBox για επιλογή.
     * Ενημερώνει τη λίστα προϊόντων στην οθόνη.
     */
    private void loadProducts() {
        List<Product> products = FileHandler.loadProducts();
        productComboBox.removeAllItems();

        for (Product product : products) {
            productComboBox.addItem(product);
        }
    }

    /**
     * Προσθέτει ένα νέο προϊόν στην πηγή δεδομένων.
     * Διαβάζει τα πεδία εισαγωγής, ελέγχει την εγκυρότητα της τιμής και αποθηκεύει το προϊόν.
     */
    private void addProduct() {
        String name = productNameField.getText();
        String description = productDescriptionField.getText();
        double price;

        try {
            price = Double.parseDouble(productPriceField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid price.");
            return;
        }


        List<Product> products = FileHandler.loadProducts();

        FileHandler.saveProducts(products);

        JOptionPane.showMessageDialog(this, "Product added successfully!");
        loadProducts();
    }

    /**
     * Επεξεργάζεται το επιλεγμένο προϊόν από το comboBox.
     * Διαβάζει τα πεδία εισαγωγής και ενημερώνει το προϊόν με τις νέες πληροφορίες.
     */
    private void editProduct() {
        Product selectedProduct = (Product) productComboBox.getSelectedItem();
        if (selectedProduct != null) {
            selectedProduct.setName(productNameField.getText());
            selectedProduct.setDescription(productDescriptionField.getText());
            try {
                selectedProduct.setPrice(Double.parseDouble(productPriceField.getText()));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter a valid price.");
                return;
            }
            List<Product> products = FileHandler.loadProducts();
            FileHandler.saveProducts(products);

            JOptionPane.showMessageDialog(this, "Product updated successfully!");
            loadProducts();
        }
    }
}
