package pl.kspm.hello.config;

import org.springframework.security.core.context.SecurityContextHolder;
import pl.kspm.hello.model.User;

public class UserContext {
    public static User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static long getCurrentUserId() {
        return getCurrentUser().getId();
    }
}
