import api.FileHandler;
import gui.Login;


/**
 * Το πρόγραμμά σας πρέπει να έχει μόνο μία main, η οποία πρέπει να είναι η παρακάτω.
 * <p>
 * <p>
 * ************* ΜΗ ΣΒΗΣΕΤΕ ΑΥΤΗ ΤΗΝ ΚΛΑΣΗ ************
 */
public class Main {
    public static void main(String[] args) {

        FileHandler.initializeDefaultAdmins();
        FileHandler.initializeDefaultCustomers();
        FileHandler.initializeDefaultProducts();
        Login startingPage = new Login();


    }
}
