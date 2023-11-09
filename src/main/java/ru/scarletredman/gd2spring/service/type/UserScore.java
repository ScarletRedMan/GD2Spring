package ru.scarletredman.gd2spring.service.type;

import ru.scarletredman.gd2spring.model.User;

public record UserScore(User user, User target, long newMessages) {

    public boolean isSelf() {
        return user.equals(target);
    }

    public boolean isError() {
        return user == null || target == null;
    }

    public static UserScore error() {
        return new UserScore(null, null, 0);
    }
}
