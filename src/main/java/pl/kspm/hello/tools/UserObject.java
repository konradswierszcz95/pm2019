package pl.kspm.hello.tools;

//Obiekt do przekazywania pobranych zmiennych uzytkownika w wygodny sposob
public class UserObject {

    private String firstName;
    private String lastName;
    private String email;

    private String country;
    private String locality;
    private String street;
    private String zipCode;
    private String postLocality;
    private String houseNumber;

    private String phone;


    public String getFirstName() {
        return firstName;
    }

    public UserObject setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserObject setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserObject setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public UserObject setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getLocality() {
        return locality;
    }

    public UserObject setLocality(String locality) {
        this.locality = locality;
        return this;
    }

    public String getStreet() {
        return street;
    }

    public UserObject setStreet(String street) {
        this.street = street;
        return this;
    }

    public String getZipCode() {
        return zipCode;
    }

    public UserObject setZipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public String getPostLocality() {
        return postLocality;
    }

    public UserObject setPostLocality(String postLocality) {
        this.postLocality = postLocality;
        return this;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public UserObject setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public UserObject setPhone(String phone) {
        this.phone = phone;
        return this;
    }
}
