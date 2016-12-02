package m1.jee.model;

public class BeanMember {
    
    /**
     * Ussername
     */
    private String username;
    
    /**
     * Password
     */
    private String password;

    /**
     * Set username
     * @param username String
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Set password
     * @param password 
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get username
     * @return String
     */
    public String getUsername() {
        return username;
    }
    
    /**
     * Get password
     * @return String
     */
    public String getPassword() {
        return password;
    }
}
