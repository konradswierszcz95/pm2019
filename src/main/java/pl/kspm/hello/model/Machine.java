package pl.kspm.hello.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
public class Machine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String producer;
    private String model;
    private String serviceNumber;
    private String building;
    private String localisation;
    private String description;
    private String code;
    private Timestamp added;
    private String foto;

    @OneToMany(mappedBy = "machineDocument")
    private List<Document> documents;

    @OneToMany(mappedBy = "brokenMachine")
    private List<Failure> failures;

    public long getId() {
        return id;
    }

    public Machine setId(long id) {
        this.id = id;
        return this;
    }

    public String getProducer() {
        return producer;
    }

    public Machine setProducer(String producer) {
        this.producer = producer;
        return this;
    }

    public String getModel() {
        return model;
    }

    public Machine setModel(String model) {
        this.model = model;
        return this;
    }

    public String getServiceNumber() {
        return serviceNumber;
    }

    public Machine setServiceNumber(String serviceNumber) {
        this.serviceNumber = serviceNumber;
        return this;
    }

    public String getBuilding() {
        return building;
    }

    public Machine setBuilding(String building) {
        this.building = building;
        return this;
    }

    public String getLocalisation() {
        return localisation;
    }

    public Machine setLocalisation(String localisation) {
        this.localisation = localisation;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Machine setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getCode() {
        return code;
    }

    public Machine setCode(String code) {
        this.code = code;
        return this;
    }

    public Timestamp getAdded() {
        return added;
    }

    public Machine setAdded(Timestamp added) {
        this.added = added;
        return this;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public List<Failure> getFailures() {
        return failures;
    }

    public void setFailures(List<Failure> failures) {
        this.failures = failures;
    }
}
