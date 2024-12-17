package org.poo.packagePOO.Bank;

public class User {
    private final String firstName;
    private final String lastName;
    private final String email;

    /**
     *
     * @param firstName
     * @param lastName
     * @param email
     */
    public User(final String firstName, final String lastName, final String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    /**
     *
     * @return
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     *
     * @return
     */
    public String getLastName() {
        return lastName;
    }

    /**
     *
     * @return
     */
    public String getEmail() {
        return email;
    }
}
