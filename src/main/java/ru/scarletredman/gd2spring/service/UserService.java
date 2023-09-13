package ru.scarletredman.gd2spring.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.scarletredman.gd2spring.controller.response.LoginResponse;
import ru.scarletredman.gd2spring.controller.response.RegisterResponse;
import ru.scarletredman.gd2spring.model.User;
import ru.scarletredman.gd2spring.repository.UserRepository;
import ru.scarletredman.gd2spring.security.HashPassword;
import ru.scarletredman.gd2spring.service.exception.UserLoginError;
import ru.scarletredman.gd2spring.service.exception.UserRegisterError;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final HashPassword hashPassword;

    @Transactional(rollbackFor = UserRegisterError.class)
    public void registerUser(String username, String password, String email) throws UserRegisterError {
        if (username.length() < 3) {
            throw new UserRegisterError(RegisterResponse.TOO_SHORT_USERNAME);
        }
        if (username.length() > 20 && !username.matches("^[aA-zZ\\d]+$")) {
            throw new UserRegisterError(RegisterResponse.INVALID_USERNAME);
        }
        if (password.length() < 6) {
            throw new UserRegisterError(RegisterResponse.TOO_SHORT_PASSWORD);
        }
        if (userRepository.findUserByUsernameIgnoreCase(username).isPresent()) {
            throw new UserRegisterError(RegisterResponse.USERNAME_IS_ALREADY_IN_USE);
        }
        if (userRepository.findUserByEmailIgnoreCase(email).isPresent()) {
            throw new UserRegisterError(RegisterResponse.EMAIL_IS_ALREADY_IN_USE);
        }

        var hashedPassword = hashPassword.hash(password, username.toLowerCase());
        var user = new User(username, hashedPassword, email);
        userRepository.save(user);
    }

    @Transactional(rollbackFor = UserLoginError.class, readOnly = true)
    public User loginUser(String username, String hashedPassword) throws UserLoginError {
        return processLogin(userRepository.findUserByUsernameIgnoreCase(username), hashedPassword, false);
    }

    @Transactional
    public User loginUser(long userId, String rawPassword) throws UserLoginError {
        return processLogin(userRepository.findById(userId), rawPassword, true);
    }

    private User processLogin(Optional<User> userOpt, String password, boolean rawPassword) throws UserLoginError {
        if (userOpt.isEmpty()) {
            throw new UserLoginError(LoginResponse.ErrorReason.LOGIN_FAILED);
        }

        var user = userOpt.get();
        if (rawPassword) {
            password = hashPassword.hash(password, user.getUsername().toLowerCase());
        }
        if (!password.equals(user.getPassword())) {
            throw new UserLoginError(LoginResponse.ErrorReason.LOGIN_FAILED);
        }
        if (user.isBanned()) {
            throw new UserLoginError(LoginResponse.ErrorReason.BANNED);
        }

        return user;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findUserByUsernameIgnoreCase(username);
        return user.orElseThrow(() -> new UsernameNotFoundException("User not fount exception"));
    }

    @Transactional
    public void updateScore(User user) {
        userRepository.save(user);
    }

    @Transactional
    public Optional<User> findUserById(long userId) {
        return userRepository.findById(userId);
    }

    public static @NonNull User getCurrentUserFromSecurityContextHolder() throws UserLoginError {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof User user) {
            if (user.isBanned()) {
                throw new UserLoginError(LoginResponse.ErrorReason.BANNED);
            }

            return user;
        }

        throw new UserLoginError(LoginResponse.ErrorReason.LOGIN_FAILED);
    }
}
