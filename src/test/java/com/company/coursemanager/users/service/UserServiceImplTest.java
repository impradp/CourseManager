package com.company.coursemanager.users.service;

import com.company.coursemanager.users.model.User;
import com.company.coursemanager.users.model.UserDTO;
import com.company.coursemanager.users.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userServiceImpl;

    @Mock
    UserRepository userRepository;

    @Mock
    User userMock;

    @Test
    void testFindAllReturnsSuccess() {
        //Arrange
        int expectedResult = 1;
        when(userRepository.findAll()).thenReturn(List.of(userMock));
        //Act
        List<UserDTO> result = userServiceImpl.findAll();
        int actualResult = result.size();
        //Assert
        Assertions.assertEquals(expectedResult, actualResult);
    }
}