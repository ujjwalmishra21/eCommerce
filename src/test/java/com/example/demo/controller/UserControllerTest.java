package com.example.demo.controller;


import com.example.demo.TestUtils;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Assert;
import static org.hamcrest.Matchers.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.net.URI;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class UserControllerTest {
//    private UserController userController;
//
//    private UserRepository userRepository = mock(UserRepository.class);
//    private CartRepository cartRepository = mock(CartRepository.class);
//    private BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);
//
//    @Before
//    public void setup(){
//        userController = new UserController();
//        TestUtils.injectObjects(userController,"userRepository", userRepository);
//        TestUtils.injectObjects(userController,"cartRepository", cartRepository);
//        TestUtils.injectObjects(userController,"bCryptPasswordEncoder", encoder);
//
//
//    }
//
//    @Test
//    public void createUser() throws Exception{
//        when(encoder.encode("ujjwal21")).thenReturn("thisIsHashed");
//        CreateUserRequest r = new CreateUserRequest();
//        r.setUsername("ujjwal2102");
//        r.setPassword("ujjwal21");
//        r.setConfirmPassword("ujjwal21");
//
//        final ResponseEntity<User> response = userController.createUser(r);
//
//        Assert.assertNotNull(response);
//        Assert.assertEquals(200,response.getStatusCodeValue());
//        User u = response.getBody();
//        Assert.assertNotNull(u);
//        Assert.assertEquals("ujjwal2102",u.getUsername());
//        Assert.assertEquals("thisIsHashed",u.getPassword());
//        getUser();
//    }
//
//
//    public void getUser(){
////        User user = new User();
////        user.setId(1L);
////        user.setUsername("ujjwal2102");
////        user.setPassword("ujjwal21");
//
//        final ResponseEntity<User> response = userController.findByUserName("ujjwal2102");
//        System.out.println(response.getBody());
//
//    }


    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<User> json;

    @Autowired
    private JacksonTester<CreateUserRequest> jsonN;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private BCryptPasswordEncoder encoder;

    @Before
    public void setup(){
//        User user = new User();
//        user.setId(1L);
//
//        BDDMockito.given(userRepository.save(any())).willReturn(user);


    }

    @Test
    public void createUser() throws Exception{
        when(encoder.encode("ujjwal21")).thenReturn("thisIsHashed");
        CreateUserRequest r = new CreateUserRequest();
        r.setUsername("ujjwal2102");
        r.setPassword("ujjwal21");
        r.setConfirmPassword("ujjwal21");
        mvc.perform(
                MockMvcRequestBuilders.post(new URI("/api/user/create"))
                        .content(jsonN.write(r).getJson())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",is(0)))
                .andExpect(jsonPath("$.username",is("ujjwal2102")));
        login();
    }


    public void login() throws Exception{
        User user = new User();
        user.setUsername("ujjwal2102");
        user.setPassword("ujjwal21");
        mvc.perform(
                MockMvcRequestBuilders.post(new URI("/login"))
                        .content(json.write(user).getJson())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",is(0)))
                .andExpect(jsonPath("$.username",is("ujjwal2102")));

    }
}
