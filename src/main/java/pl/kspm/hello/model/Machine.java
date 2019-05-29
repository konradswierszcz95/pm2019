package pl.kspm.hello.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
    private String documentation;
    private String description;
    private String code;

    
}
