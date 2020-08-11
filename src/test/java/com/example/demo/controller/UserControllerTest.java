package com.example.demo.controller;


import com.example.demo.TestUtils;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {
    private UserController userController;

    private UserRepository userRepository = mock(UserRepository.class);
    private CartRepository cartRepository = mock(CartRepository.class);
    private BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);

    @Before
    public void setup(){
        userController = new UserController();
        TestUtils.injectObjects(userController,"userRepository", userRepository);
        TestUtils.injectObjects(userController,"cartRepository", cartRepository);
        TestUtils.injectObjects(userController,"bCryptPasswordEncoder", encoder);

    }

    @Test
    public void createUser() throws Exception{
        when(encoder.encode("ujjwal21")).thenReturn("thisIsHashed");
        CreateUserRequest r = new CreateUserRequest();
        r.setUsername("ujjwal2102");
        r.setPassword("ujjwal21");
        r.setConfirmPassword("ujjwal21");

        final ResponseEntity<User> response = userController.createUser(r);

        Assert.assertNotNull(response);
        Assert.assertEquals(200,response.getStatusCodeValue());
        User u = response.getBody();
        Assert.assertNotNull(u);
        Assert.assertEquals("ujjwal2102",u.getUsername());
        Assert.assertEquals("thisIsHashed",u.getPassword());

    }

    @Test
    public void getUserByUsername(){
        User user = new User();
        user.setId(1);
        user.setUsername("ujjwal2102");
        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);

        ResponseEntity<User> response = userController.findByUserName("ujjwal2102");
        User userData = response.getBody();
        Assert.assertEquals(200,response.getStatusCodeValue());
        Assert.assertEquals("ujjwal2102",userData.getUsername());
        Assert.assertEquals(1,user.getId());

    }

    @Test
    public void getUserById(){
        User user = new User();
        user.setId(1);
        user.setUsername("ujjwal2102");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        ResponseEntity<User> response = userController.findById(1L);
        User userData = response.getBody();
        Assert.assertEquals(200,response.getStatusCodeValue());
        Assert.assertEquals("ujjwal2102",userData.getUsername());
        Assert.assertEquals(1,user.getId());
    }

}
