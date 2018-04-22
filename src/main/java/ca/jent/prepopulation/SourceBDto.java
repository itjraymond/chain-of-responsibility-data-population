package ca.jent.prepopulation;

import java.time.LocalDate;

public class SourceBDto {

    private Long id;
    private String firstname;
    private String lastname;
    private LocalDate dob;

    private static Long idGen = 20L;

    public SourceBDto() {
        this.id = idGen++;
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

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }
}
