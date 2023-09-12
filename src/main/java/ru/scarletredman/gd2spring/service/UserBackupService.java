package ru.scarletredman.gd2spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.scarletredman.gd2spring.controller.response.LoginResponse;
import ru.scarletredman.gd2spring.model.User;
import ru.scarletredman.gd2spring.repository.UserBackupRepository;
import ru.scarletredman.gd2spring.repository.UserRepository;
import ru.scarletredman.gd2spring.security.HashPassword;
import ru.scarletredman.gd2spring.service.exception.UserBackupError;
import ru.scarletredman.gd2spring.service.exception.UserLoginError;

@Service
@RequiredArgsConstructor
public class UserBackupService {

    private final HashPassword hashPassword;
    private final UserRepository userRepository;
    private final UserBackupRepository backupRepository;

    private User auth(String username, String password) throws UserLoginError {
        var userOpt = userRepository.findUserByUsernameIgnoreCase(username);
        if (userOpt.isEmpty()) {
            throw new UserLoginError(LoginResponse.ErrorReason.LOGIN_FAILED);
        }
        var user = userOpt.get();
        if (!hashPassword.hash(password, username.toLowerCase()).equals(user.getPassword())) {
            throw new UserLoginError(LoginResponse.ErrorReason.LOGIN_FAILED);
        }
        if (user.isBanned()) {
            throw new UserLoginError(LoginResponse.ErrorReason.BANNED);
        }
        return user;
    }

    @Transactional(rollbackFor = {UserLoginError.class, UserBackupError.class})
    public void save(String username, String password, String data) throws UserLoginError {
        var user = auth(username, password);
        backupRepository.saveBackup(user, data);
    }

    @Transactional(rollbackFor = {UserLoginError.class, UserBackupError.class})
    public String load(String username, String password) throws UserLoginError {
        var user = auth(username, password);
        return backupRepository.loadBackup(user).orElseThrow(UserBackupError::new);
    }
}
