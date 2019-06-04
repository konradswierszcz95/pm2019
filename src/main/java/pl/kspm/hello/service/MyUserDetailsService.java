package pl.kspm.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.kspm.hello.model.MyUserDetails;
import pl.kspm.hello.model.User;
import pl.kspm.hello.repository.RoleInterface;
import pl.kspm.hello.repository.UserConnectorRepository;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserConnectorRepository userConnectorRepository;
    @Autowired
    private RoleInterface roleInterface;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUserConnector = userConnectorRepository.findByEmployee_Login(username);

        //Ogarnąć muszę o chuj chodzi z lambdami
        optionalUserConnector
                .orElseThrow(() -> new UsernameNotFoundException("Użytkownik o takim loginie nie istnieje"));

        MyUserDetails myUserDetails = optionalUserConnector
                .map(MyUserDetails::new).get();

        return myUserDetails;
    }

}
