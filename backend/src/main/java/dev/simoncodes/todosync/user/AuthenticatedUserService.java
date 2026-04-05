package dev.simoncodes.todosync.user;

import dev.simoncodes.todosync.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticatedUserService {

    public User getCurrentUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        UserDetailsAdapter uda = (UserDetailsAdapter) authentication.getPrincipal();
        return uda.getUser();
    }

}
