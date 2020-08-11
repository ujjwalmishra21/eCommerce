package com.example.demo.controller;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderControllerTest {
    private OrderController orderController;

    private UserRepository userRepository = mock(UserRepository.class);
    private OrderRepository orderRepository = mock(OrderRepository.class);

    @Before
    public void setup(){
        orderController = new OrderController();

        TestUtils.injectObjects(orderController,"userRepository", userRepository);
        TestUtils.injectObjects(orderController,"orderRepository", orderRepository);
    }

    @Test
        public void orderSubmit(){
            User user = new User();
            user.setId(1);
            user.setUsername("ujjwal2102");
            Cart cartObj = new Cart();

            Item item = new Item();
            item.setId(1L);
            item.setName("Play Station 5");
            item.setDescription("Bundled");
            item.setPrice(BigDecimal.valueOf(50000));

            List<Item> cartItems = new ArrayList<>();
            cartItems.add(item);
            cartObj.setItems(cartItems);

            user.setCart(cartObj);

            when(userRepository.findByUsername(user.getUsername())).thenReturn(user);

            ResponseEntity<UserOrder> response = orderController.submit(user.getUsername());

            UserOrder userOrder = response.getBody();

            Assert.assertEquals(200, response.getStatusCodeValue());
            Assert.assertEquals(1, userOrder.getItems().size());
            Assert.assertEquals("Play Station 5", userOrder.getItems().get(0).getName());

        }
    @Test
    public void orderSubmitError(){
        User user = new User();
        user.setId(1);
        user.setUsername("ujjwal2102");
        Cart cartObj = new Cart();

        Item item = new Item();
        item.setId(1L);
        item.setName("Play Station 5");
        item.setDescription("Bundled");
        item.setPrice(BigDecimal.valueOf(50000));

        List<Item> cartItems = new ArrayList<>();
        cartItems.add(item);
        cartObj.setItems(cartItems);

        user.setCart(cartObj);

        String username = "ujjwal2101";

        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);

        ResponseEntity<UserOrder> response = orderController.submit(username);

        UserOrder userOrder = response.getBody();

        Assert.assertEquals(404, response.getStatusCodeValue());

    }

    @Test
    public void getOrderHistory(){
        User user = new User();
        user.setId(1);
        user.setUsername("ujjwal2102");
        Cart cartObj = new Cart();

        Item item = new Item();
        item.setId(1L);
        item.setName("Play Station 5");
        item.setDescription("Bundled");
        item.setPrice(BigDecimal.valueOf(50000));

        List<Item> cartItems = new ArrayList<>();
        cartItems.add(item);
        cartObj.setItems(cartItems);

        user.setCart(cartObj);
        UserOrder userOrder = new UserOrder();
        userOrder.setItems(cartItems);
        userOrder.setUser(user);
        userOrder.setTotal(item.getPrice());

        List<UserOrder> userOrderList = new ArrayList<>();
        userOrderList.add(userOrder);
        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);
        when(orderRepository.findByUser(user)).thenReturn(userOrderList);

        ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser(user.getUsername());

        List<UserOrder> userOrders = response.getBody();

        Assert.assertEquals(200, response.getStatusCodeValue());
        Assert.assertEquals(1, userOrders.size());
        Assert.assertEquals("ujjwal2102", userOrders.get(0).getUser().getUsername());
        Assert.assertEquals("Play Station 5", userOrders.get(0).getItems().get(0).getName());

    }
}
