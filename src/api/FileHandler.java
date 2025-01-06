package api;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Η κλάση {@code FileHandler} παρέχει λειτουργίες για την αποθήκευση και ανάγνωση δεδομένων από αρχεία.
 * Συγκεκριμένα, διαχειρίζεται χρήστες, προϊόντα και παραγγελίες που αποθηκεύονται σε αρχεία bin.
 */
public class FileHandler {

    private static final String USERS_FILE = "users.bin";
    private static final String PRODUCTS_FILE = "products.bin";
    private static final String ORDERS_FILE = "orders.bin";

    /**
     * Φορτώνει τη λίστα χρηστών από το αρχείο {@code users.bin}.
     *
     * @return Λίστα χρηστών.
     */
    public static List<User> loadUsers() {
        return readFromFile(USERS_FILE);
    }

    /**
     * Φορτώνει τη λίστα προϊόντων από το αρχείο {@code products.bin}.
     *
     * @return Λίστα προϊόντων.
     */
    public static List<Product> loadProducts() {
        return readFromFile(PRODUCTS_FILE);
    }

    /**
     * Φορτώνει τη λίστα παραγγελιών από το αρχείο {@code orders.bin}.
     *
     * @return Λίστα παραγγελιών.
     */
    public static List<Order> loadOrders() {
        return readFromFile(ORDERS_FILE);
    }

    /**
     * Αποθηκεύει τη λίστα χρηστών στο αρχείο {@code users.bin}.
     *
     * @param users Λίστα χρηστών που θα αποθηκευτεί.
     */
    public static void saveUsers(List<User> users) {
        writeToFile(USERS_FILE, users);
    }

    /**
     * Αποθηκεύει τη λίστα προϊόντων στο αρχείο {@code products.bin}.
     *
     * @param products Λίστα προϊόντων που θα αποθηκευτεί.
     */
    public static void saveProducts(List<Product> products) {
        writeToFile(PRODUCTS_FILE, products);
    }

    /**
     * Αποθηκεύει τη λίστα παραγγελιών στο αρχείο {@code orders.bin}.
     *
     * @param orders Λίστα παραγγελιών που θα αποθηκευτεί.
     */
    public static void saveOrders(List<Order> orders) {
        writeToFile(ORDERS_FILE, orders);
    }

    /**
     * Ελέγχει αν ο χρήστης με συγκεκριμένο όνομα χρήστη και κωδικό υπάρχει στο αρχείο χρηστών.
     *
     * @param username Το όνομα χρήστη.
     * @param password Ο κωδικός πρόσβασης.
     * @return {@code true} αν ο χρήστης υπάρχει, {@code false} αν δεν υπάρχει.
     */
    public static boolean userExists(String username, String password) {
        List<User> users = loadUsers();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Προσθέτει έναν νέο χρήστη στη λίστα χρηστών και αποθηκεύει τη νέα λίστα.
     *
     * @param newUser Ο νέος χρήστης που θα προστεθεί.
     */
    public static void addUser(User newUser) {
        List<User> users = loadUsers();
        users.add(newUser);
        saveUsers(users);
    }

    /**
     * Προσθέτει ένα νέο προϊόν στη λίστα προϊόντων και αποθηκεύει τη νέα λίστα.
     *
     * @param newProduct Το νέο προϊόν που θα προστεθεί.
     */
    public static void addProduct(Product newProduct) {
        List<Product> products = loadProducts();
        products.add(newProduct);
        saveProducts(products);
    }

    /**
     * Αρχικοποιεί προεπιλεγμένους διαχειριστές στο σύστημα, εάν δεν υπάρχουν ήδη.
     */
    public static void initializeDefaultAdmins() {
        List<User> users = loadUsers();
        boolean admin1Exists = users.stream().anyMatch(user -> user.getUsername().equals("admin1"));
        boolean admin2Exists = users.stream().anyMatch(user -> user.getUsername().equals("admin2"));

        if (!admin1Exists) {
            users.add(new Admin("admin1", "password1"));
        }
        if (!admin2Exists) {
            users.add(new Admin("admin2", "password2"));
        }

        saveUsers(users);
    }

    /**
     * Αρχικοποιεί προεπιλεγμένους πελάτες στο σύστημα, εάν δεν υπάρχουν ήδη.
     */
    public static void initializeDefaultCustomers() {
        List<User> users = loadUsers();
        boolean user1Exists = users.stream().anyMatch(user -> user.getUsername().equals("user1"));
        boolean user2Exists = users.stream().anyMatch(user -> user.getUsername().equals("user2"));

        if (!user1Exists) {
            users.add(new Customer("user1", "password1"));
        }
        if (!user2Exists) {
            users.add(new Customer("user2", "password2"));
        }

        saveUsers(users);
    }

    /**
     * Αρχικοποιεί προεπιλεγμένα προϊόντα στο σύστημα, εάν δεν υπάρχουν ήδη.
     */
    public static void initializeDefaultProducts() {
        List<Product> products = loadProducts();

        if (!products.isEmpty()) {
            return;
        }

        products.add(new Product("Πορτοκάλια 1kg", "Φρέσκα πορτοκάλια, ιδανικά για χυμό ή κατανάλωση.",
                "Φρέσκα τρόφιμα", "Φρούτα", 1.20, 200));
        products.add(new Product("Καρότα 1kg", "Τραγανά καρότα, κατάλληλα για σαλάτες και μαγείρεμα.",
                "Φρέσκα τρόφιμα", "Λαχανικά", 1.00, 150));
        products.add(new Product("Φιλέτο Σολομού 300g", "Φρέσκος σολομός φιλέτο έτοιμος για μαγείρεμα.",
                "Φρέσκα τρόφιμα", "Ψάρια", 12.00, 50));
        products.add(new Product("Κιμάς Μοσχαρίσιος 500g", "Φρέσκος κιμάς μοσχαρίσιος από τοπικό κρεοπωλείο.",
                "Φρέσκα τρόφιμα", "Κρέατα", 6.50, 100));

        products.add(new Product("Κατεψυγμένες Πίτσες Margherita 400g", "Πίτσα Margherita με φρέσκια σάλτσα ντομάτας και τυρί.",
                "Κατεψυγμένα τρόφιμα", "Κατεψυγμένες πίτσες", 4.00, 80));
        products.add(new Product("Κατεψυγμένα Γεύματα Σπαγγέτι Μπολονέζ 450g", "Έτοιμο κατεψυγμένο γεύμα σπαγγέτι μπολονέζ.",
                "Κατεψυγμένα τρόφιμα", "Κατεψυγμένα γεύματα", 3.80, 60));

        products.add(new Product("Γιαούρτι Στραγγιστό 2% Λιπαρά 200g", "Ελαφρύ στραγγιστό γιαούρτι με χαμηλά λιπαρά.",
                "Προϊόντα ψυγείου", "Γιαούρτια", 1.80, 200));
        products.add(new Product("Γάλα Πλήρες 1lt", "Πλήρες γάλα με πλούσια γεύση και θρεπτικά συστατικά.",
                "Προϊόντα ψυγείου", "Γάλα", 1.30, 250));

        products.add(new Product("Σαλάμι Αέρος 200g", "Αυθεντικό σαλάμι αέρος με έντονη γεύση.",
                "Αλλαντικά", "Σαλάμι", 3.50, 70));
        products.add(new Product("Μπέικον Καπνιστό 200g", "Καπνιστό μπέικον για μαγείρεμα ή σάντουιτς.",
                "Αλλαντικά", "Μπέικον", 3.20, 90));

        products.add(new Product("Κρασί Ερυθρό Ξηρό 750ml", "Ερυθρό ξηρό κρασί από ελληνικά σταφύλια.",
                "Αλκοολούχα ποτά", "Κρασί", 7.00, 120));
        products.add(new Product("Ούζο Πλωμαρίου 200ml", "Παραδοσιακό ούζο από τη Λέσβο με γλυκάνισο.",
                "Αλκοολούχα ποτά", "Ούζο", 5.00, 150));

        products.add(new Product("Αναψυκτικό Coca Cola 1,5lt", "Κλασικό αναψυκτικό Coca Cola με ζάχαρη.",
                "Μη αλκοολούχα ποτά", "Αναψυκτικά", 1.30, 300));
        products.add(new Product("Νερό Μεταλλικό 1,5lt", "Φυσικό μεταλλικό νερό από ελληνικές πηγές.",
                "Μη αλκοολούχα ποτά", "Νερό", 0.50, 500));

        products.add(new Product("Καθαριστικό Τζαμιών 750ml", "Αποτελεσματικό καθαριστικό για τζάμια και καθρέπτες.",
                "Καθαριστικά για το σπίτι", "Καθαριστικά για τα τζάμια", 2.50, 180));
        products.add(new Product("Καθαριστικό Κουζίνας 500ml", "Ισχυρό καθαριστικό για επιφάνειες κουζίνας.",
                "Καθαριστικά για το σπίτι", "Καθαριστικά κουζίνας", 3.00, 150));

        saveProducts(products);
    }

    /**
     * Ελέγχει αν ο χρήστης είναι διαχειριστής.
     *
     * @param username Το όνομα χρήστη.
     * @return {@code true} αν ο χρήστης είναι διαχειριστής, {@code false} αλλιώς.
     */
    public static boolean isAdmin(String username) {
        List<User> users = loadUsers();
        for (User user : users) {
            if (user.getUsername().equals(username) && user instanceof Admin) {
                return true;
            }
        }
        return false;
    }

    /**
     * Διαβάζει δεδομένα από ένα αρχείο και επιστρέφει τη λίστα αντικειμένων.
     *
     * @param fileName Το όνομα του αρχείου από το οποίο θα διαβαστούν τα δεδομένα.
     * @param <T>      Ο τύπος των αντικειμένων που αποθηκεύονται στο αρχείο.
     * @return Η λίστα των αντικειμένων που διαβάστηκαν από το αρχείο.
     */
    @SuppressWarnings("unchecked")
    private static <T> List<T> readFromFile(String fileName) {
        File file = new File(fileName);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return (List<T>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            if (e instanceof InvalidClassException) {
                file.delete();
            }
            return new ArrayList<>();
        }
    }

    /**
     * Γράφει δεδομένα σε ένα αρχείο.
     *
     * @param fileName Το όνομα του αρχείου στο οποίο θα γραφούν τα δεδομένα.
     * @param data     Τα δεδομένα που θα αποθηκευτούν.
     */
    private static void writeToFile(String fileName, Object data) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ενημερώνει την ποσότητα ενός προϊόντος στο αρχείο προϊόντων.
     *
     * @param product Το προϊόν που πρέπει να ενημερωθεί.
     */
    public static void updateProductQuantity(Product product) {
        List<Product> products = loadProducts();

        for (Product p : products) {
            if (p.getName().equals(product.getName()) && p.getCategory().equals(product.getCategory()) && p.getSubcategory().equals(product.getSubcategory())) {
                p.setQuantity(p.getQuantity() - product.getQuantity());
                break;
            }
        }

        saveProducts(products);
    }

    /**
     * Αποθηκεύει μια παραγγελία στο αρχείο παραγγελιών.
     *
     * @param order Η παραγγελία που θα αποθηκευτεί.
     */
    public static void saveOrder(Order order) {
        List<Order> orders = loadOrders();
        orders.add(order);
        saveOrders(orders);
    }
}
