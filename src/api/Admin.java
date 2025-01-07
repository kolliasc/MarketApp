package api;

import java.io.Serializable;
import java.util.List;

/**
 * Η κλάση {@code Admin} υλοποιεί τη λειτουργικότητα ενός διαχειριστή (Admin) του συστήματος.
 * Επεκτείνει την κλάση {@link User} και προσθέτει επιπλέον δυνατότητες για τη διαχείριση χρηστών.
 * Η κλάση είναι {@link Serializable} για αποθήκευση της κατάστασης.
 */

public class Admin extends User implements Serializable {
    private static final long serialVersionUID = 6643028710903648976L;

    /**
     * Κατασκευαστής της κλάσης {@code Admin}.
     * Δημιουργεί έναν νέο διαχειριστή με όνομα χρήστη και κωδικό πρόσβασης.
     *
     * @param username Το όνομα χρήστη του διαχειριστή.
     * @param password Ο κωδικός πρόσβασης του διαχειριστή.
     */

    public Admin(String username, String password) {
        super(username, password, "admin");
    }

    /**
     * Μέθοδος για την προβολή όλων των χρηστών.
     *
     * @param users Λίστα χρηστών για να την εμφανίσει ο διαχειριστής.
     */
    public void viewAllUsers(List<User> users) {
        System.out.println("---- Όλοι οι Χρήστες ----");
        for (User user : users) {
            System.out.println(user.getUsername() + " - " + user.getRole());
        }
    }

    /**
     * Μέθοδος για την προσθήκη νέου χρήστη.
     *
     * @param users Λίστα χρηστών στην οποία θα προστεθεί ο νέος χρήστης.
     * @param newUser Ο νέος χρήστης που θα προστεθεί.
     */
    public void addUser(List<User> users, User newUser) {
        users.add(newUser);
        FileHandler.saveUsers(users);
        System.out.println("Ο χρήστης " + newUser.getUsername() + " προστέθηκε επιτυχώς.");
    }

    /**
     * Μέθοδος για την διαγραφή χρήστη.
     *
     * @param users Λίστα χρηστών από την οποία θα διαγραφεί ο χρήστης.
     * @param username Το username του χρήστη που θέλουμε να διαγράψουμε.
     */
    public void deleteUser(List<User> users, String username) {
        User userToRemove = null;
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                userToRemove = user;
                break;
            }
        }

        if (userToRemove != null) {
            users.remove(userToRemove);
            FileHandler.saveUsers(users);
            System.out.println("Ο χρήστης " + username + " διαγράφηκε επιτυχώς.");
        } else {
            System.out.println("Ο χρήστης με όνομα " + username + " δεν βρέθηκε.");
        }
    }


}
