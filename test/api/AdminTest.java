package api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class για την κλάση Admin.
 */
class AdminTest {

    private Admin admin;
    private List<User> userList;

    @BeforeEach
    void setUp() {
        admin = new Admin("admin", "admin123");
        userList = new ArrayList<>();

        // Προσθήκη αρχικών χρηστών
        userList.add(new Customer("user1", "password1"));
        userList.add(new Customer("user2", "password2"));
    }

    @Test
    void viewAllUsers() {
        // Προσθήκη ενός ακόμα χρήστη
        userList.add(new Customer("user3", "password3"));

        // Έλεγχος της προβολής όλων των χρηστών
        admin.viewAllUsers(userList);
        assertEquals(3, userList.size(), "Ο συνολικός αριθμός των χρηστών πρέπει να είναι 3.");
    }

    @Test
    void addUser() {
        // Δημιουργία νέου χρήστη
        User newUser = new Customer("newUser", "newPassword");

        // Προσθήκη του χρήστη
        admin.addUser(userList, newUser);

        // Έλεγχος ότι ο χρήστης προστέθηκε
        assertEquals(3, userList.size(), "Ο συνολικός αριθμός των χρηστών πρέπει να είναι 3 μετά την προσθήκη.");
        assertTrue(userList.contains(newUser), "Η λίστα πρέπει να περιέχει τον νέο χρήστη.");
    }

    @Test
    void deleteUser_existingUser() {
        // Διαγραφή υπάρχοντος χρήστη
        admin.deleteUser(userList, "user1");

        // Έλεγχος ότι ο χρήστης διαγράφηκε
        assertEquals(1, userList.size(), "Ο συνολικός αριθμός των χρηστών πρέπει να είναι 1 μετά τη διαγραφή.");
        assertFalse(userList.stream().anyMatch(user -> user.getUsername().equals("user1")),
                "Ο χρήστης user1 δεν πρέπει να υπάρχει στη λίστα.");
    }

    @Test
    void deleteUser_nonExistingUser() {
        // Προσπάθεια διαγραφής μη υπαρκτού χρήστη
        admin.deleteUser(userList, "nonExistingUser");

        // Έλεγχος ότι η λίστα παραμένει ίδια
        assertEquals(2, userList.size(), "Ο συνολικός αριθμός των χρηστών δεν πρέπει να αλλάξει.");
    }
}