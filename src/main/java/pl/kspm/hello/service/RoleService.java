package pl.kspm.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kspm.hello.model.Role;
import pl.kspm.hello.repository.RoleInterface;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    RoleInterface roleInterface;


}
