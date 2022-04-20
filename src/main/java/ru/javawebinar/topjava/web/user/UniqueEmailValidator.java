package ru.javawebinar.topjava.web.user;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.to.UserTo;
import ru.javawebinar.topjava.util.UserUtil;
import ru.javawebinar.topjava.util.exception.IllegalRequestDataException;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Polik on 4/19/2022
 */
@Component
public class UniqueEmailValidator implements Validator {
    private final UserRepository repository;
    private final HttpServletRequest request;
    private final MessageSource source;

    public UniqueEmailValidator(UserRepository repository, HttpServletRequest request, MessageSource source) {
        this.repository = repository;
        this.request = request;
        this.source = source;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(User.class) || clazz.isAssignableFrom(UserTo.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = target instanceof User ?
                (User) target :
                UserUtil.createNewFromToWithId((UserTo) target);

        User fromDb = repository.getByEmail(user.getEmail());

        if (fromDb != null) {
            if (!user.isNew()) {
                int id = fromDb.id();

                if (user.getId() != null && user.getId() == id) {
                    return;
                }
            }

            throw new IllegalRequestDataException(source.getMessage("user.email.duplicated", new Object[]{}, request.getLocale()));
        }
    }
}
