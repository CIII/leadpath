package com.pony.callcenter;

/**
 * ArbVentures 2013.
 * User: martin
 * Date: 3/27/14
 * Time: 2:32 PM
 */
public class Broker {
    private final Long id;
    private final String firstName, lastName, email, phone, brokerId;

    public Broker() {
        this(null, null, null, null, null, null);
    }

    private Broker(Long id, String firstName, String lastName, String email, String phone, String brokerId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.brokerId = brokerId;
    }

    public static Broker create(Long id, String firstName, String lastName, String email, String phone, String brokerId) {
        return new Broker(id, firstName, lastName, email, phone, brokerId);
    }


    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getBrokerId() {
        return brokerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Broker broker = (Broker) o;

        if (!id.equals(broker.id)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Broker{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", brokerId='" + brokerId + '\'' +
                '}';
    }
}
