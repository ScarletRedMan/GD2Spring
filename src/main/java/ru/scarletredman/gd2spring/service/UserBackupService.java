package ru.scarletredman.gd2spring.service;

import ru.scarletredman.gd2spring.service.exception.UserLoginError;

public interface UserBackupService {

    void save(String username, String password, String data) throws UserLoginError;

    String load(String username, String password) throws UserLoginError;
}
