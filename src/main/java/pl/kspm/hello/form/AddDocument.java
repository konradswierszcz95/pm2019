package pl.kspm.hello.form;

import org.springframework.web.multipart.MultipartFile;

public class AddDocument {
    private MultipartFile document;
    private String description;
    private String localisation;

    public AddDocument() {super();
    }

    public MultipartFile getDocument() {
        return document;
    }

    public void setDocument(MultipartFile document) {
        this.document = document;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }
}
