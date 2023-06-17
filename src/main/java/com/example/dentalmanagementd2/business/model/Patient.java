package com.example.dentalmanagementd2.business.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Muamer Maslo
 */
@Entity
@Table(name = "patient")
@NamedQueries({
        @NamedQuery(name = "Patient.findAll", query = "SELECT p FROM Patient p"),
        @NamedQuery(name = "Patient.findById", query = "SELECT p FROM Patient p WHERE p.id = :id"),
        @NamedQuery(name = "Patient.findByName", query = "SELECT p FROM Patient p WHERE p.name = :name"),
        @NamedQuery(name = "Patient.findBySurname", query = "SELECT p FROM Patient p WHERE p.surname = :surname"),
        @NamedQuery(name = "Patient.findByPhone", query = "SELECT p FROM Patient p WHERE p.phone = :phone"),
        @NamedQuery(name = "Patient.findByEmail", query = "SELECT p FROM Patient p WHERE p.email = :email")})
public class Patient implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "surname")
    private String surname;
    @Basic(optional = false)
    @Column(name = "phone")
    private int phone;
    @Basic(optional = false)
    @Column(name = "email")
    private String email;
    @JoinTable(name = "user_patient", joinColumns = {
            @JoinColumn(name = "id_patient", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "id_user", referencedColumnName = "id")})
    @ManyToMany
    private List<User> userList;
    @JoinTable(name = "patient_type_of_checkup", joinColumns = {
            @JoinColumn(name = "id_patient", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "id_type_of_checkup", referencedColumnName = "id")})
    @ManyToMany
    private List<TypeOfCheckup> typeOfCheckupList;
    @ManyToMany(mappedBy = "patientList")

    private List<Appointment> appointmentList;

    public Patient() {
    }

    public Patient(Integer id) {
        this.id = id;
    }

    public Patient(Integer id, String name, String surname, int phone, String email) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<TypeOfCheckup> getTypeOfCheckupList() {
        return typeOfCheckupList;
    }

    public void setTypeOfCheckupList(List<TypeOfCheckup> typeOfCheckupList) {
        this.typeOfCheckupList = typeOfCheckupList;
    }

    public List<Appointment> getAppointmentList() {
        return appointmentList;
    }

    public void setAppointmentList(List<Appointment> appointmentList) {
        this.appointmentList = appointmentList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Patient)) {
            return false;
        }
        Patient other = (Patient) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phone=" + phone +
                ", email='" + email + '\'' +
                ", userList=" + userList +
                ", typeOfCheckupList=" + typeOfCheckupList +
                ", appointmentList=" + appointmentList +
                '}';
    }



}
