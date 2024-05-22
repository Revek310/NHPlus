package de.hitec.nhplus.model;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Patients live in a NURSING home and are treated by nurses.
 */
public class Caregiver extends Person {
    private SimpleLongProperty cid;


    public String getUsername() {
        return username.get();
    }

    public SimpleStringProperty usernameProperty() {
        return username;
    }

    public String getPassword() {
        return password.get();
    }

    public SimpleStringProperty passwordProperty() {
        return password;
    }

    private SimpleStringProperty username;
    private SimpleStringProperty password;
    private SimpleStringProperty telephone;

    public String isLocked() {
        return locked.get();
    }

    public SimpleStringProperty lockedProperty() {
        return locked;
    }

    public void setLocked(String locked) {
        this.locked.set(locked);
    }

    private SimpleStringProperty locked;

    private final List<Treatment> allTreatments = new ArrayList<>();

    /**
     * Constructor to initiate an object of class <code>Patient</code> with the given parameter. Use this constructor
     * to initiate objects, which are not persisted yet, because it will not have a patient id (pid).
     *
     * @param firstName First name of the caregiver.
     * @param surname   Last name of the caregiver.
     */
    public Caregiver( String firstName, String surname, String telephone, String username, String password, String locked) {
        super(firstName, surname);
        this.telephone = new SimpleStringProperty(telephone);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.locked = new SimpleStringProperty(locked);
    }

    /**
     * Constructor to initiate an object of class <code>Patient</code> with the given parameter. Use this constructor
     * to initiate objects, which are already persisted and have a patient id (pid).
     *
     * @param cid         Patient id.
     * @param firstName   First name of the patient.
     * @param surname     Last name of the patient.
     * @param telephone
     */
    public Caregiver(long cid, String firstName, String surname, String telephone, String username, String password, String locked) {
        super(firstName, surname);
        this.cid = new SimpleLongProperty(cid);
        this.telephone = new SimpleStringProperty(telephone);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.locked = new SimpleStringProperty(locked);
    }


    public long getCid() {
        return cid.get();
    }

    public SimpleLongProperty cidProperty() {
        return cid;
    }


    /**
     * Stores the given string as new <code>birthOfDate</code>.
     *
     */



    public void setTelephone(String telephone) {
        this.telephone.set(telephone);
    }

    public String getTelephone() {
        return telephone.get();
    }

    /**
     * Adds a treatment to the list of treatments, if the list does not already contain the treatment.
     *
     * @param treatment Treatment to add.
     * @return False, if the treatment was already part of the list, else true.
     */
    public boolean add(Treatment treatment) {
        if (this.allTreatments.contains(treatment)) {
            return false;
        }
        this.allTreatments.add(treatment);
        return true;
    }

    public String toString() {
        return "Patient" + "\nMNID: " + this.cid +
                "\nFirstname: " + this.getFirstName() +
                "\nSurname: " + this.getSurname() +
                "\nTelephone: " + this.telephone +
                "\n";
    }
}