package com.example.classbjunit.controller;

import com.example.classbjunit.model.Item;
import com.example.classbjunit.service.ItemService;
import com.example.classbjunit.utils.APIResponse;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItemControllerIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	public void getAll_success() throws JSONException {
		String response = this.restTemplate.getForObject("/all-items",String.class);

		JSONAssert.assertEquals("[{id:101},{id:102},{id:103},{id:104}]",response,false);
			
	}

	@Test
	public void getById_successObject() throws JSONException {
		Item item = this.restTemplate.getForObject("/all-items/101",Item.class);

		assertEquals("Item1",item.getName());
		assertEquals(1000,item.getValue());

	}

	@Test
	public void getById_success() throws JSONException {
		ResponseEntity<Item> item = this.restTemplate.getForEntity("/all-items/101",Item.class);

		assertTrue(item.getStatusCode().is2xxSuccessful());
		assertEquals("Item1",item.getBody().getName());
		assertEquals(1000,item.getBody().getValue());

	}

	@Test
	public void getById_404() throws JSONException {
		ResponseEntity<APIResponse> response = this.restTemplate.getForEntity("/all-items/901",APIResponse.class);

		assertTrue(response.getStatusCodeValue()==404);
		assertFalse(response.getBody().isStatus());
		assertEquals("Item not found",response.getBody().getMessage());

	}

	@Test
	public void postItem_success() throws JSONException {
		Item body = new Item(300,"TestItem",700,8);
		ResponseEntity<Item> item = this.restTemplate.postForEntity("/all-items/new",body, Item.class);

		assertTrue(item.getStatusCode().is2xxSuccessful());
		assertEquals("TestItem",item.getBody().getName());

	}

	@Test
	public void postItem_404() throws JSONException {
		Item body = new Item(300,"Item1",700,8);
		ResponseEntity<APIResponse> response = this.restTemplate.postForEntity("/all-items/new",body,APIResponse.class);

		assertTrue(response.getStatusCodeValue()==400);
		assertFalse(response.getBody().isStatus());
		assertEquals("Item name exists already",response.getBody().getMessage());

	}

    @Test
	public void updateItem_success(){
		Item body = new Item(300,"Item1",700,8);
//		ResponseEntity<APIResponse> response = this.restTemplate.exchange("/all-items/1",,body,APIResponse.class);

	}
}
