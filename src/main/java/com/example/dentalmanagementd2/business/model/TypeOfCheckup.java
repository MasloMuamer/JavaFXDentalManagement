package com.example.dentalmanagementd2.business.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name = "type_of_checkup")
@NamedQueries({
        @NamedQuery(name = "TypeOfCheckup.findAll", query = "SELECT t FROM TypeOfCheckup t"),
        @NamedQuery(name = "TypeOfCheckup.findById", query = "SELECT t FROM TypeOfCheckup t WHERE t.id = :id"),
        @NamedQuery(name = "TypeOfCheckup.findByName", query = "SELECT t FROM TypeOfCheckup t WHERE t.name = :name"),
        @NamedQuery(name = "TypeOfCheckup.findByDescription", query = "SELECT t FROM TypeOfCheckup t WHERE t.description = :description")})
public class TypeOfCheckup implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @ManyToMany(mappedBy = "typeOfCheckupList")
    private List<Patient> patientList;

    public TypeOfCheckup() {
    }

    public TypeOfCheckup(Integer id) {
        this.id = id;
    }

    public TypeOfCheckup(Integer id, String name) {
        this.id = id;
        this.name = name;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        if (!(object instanceof TypeOfCheckup)) {
            return false;
        }
        TypeOfCheckup other = (TypeOfCheckup) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getName();
    }

  //  @Override
  //  public String toString() {
  //      return "TypeOfCheckup{" +
  //              "id=" + id +
  //              ", name='" + name + '\'' +
  //              ", description='" + description + '\'' +
  //              ", patientList=" + patientList +
  //              '}';
  //  }
}

