package api;


public abstract class User{
    private String username;
    private String password;
    private String role;

    /**
     *
     * @param username name of the user
     * @param password password of the user
     * @param role role of the user(admin/customer)
     */

    public User(String username,String password,String role){
        this.username = username;
        this.password = password;
        this.role = role;
    }

    //getters
    Public String getUsername(){return username;}
    Public String getPassword(){return password;}
    Public String getRole(){return role;}

}
