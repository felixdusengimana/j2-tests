package com.example.classbjunit.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import com.example.classbjunit.dto.UpdateItemDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.classbjunit.model.Item;
import com.example.classbjunit.service.ItemService;
import com.example.classbjunit.utils.APIResponse;

//@RunWith(SpringRunner.class)
//@WebMvcTest(ItemController.class)
@SpringBootTest()
@AutoConfigureMockMvc
public class ItemControllerTest {

	@MockBean
	private ItemService itemServiceMock;

	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void getAll_success() throws Exception {
		List<Item> asList = Arrays.asList(new Item(1,"Samuel",1,10),
				new Item(2,"Blessing",4,100));
		when(itemServiceMock.getAll()).thenReturn(asList);
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
				.get("/all-items")
				.accept(MediaType.APPLICATION_JSON);
				
				 MvcResult result = mockMvc
						.perform(request)
						.andExpect(status().isOk())
						.andExpect(content().json("[{\"id\":1,\"name\":\"Samuel\",\"price\":1},{\"id\":2,\"name\":\"Blessing\",\"price\":4}]"))
						.andReturn();
			
	}
	
	@Test
	public void getByOne_404() throws Exception {
		Item item = new Item(1,"Samuel",1,10);
		
		when(itemServiceMock.getById(item.getId())).thenReturn(item);
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
				.get("/all-items/2")
				.accept(MediaType.APPLICATION_JSON);
				
				 MvcResult result = mockMvc
						.perform(request)
						.andExpect(status().isNotFound())
						//.andExpect(content().string(""))
						.andExpect(content().json("{\"status\":false,\"message\":\"Item not found\"}"))
						.andReturn();
			
	}


	@Test
	public void update_success() throws Exception {
		Item item = new Item(1,"money",1,10);

		ResponseEntity.status(HttpStatus.CREATED).body(item);

		ResponseEntity<?> respnse= ResponseEntity.status(HttpStatus.CREATED).body(item);
		when(itemServiceMock.updateItem(1,new UpdateItemDto("money", 100, 3))).thenReturn(null);

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
				.get("/all-items/2")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc
				.perform(request)
				.andExpect(status().isNotFound())
				//.andExpect(content().string(""))
				.andExpect(content().json("{\"status\":false,\"message\":\"Item not found\"}"))
				.andReturn();

	}
}
