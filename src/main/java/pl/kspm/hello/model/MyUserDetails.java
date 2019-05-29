package pl.kspm.hello.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MyUserDetails extends UserConnector implements UserDetails {

    public MyUserDetails (UserConnector userConnector) {
        super(userConnector);
    }

    @Override
    public List<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> result= new ArrayList<>();
        for (Role role:super.getRoles()) {
            result.add(new SimpleGrantedAuthority(role.getRolename()));
        }

        return result;
    }

    @Override
    public String getPassword() {
        return super.getEmployee().getPassword();
    }

    @Override
    public String getUsername() {
        return super.getEmployee().getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
