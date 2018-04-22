package ca.jent.prepopulation;

public class User {

    private Long id;
    private String firstname;
    private String lastname;
    private String username;

    private static Long idGen = 1L;

    public User() {
        this.id  = idGen++;
    }

    public Long getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
