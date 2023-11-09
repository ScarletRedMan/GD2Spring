package ru.scarletredman.gd2spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.scarletredman.gd2spring.model.User;
import ru.scarletredman.gd2spring.service.MessageService;
import ru.scarletredman.gd2spring.service.UserScoreService;
import ru.scarletredman.gd2spring.service.UserService;
import ru.scarletredman.gd2spring.service.type.UserScore;

@Service
@RequiredArgsConstructor
public class UserScoreServiceImpl implements UserScoreService {

    private final UserService userService;
    private final MessageService messageService;

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public UserScore getUserScore(User user, long targetUserId) {
        User targetUser;

        {
            var temp = userService.findUserWithRating(targetUserId);
            if (temp.isEmpty()) return UserScore.error();
            targetUser = temp.get();
        }

        long messages = 0;
        if (user.equals(targetUser)) {
            messages = messageService.countNewMessages(user);
        }

        return new UserScore(user, targetUser, messages);
    }
}
