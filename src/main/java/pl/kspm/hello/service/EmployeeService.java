package pl.kspm.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.kspm.hello.config.UserContext;
import pl.kspm.hello.model.*;
import pl.kspm.hello.repository.AddressRepository;
import pl.kspm.hello.repository.EmployeeRepository;
import pl.kspm.hello.repository.UserConnectorRepository;
import pl.kspm.hello.tools.*;

import java.sql.Timestamp;
import java.util.Date;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmailSenderService ess;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserConnectorRepository userConnectorRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String errorMessage (int errorCode) {
        //Informacje zwrotne po próbie dodania pracownika
        switch (errorCode) {
            case 0:
                return "Dodano pracownika";
            case 1:
                return "Adres E-mail jest zajęty";
            case 2:
                return "Imię zawiera niedozwolone znaki";
            case 3:
                return "Nazwisko zawiera niedozwolone znaki";
            case 4:
                return "Podany E-mail jest niepoprawny";
            case 5:
                return "Imię jest za krótkie";
            case 6:
                return "Nazwisko jest za krótkie";
            default:
                return "Taki adres błędu nie istnieje!";

        }
    }

    public int addNewEmployee(UserObject userObject){
        StringCheck spc = new StringCheck();
        //Tworzenie nowego pracownika na podstawie podanych wartości
        Employee e = new Employee();
        e.setName(userObject.getFirstName())
                .setSurname(userObject.getLastName())
                .setEmail(userObject.getEmail());

        Address address = new Address();
        address.setCountry(userObject.getCountry())
                .setHouseNumber(userObject.getHouseNumber())
                .setLocality(userObject.getLocality())
                .setStreet(userObject.getStreet())
                .setZipCode(userObject.getZipCode())
                .setPostLocality(userObject.getPostLocality());

        Account account = new Account();
        account.setActive(false)
                .setCreated(new Timestamp(new Date().getTime()));

        User user = new User();
        user.setEmployee(e)
                .setAddress(address)
                .setAccount(account);
        //===========================================================

        //Automatyczne utworzenie loginu i dodanie do obiektu pracownika
        CreateLogin cl = new CreateLogin(userObject.getFirstName(),userObject.getLastName(),employeeRepository);
        String login = cl.create();
        e.setLogin(login);
        //===========================================================

        //Generowanie jednorazowego tokena do pierwszego logowania
        TokenGenerator tokenGenerator = new TokenGenerator();
        String token = tokenGenerator.generateToken();
        e.setPassword(passwordEncoder.encode(token));
        //===========================================================

        e.setPhone(userObject.getPhone());

        //Formułowanie treści e-maila rejestracyjnego
        String content = "Witaj "+userObject.getFirstName()+" "+userObject.getLastName();
        content+="\nTa wiadomość potwierdza założenie Twojego konta w systemie firmy Praca Magisterska";
        content+="\nDane do logowania:";
        content+="\nlogin: "+login;
        content+="\nhasło: "+token;
        content+="\nHasło zostało wygenerowane automatycznie i wymagana będzie jego zmiana podczas pierwszego logowania.";
        content+="\n====================================================================================================";
        content+="\nJeżeli ta wiadomość nie była adresowana do Ciebie, prosimy zgłoś to pod adresem: zgłoś@dupa.pl";
        //===========================================================

        //Ustawienie kodu błędu informacji zwortnej dla osoby tworzącej użytkownika
        if (employeeRepository.existsByEmail(userObject.getEmail())) {
            return 1;
        } else if (spc.containsSpecialSign(userObject.getFirstName())) {
            return 2;
        } else if (spc.containsSpecialSign(userObject.getLastName())) {
            return 3;
        } else if (!spc.emailIsCorrect(userObject.getEmail())) {
            return 4;
        } else if (userObject.getFirstName().length()<3) {
            return 5;
        } else if (userObject.getLastName().length()<3) {
            return 6;
        } else {
            sendEmail(userObject.getEmail(),content,token);
            this.userConnectorRepository.save(user);
            return 0;
        }
        //====================================================================
    }

    public Iterable<User> getAllEmployees() {
        Iterable<User> users =  this.userConnectorRepository.findAll();
        return users;
    }

    public void sendEmail(String email, String content, String token) {
        ess.sendEmail(email,"Praca Magisterska - konto zostało utworzone",content);
    }

    public User getUserById(long id) {
        return this.userConnectorRepository.findFirstById(id);
    }
}
