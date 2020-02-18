package com.techprimers.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ElectioncardData {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;
    @Column(name = "voter_id ")
    private String voterId;
    @Column(name = "name")
    private String voterName;
    @Column(name = "father_name")
    private String voterFatherName;
    @Column(name = "Gender")
    private String voterGender;
    @Column(name = "birth_date")
    private String voterBirthdate;
    @Column(name = "address")
    private String voterAddress;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVoterId() {
        return voterId;
    }

    public void setVoterId(String voterId) {
        this.voterId = voterId;
    }

    public String getVoterName() {
        return voterName;
    }

    public void setVoterName(String voterName) {
        this.voterName = voterName;
    }

    public String getVoterFatherName() {
        return voterFatherName;
    }

    public void setVoterFatherName(String voterFatherName) {
        this.voterFatherName = voterFatherName;
    }

    public String getVoterGender() {
        return voterGender;
    }

    public void setVoterGender(String voterGender) {
        this.voterGender = voterGender;
    }

    public String getVoterBirthdate() {
        return voterBirthdate;
    }

    public void setVoterBirthdate(String voterBirthdate) {
        this.voterBirthdate = voterBirthdate;
    }

    public String getVoterAddress() {
        return voterAddress;
    }

    public void setVoterAddress(String voterAddress) {
        this.voterAddress = voterAddress;
    }
}
