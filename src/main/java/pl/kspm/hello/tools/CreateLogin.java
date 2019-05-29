package pl.kspm.hello.tools;

import pl.kspm.hello.repository.EmployeeRepository;

public class CreateLogin {
    private String name;
    private String surname;
    private EmployeeRepository er;
    private DiactricalSignReplacer dsr;

    public CreateLogin(String name, String surname, EmployeeRepository employeeRepository) {
        this.name=name.toLowerCase();
        this.surname=surname.toLowerCase();
        this.er=employeeRepository;
    }

    public String create() {
        dsr = new DiactricalSignReplacer();
        
        String login =dsr.replace(name) +"."+dsr.replace(surname);
        int test = er.findAllByLoginContains(login).size();

        if (test!=0) {
            login+=test;
        }

        return login;
    }
}
