package am.basic.jdbcStart.service;

import am.basic.jdbcStart.model.User;
import am.basic.jdbcStart.model.exceptions.*;
import am.basic.jdbcStart.repository.UserRepository;
import am.basic.jdbcStart.util.DataSource;
import am.basic.jdbcStart.util.encoder.Generator;
import am.basic.jdbcStart.util.encoder.Md5Encoder;

import java.sql.SQLException;

import static am.basic.jdbcStart.util.constants.Messages.*;

public class UserService {


    private UserRepository userRepository = new UserRepository(new DataSource());


    public void register(User user) throws DuplicateDataException, InternalServerException {

        try {
            User duplicate = userRepository.getByUsername(user.getUsername());
            DuplicateDataException.check(duplicate != null, DUPLICATE_USER_MESSAGE);
            user.setPassword(Md5Encoder.encode(user.getPassword()));
            user.setCode(Generator.getRandomDigits(5));
            user.setStatus(0);
            userRepository.add(user);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new InternalServerException(INTERNAL_ERROR_MESSAGE);
        }


    }


    public User login(String username, String password) throws InternalServerException, NotFoundException, UnverifiedException {
        try {
            User user = userRepository.getByUsername(username);
            NotFoundException.check(user == null || !Md5Encoder.matches(password, user.getPassword()), INVALID_CREDENTIALS_MESSAGE);
            UnverifiedException.check(user.getStatus() != 1, UNVERIFIED_MESSAGE);
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new InternalServerException(INTERNAL_ERROR_MESSAGE);
        }


    }

    public User changePassword(String username, String password, String newPassword) throws NotFoundException, InternalServerException, AccessDeniedException {
        try {
            User user = userRepository.getByUsername(username);
            NotFoundException.check(user == null, USER_NOT_EXIST_MESSAGE);
            AccessDeniedException.check(!user.getPassword().equals(password), WRONG_PASSWORD_MESSAGE);
            user.setPassword(newPassword);
            userRepository.update(user);
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new InternalServerException(INTERNAL_ERROR_MESSAGE);
        }

    }

    public void sendCode(String username) throws NotFoundException, InternalServerException {
        try {
            User user = userRepository.getByUsername(username);
            NotFoundException.check(user == null, USER_NOT_EXIST_MESSAGE);
            user.setCode(Generator.getRandomDigits(5));
            //send code to user by email
            userRepository.update(user);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new InternalServerException(INTERNAL_ERROR_MESSAGE);
        }
    }

    public void recoverPassword(String username, String code, String password) throws NotFoundException, AccessDeniedException, InternalServerException {
        try {
            User user = userRepository.getByUsername(username);
            NotFoundException.check(user == null, USER_NOT_EXIST_MESSAGE);
            AccessDeniedException.check(!user.getCode().equals(code), WRONG_CODE_MESSAGE);
            user.setPassword(password);
            userRepository.update(user);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new InternalServerException(INTERNAL_ERROR_MESSAGE);
        }
    }

    public void verify(String username, String code) throws NotFoundException, InternalServerException, AccessDeniedException {
        try {
            User user = userRepository.getByUsername(username);
            NotFoundException.check(user == null, USER_NOT_EXIST_MESSAGE);
            AccessDeniedException.check(!user.getCode().equals(code), WRONG_CODE_MESSAGE);
            user.setStatus(1);
            userRepository.update(user);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new InternalServerException(INTERNAL_ERROR_MESSAGE);
        }

    }


}
