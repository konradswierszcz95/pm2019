package pl.kspm.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.kspm.hello.config.UserContext;
import pl.kspm.hello.model.Employee;
import pl.kspm.hello.model.User;
import pl.kspm.hello.repository.EmployeeRepository;
import pl.kspm.hello.repository.UserRepository;
import pl.kspm.hello.tools.StringCheck;

@Service
public class
AccountService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    EmployeeRepository employeeRepository;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User getCurrentUser() {
        return UserContext.getCurrentUser();
    }

    public String changePassword(String oldPass, String newPass, String repeat) {
        User user = UserContext.getCurrentUser();
        long id = user.getId();
        Employee employee = this.userRepository.findFirstById(id).getEmployee();

        String actualPass = user.getEmployee().getPassword();

        if (newPass.equals(repeat)) {
            if (passwordEncoder.matches(oldPass,actualPass)) {
                if (checkPass(newPass)) {
                    try {
                        employee.setPassword(passwordEncoder.encode(newPass));
                        this.employeeRepository.save(employee);
                        return "Hasło zostało zmienione";
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        return "Błąd komunikacji z bazą danych";
                    }
                } else {
                    return "Nowe hasło nie spełnia wymagań!";
                }
            }
            return "Stare hasło jest nieprawidłowe";
        } else {
            return "Podane nowe hasła są różne";
        }
    }

    public boolean checkPass(String pass) {
        if (pass.length()>= 8 && StringCheck.containsSpecialSign(pass)) {
            return true;
        } else {
            return false;
        }
    }
}
