package com.example.dentalmanagementd2.business.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Muamer Maslo
 */
@Entity
@Table(name = "appointment")
@NamedQueries({
        @NamedQuery(name = "Appointment.findAll", query = "SELECT a FROM Appointment a"),
        @NamedQuery(name = "Appointment.findById", query = "SELECT a FROM Appointment a WHERE a.id = :id"),
        @NamedQuery(name = "Appointment.findByDate", query = "SELECT a FROM Appointment a WHERE a.date = :date"),
        @NamedQuery(name = "Appointment.findByTime", query = "SELECT a FROM Appointment a WHERE a.time = :time")})
public class Appointment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "date")
    //@Temporal(TemporalType.DATE)
    private LocalDate date;
    @Column(name = "time")
    //@Temporal(TemporalType.TIME)
    private LocalTime time;
    @JoinTable(name = "patient_appointment", joinColumns = {
            @JoinColumn(name = "id_appointment", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "id_patient", referencedColumnName = "id")})
    @ManyToMany
    private List<Patient> patientList;

    public Appointment() {
    }

    public Appointment(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public List<Patient> getPatientList() {
        return patientList;
    }

    public void setPatientList(List<Patient> patientList) {
        this.patientList = patientList;
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
        if (!(object instanceof Appointment)) {
            return false;
        }
        Appointment other = (Appointment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", date=" + date +
                ", time=" + time +
                ", patientList=" + patientList +
                '}';
    }
}
