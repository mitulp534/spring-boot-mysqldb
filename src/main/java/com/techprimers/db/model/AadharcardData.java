package com.techprimers.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class AadharcardData {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;
    @Column(name = "aadhar_number")
    private String aadharNumber;
    @Column(name = "name")
    private String aadharName;
    @Column(name = "birth_date")
    private String birthDate;
    @Column(name = "gender")
    private String aadharGender;
    @Column(name = "city")
    private String aadharCity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAadharNumber() {
        return aadharNumber;
    }

    public void setAadharNumber(String aadharNumber) {
        this.aadharNumber = aadharNumber;
    }

    public String getAadharName() {
        return aadharName;
    }

    public void setAadharName(String aadharName) {
        this.aadharName = aadharName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getAadharGender() {
        return aadharGender;
    }

    public void setAadharGender(String aadharGender) {
        this.aadharGender = aadharGender;
    }

    public String getAadharCity() {
        return aadharCity;
    }

    public void setAadharCity(String aadharCity) {
        this.aadharCity = aadharCity;
    }
}
