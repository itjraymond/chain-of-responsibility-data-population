package ca.jent.prepopulation;

import java.time.LocalDate;

public class SourceCDto {

    private Long id;
    private String firstname;
    private String lastname;
    private LocalDate dob;
    private Double salary;
    private String email;

    private static Long idGen = 30L;

    public SourceCDto() {
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

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
