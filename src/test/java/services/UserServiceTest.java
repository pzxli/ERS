package services;

import models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import repositories.UserDAO;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private UserService userService;

    private UserDAO userDao = Mockito.mock(UserDAO.class);

    public UserServiceTest(){
        this.userService = new UserService(userDao);
    }

    @Test
    void validateCredentialsInvalidUsername() {
        //arrange
        String expectedUsername = "incorrectusername";
        String expectedPassword = "pass123";
        User expectedOutput = null;
        Mockito.when(userDao.getUserGivenUsername(expectedUsername)).thenReturn(expectedOutput);

        //act
        User actualOutput = userService.validateCredentials(expectedUsername, expectedPassword);

        //assert
        Assertions.assertEquals(expectedOutput,actualOutput);
    }

    @Test
    void validateCredentialsInvalidPassword() {
        //arrange
        String expectedUsername = "correctusername";
        String expectedPassword = "pass123";
        User expectedOutput = null;
        User userFromDb = new User("correctusername", "pass1234", "firstname", "lastname", "email@gmail.com");
        Mockito.when(userDao.getUserGivenUsername(expectedUsername)).thenReturn(expectedOutput);

        //act
        User actualOutput = userService.validateCredentials(expectedUsername, expectedPassword);

        //assert
        Assertions.assertEquals(expectedOutput,actualOutput);
    }

    @Test
    void validateCredentialsValidCredentials() {
        //arrange
        String expectedUsername = "correctusername";
        String expectedPassword = "correctpassword";
        User expectedOutput = new User(expectedUsername, expectedPassword, "user", "one", "email@gmail.com");
        Mockito.when(userDao.getUserGivenUsername(expectedUsername)).thenReturn(expectedOutput);

        //act
        User actualOutput = userService.validateCredentials(expectedUsername, expectedPassword);

        //assert
        Assertions.assertEquals(expectedOutput,actualOutput);
    }

    @Test
    void createUser(){
        //arrange
        User userToPass = new User("username", "password", "user", "one", "email@gmail.com");

        //act
        userService.createUser(userToPass);

        //assert
        //Mockito.verify can be used to validate if a method was ran
        Mockito.verify(userDao, Mockito.times(1)).createUser(userToPass);
    }
}