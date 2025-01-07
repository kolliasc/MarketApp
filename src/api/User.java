
package api;

import java.io.Serializable;

/**
 * Η κλάση User είναι μια αφηρημένη κλάση που αναπαριστά έναν χρήστη του συστήματος.
 * Η κλάση αυτή περιλαμβάνει τα χαρακτηριστικά του χρήστη, όπως το όνομα χρήστη, τον κωδικό πρόσβασης και τον ρόλο του χρήστη.
 * Οι υποκλάσεις της κλάσης αυτής θα ορίζουν τον τύπο του χρήστη και τη συμπεριφορά του, όπως π.χ. διαχειριστές ή απλοί χρήστες.
 */
public abstract class User  implements Serializable {
    private String username;
    private String password;
    private String role;


    /**
     * Δημιουργεί έναν νέο χρήστη με το καθορισμένο όνομα χρήστη, κωδικό πρόσβασης και ρόλο.
     *
     * @param username Το όνομα χρήστη.
     * @param password Ο κωδικός πρόσβασης.
     * @param role Ο ρόλος του χρήστη (π.χ. "admin", "user").
     */
    public User(String username,String password,String role){
        this.username = username;
        this.password = password;
        this.role = role;
    }

    /**
     * Επιστρέφει το όνομα χρήστη του χρήστη.
     *
     * @return Το όνομα χρήστη του χρήστη.
     */
    public String getUsername(){return username;}

    /**
     * Επιστρέφει τον κωδικό πρόσβασης του χρήστη.
     *
     * @return Ο κωδικός πρόσβασης του χρήστη.
     */
    public  String getPassword(){return password;}

    /**
     * Επιστρέφει τον ρόλο του χρήστη (π.χ. "admin", "user").
     *
     * @return Ο ρόλος του χρήστη.
     */
    public String getRole(){return role;}
}
