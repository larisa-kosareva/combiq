package ru.atott.combiq.web.security;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.atott.combiq.service.bean.User;
import ru.atott.combiq.service.bean.UserQualifier;
import ru.atott.combiq.service.user.UserService;

import java.util.List;

public class CombiqUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String qualifier) throws UsernameNotFoundException {
        try {
            UserQualifier userQualifier = new UserQualifier(qualifier);
            User user = userService.findByLogin(userQualifier.getLogin(), userQualifier.getType());
            if (user != null) {
                String passwordHash = user.getPasswordHash();
                switch (user.getType()) {
                    case github:
                        passwordHash = DigestUtils.sha1Hex("github");
                        break;
                    case vk:
                        passwordHash = DigestUtils.sha1Hex("vk");
                        break;
                }

                List<String> userRoles = userService.getUserRoles(userQualifier);

                CombiqUser combiqUser = new CombiqUser(qualifier, passwordHash, userRoles);
                combiqUser.setType(user.getType());
                combiqUser.setLogin(user.getLogin());
                combiqUser.setId(user.getId());
                combiqUser.setAvatarUrl(user.getAvatarUrl());
                combiqUser.setEmail(user.getEmail());
                combiqUser.setName(user.getName());
                return combiqUser;
            }
        } catch (Exception e) {
            throw new UsernameNotFoundException(e.getMessage(), e);
        }
        throw new UsernameNotFoundException(String.format("User '%s' is not found.", qualifier));
    }
}
