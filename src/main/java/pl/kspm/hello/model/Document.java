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

    public void setMachineDocument(Machine machineDocument) {
        this.machineDocument = machineDocument;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPaperCopyLocalisation() {
        return paperCopyLocalisation;
    }

    public void setPaperCopyLocalisation(String paperCopyLocalisation) {
        this.paperCopyLocalisation = paperCopyLocalisation;
    }
}
