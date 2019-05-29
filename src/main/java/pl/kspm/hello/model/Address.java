package pl.kspm.hello.model;

import javax.persistence.*;

@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String country; //PANSTWO
    private String locality; //MIEJSCOWOSC
    private String street; //ULICA
    private String zipCode; //KOD POCZTOWY
    private String postLocality; //MIEJSCOWOSC POCZTY
    private String houseNumber; //NUMER DOMU/MIESZKANIA np. 27a/10

    public long getId() {
        return id;
    }

    public Address setId(long id) {
        this.id = id;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public Address setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getLocality() {
        return locality;
    }

    public Address setLocality(String locality) {
        this.locality = locality;
        return this;
    }

    public String getStreet() {
        return street;
    }

    public Address setStreet(String street) {
        this.street = street;
        return this;
    }

    public String getZipCode() {
        return zipCode;
    }

    public Address setZipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public String getPostLocality() {
        return postLocality;
    }

    public Address setPostLocality(String postLocality) {
        this.postLocality = postLocality;
        return this;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public Address setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
        return this;
    }

}
