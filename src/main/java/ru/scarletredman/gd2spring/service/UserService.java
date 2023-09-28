package ru.scarletredman.gd2spring.service;

import java.util.List;
import java.util.Optional;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.scarletredman.gd2spring.controller.response.LoginResponse;
import ru.scarletredman.gd2spring.model.User;
import ru.scarletredman.gd2spring.model.dto.UserScoreDTO;
import ru.scarletredman.gd2spring.service.exception.UserLoginError;
import ru.scarletredman.gd2spring.service.exception.UserRegisterError;
import ru.scarletredman.gd2spring.service.type.FindUserPage;

public interface UserService extends UserDetailsService {

    User registerUser(String username, String password, String email) throws UserRegisterError;

    User loginUser(String username, String hashedPassword) throws UserLoginError;

    User loginUserByGjp(long userId, String rawPassword) throws UserLoginError;

    void updateScore(User user);

    Optional<User> findUserById(long userId);

    void updateSettings(User user);

    Optional<User> findUserWithRating(int userId);

    List<UserScoreDTO> getTop100UsersByStars();

    List<UserScoreDTO> getTop100UsersByCreatorPoints();

    List<UserScoreDTO> getRelativeTop(User user);

    FindUserPage findUser(String input, int page);

    static @NonNull User getCurrentUserFromSecurityContextHolder() throws UserLoginError {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof User user) {
            if (user.isBanned()) {
                throw new UserLoginError(LoginResponse.ErrorReason.BANNED);
            }

            return user;
        }

        throw new UserLoginError(LoginResponse.ErrorReason.LOGIN_FAILED);
    }
}
