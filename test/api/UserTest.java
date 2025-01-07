package api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class για την κλάση User.
 */
public class UserTest {

    private User testUser;

    /**
     * Δημιουργία ενός test χρήστη μέσω προσωρινής κλάσης.
     */
    @BeforeEach
    void setUp() {
        testUser = new TestUser("testUser", "testPassword", "user");
    }

    @Test
    void getUsername() {
        assertEquals("testUser", testUser.getUsername(), "Το όνομα χρήστη δεν είναι σωστό.");
    }

    @Test
    void getPassword() {
        assertEquals("testPassword", testUser.getPassword(), "Ο κωδικός πρόσβασης δεν είναι σωστός.");
    }

    @Test
    void getRole() {
        assertEquals("user", testUser.getRole(), "Ο ρόλος του χρήστη δεν είναι σωστός.");
    }

    /**
     * Προσωρινή κλάση που επεκτείνει την User για testing.
     */
    private static class TestUser extends User {
        public TestUser(String username, String password, String role) {
            super(username, password, role);
        }
    }
}