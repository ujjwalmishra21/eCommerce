package com.example.demo.controller;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemControllerTest {
    private ItemController itemController;

    private ItemRepository itemRepository = mock(ItemRepository.class);

    @Before
    public void setup(){
        itemController = new ItemController();

        TestUtils.injectObjects(itemController,"itemRepository",itemRepository);
    }

    @Test
    public void getAllItems(){
        Item item = new Item();
        item.setId(1L);
        item.setName("Play Station 5");
        item.setDescription("Bundled");
        item.setPrice(BigDecimal.valueOf(50000));

        List<Item> itemList = new ArrayList<>();
        itemList.add(item);

        when(itemRepository.findAll()).thenReturn(itemList);

        ResponseEntity<List<Item>> response = itemController.getItems();

        List<Item> items = response.getBody();

        Assert.assertEquals(200, response.getStatusCodeValue());
        Assert.assertEquals(1, items.size());
        Assert.assertEquals("Play Station 5", items.get(0).getName());

    }

    @Test
    public void getItemById(){

        Item itemObj = new Item();
        itemObj.setId(1L);
        itemObj.setName("Play Station 5");
        itemObj.setDescription("Bundled");
        itemObj.setPrice(BigDecimal.valueOf(50000));


        when(itemRepository.findById(1L)).thenReturn(Optional.of(itemObj));

        ResponseEntity<Item> response = itemController.getItemById(1L);

        Item item = response.getBody();

        Assert.assertEquals(200, response.getStatusCodeValue());

        Assert.assertEquals("Play Station 5", item.getName());
        Assert.assertEquals(BigDecimal.valueOf(50000),item.getPrice());

    }

    @Test
    public void getItemByName(){

        Item item = new Item();
        item.setId(1L);
        item.setName("Play Station 5");
        item.setDescription("Bundled");
        item.setPrice(BigDecimal.valueOf(50000));

        List<Item> itemList = new ArrayList<>();
        itemList.add(item);

        when(itemRepository.findByName("Play Station 5")).thenReturn(itemList);

        ResponseEntity<List<Item>> response = itemController.getItemsByName("Play Station 5");

        List<Item> items = response.getBody();

        Assert.assertEquals(200, response.getStatusCodeValue());
        Assert.assertEquals(1, items.size());
        Assert.assertEquals("Play Station 5", items.get(0).getName());

    }

}
