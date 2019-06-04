package pl.kspm.hello.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;


public class MyUserDetails extends User implements UserDetails {

    public MyUserDetails (User user) {
        super(user);
    }

    @Override
    public List<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> result= new ArrayList<>();
        for (Role role : getRoles()) {
            result.add(new SimpleGrantedAuthority("ROLE_"+role.getRolename()));
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
