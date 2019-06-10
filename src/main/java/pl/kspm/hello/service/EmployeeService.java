package pl.kspm.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.kspm.hello.config.UserContext;
import pl.kspm.hello.model.*;
import pl.kspm.hello.repository.EmployeeRepository;
import pl.kspm.hello.repository.RoleInterface;
import pl.kspm.hello.repository.UserRepository;
import pl.kspm.hello.tools.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmailSenderService ess;
    @Autowired
    private RoleInterface roleInterface;
    @Autowired(required = true)
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String addNewEmployee(User user){
        //Automatyczne utworzenie loginu i dodanie do obiektu pracownika
        CreateLogin cl = new CreateLogin(user.getEmployee().getName(),user.getEmployee().getSurname(),employeeRepository);
        user.getEmployee().setLogin(cl.create());
        //===========================================================

        //Generowanie jednorazowego tokena do pierwszego logowania
        String token = TokenGenerator.generateToken();
        user.getEmployee().setPassword(passwordEncoder.encode(token));
        //===========================================================


        //Formułowanie treści e-maila rejestracyjnego
        String content = CreateEmail.form(user.getEmployee().getName(),user.getEmployee().getSurname(),user.getEmployee().getLogin(),token);
        //===========================================================

        //Ustawienie informacji zwortnej dla osoby tworzącej użytkownika
        if (employeeRepository.existsByEmail(user.getEmployee().getEmail())) {
            return "Użytkownik o takim adresie e-mail już istnieje!";
        } else if (StringCheck.containsSpecialSign(user.getEmployee().getName()) || user.getEmployee().getName().length()<3) {
            return "Imię użytkownika jest niepoprawne";
        } else if (StringCheck.containsSpecialSign(user.getEmployee().getSurname()) || user.getEmployee().getSurname().length()<3) {
            return "Nazwisko użytkownika jest niepoprawne";
        } else if (!StringCheck.emailIsCorrect(user.getEmployee().getEmail())) {
            return "E-mail jest niepoprawny";
        } else {
            user.setDtype();
            this.userRepository.save(user);
            sendEmail(user.getEmployee().getEmail(),content);
            return "Dodano użytkownika";
        }
        //====================================================================
    }

    public Iterable<User> getAllEmployees() {
        Iterable<User> users =  this.userRepository.findAll();
        return users;
    }

    public void sendEmail(String email, String content) {
        ess.sendEmail(email,"Praca Magisterska - konto zostało utworzone",content);
    }

    public User getUserById(long id) {
        return this.userRepository.findFirstById(id);
    }

    public List<Role> getRoleList() {
        List<Role> roles = this.roleInterface.findAll();

        roles.remove(0);

        return roles;
    }

    public List<String> getRoleListById(long id) {
        User user = this.userRepository.findFirstById(id);
        List<Role> roles = user.getRoles();
        List<String> rolenames = new ArrayList<>();

        for (int i=1; i<=roles.size(); i++) {
            rolenames.add(roles.get(i-1).getRolename());
        }

        return rolenames;
    }

    public String changeRoles(int[] rolesList, long userId, String password) {
        if (passwordEncoder.matches(password, UserContext.getCurrentUser().getEmployee().getPassword()) && userId != 1) {
            User user = this.userRepository.findFirstById(userId);
            List<Role> newRoles = new ArrayList<>();

            for (int i :
                    rolesList) {
                newRoles.add(this.roleInterface.findById(i));
            }

            user.setRoles(newRoles);
            this.userRepository.save(user);
            return "Zmieniono uprawnienia";
        } else if(userId==1) {
            return "Zmiana uprawnień użytkownika ROOT jest niemożliwa!";
        }else {
            return "Podane hasło jest nieprawidłowe!";
        }
    }

    public String changeRoles(long userId, String password) {
        if (passwordEncoder.matches(password,UserContext.getCurrentUser().getEmployee().getPassword()) && userId!=1) {
            User user = this.userRepository.findFirstById(userId);
            user.setRoles(new ArrayList<Role>());
            this.userRepository.save(user);
            return "Zmieniono uprawnienia";
        } else if (userId==1) {
            return "Zmiana uprawnień użytkownika ROOT jest niemożliwa!";
        } else {
            return "Podane hasło jest nieprawidłowe!";
        }
    }

    public String lockUser(long id, String password) {
        if (passwordEncoder.matches(password,UserContext.getCurrentUser().getEmployee().getPassword())) {
            User user = this.userRepository.findFirstById(id);
            if (user.getAccount().getIsActive() && id!=1 && id!=UserContext.getCurrentUserId()) {
                user.getAccount().setIsActive(false);
                this.userRepository.save(user);
                return "Konto użytkownika "+user.getEmployee().getName()+" "+user.getEmployee().getSurname()+" zostało zablokowane";
            } else if (id==1){
                return "Nie można zablokować konta ROOT!";
            } else if (id==UserContext.getCurrentUserId()) {
                return "Nie można zablokować własnego konta!";
            } else {
                return "Użytkownik jest już zablokowany";
            }
        } else {
            return "Hasło nieprawidłowe!";
        }
    }

    public List<User> getAllNotActive() {
       Iterable<User> list = this.userRepository.findAll();
       List<User> notActiveList = new ArrayList<>();
        for (User u:
             list) {
            if (!u.getAccount().getIsActive()) {
                notActiveList.add(u);
                System.out.println(u.getEmployee().getSurname());
            }
        }
     return notActiveList;
    }

    public String unlockUser(long id, String pass, String email) {
        //Sprawdzenie poprawności podanego hasła
        if (passwordEncoder.matches(pass,UserContext.getCurrentUser().getEmployee().getPassword())) {
            User user = this.userRepository.findFirstById(id);

            //Jeżeli podano nowy adres e-mail to ustaw go jako obecny
            if (!email.isEmpty()) {
                user.getEmployee().setEmail(email);
                this.userRepository.save(user);
            }

            if (!user.getAccount().getIsActive()) {
                //Aktywowanie użytkownika
                user.getAccount().setIsActive(true);

                //Generownie nowego hasłą
                String newPassword = TokenGenerator.generateToken();

                //Ustawianie nowego hasłą po wcześniejszym zahashowaniu
                user.getEmployee().setPassword(this.passwordEncoder.encode(newPassword));

                //Wysyłanie wiadomości e-mail z nowymi danymi do logowania
                String content = CreateEmail.form(user.getEmployee().getName(),user.getEmployee().getSurname(),
                        user.getEmployee().getLogin(),newPassword);

                sendEmail(user.getEmployee().getEmail(),content);

                //Zapisanie użytkownika do bazy danych
                this.userRepository.save(user);

                //Informacja zwrotna
                return "Przywrócono użytkownika";
            } else {
                return "Użytkownik jest aktywny!";
            }
        } else {
            return "Podane hasło jest nieprawidłowe!";
        }
    }
}
