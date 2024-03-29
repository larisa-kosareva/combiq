package ru.atott.combiq.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import ru.atott.combiq.service.bean.UserQualifier;
import ru.atott.combiq.service.site.Context;
import ru.atott.combiq.service.site.UserContext;
import ru.atott.combiq.web.security.AuthService;
import ru.atott.combiq.web.security.CombiqUser;

import java.util.HashSet;

public class BaseController {
    @Autowired
    private AuthService authService;

    protected int getZeroBasedPage(int page) {
        return Math.max(0, page - 1);
    }

    protected Context getContext() {
        CombiqUser user = authService.getUser();

        Context context = new Context();
        context.setUser(new UserContext());
        context.getUser().setAnonimous(user == null);

        if (user != null) {
            context.getUser().setUserName(user.getName());
            context.getUser().setUserQualifier(new UserQualifier(user.getType(), user.getLogin()));
            context.getUser().setUserId(user.getId());
            context.getUser().setRoles(new HashSet<>(user.getRoles()));
        }

        return context;
    }
}
