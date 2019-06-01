package pl.kspm.hello.model;

import javax.persistence.*;

@Entity
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Machine machineDocument;

    private String description;
    private String fileName;
    private String paperCopyLocalisation;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Machine getMachineDocument() {
        return machineDocument;
    }

    public Document setMachineDocument(Machine machineDocument) {
        this.machineDocument = machineDocument;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Document setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getFileName() {
        return fileName;
    }

    public Document setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public String getPaperCopyLocalisation() {
        return paperCopyLocalisation;
    }

    public Document setPaperCopyLocalisation(String paperCopyLocalisation) {
        this.paperCopyLocalisation = paperCopyLocalisation;
        return this;
    }
}
