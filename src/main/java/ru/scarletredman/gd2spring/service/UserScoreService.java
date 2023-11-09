package ru.scarletredman.gd2spring.service;

import ru.scarletredman.gd2spring.model.User;
import ru.scarletredman.gd2spring.service.type.UserScore;

public interface UserScoreService {

    UserScore getUserScore(User user, long targetUserId);
}
