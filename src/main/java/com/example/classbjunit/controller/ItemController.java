package com.example.classbjunit.controller;

import java.util.List;

import com.example.classbjunit.dto.UpdateItemDto;
import com.example.classbjunit.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.classbjunit.model.Item;
import com.example.classbjunit.service.ItemService;
import com.example.classbjunit.utils.APIResponse;

import javax.validation.Valid;

@RestController
public class ItemController {

	@Autowired
	private ItemService itemService;

	@Autowired
	ItemRepository itemRepository;

	@GetMapping("/all-items")
	public List<Item> getAll() {

		return itemService.getAll();
	}

	@GetMapping("/all-items/{id}")
	public ResponseEntity<?> getById(@PathVariable(name = "id") int id) {

		Item item = itemService.getById(id);
		if (item != null) {
			return ResponseEntity.ok(item);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(false, "Item not found"));
	}

	@PostMapping("/all-items/new")
	public ResponseEntity<?> getById(@RequestBody Item item) {
		if(itemRepository.existsByName(item.getName())){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new APIResponse(false,"Item name exists already"));
		}
		itemRepository.save(item);
		return ResponseEntity.status(HttpStatus.CREATED).body(item);
	}

	@PutMapping("/all-items/{id}")
	public ResponseEntity<?> updateItem(@PathVariable(name = "id") int id,
										@Valid UpdateItemDto dto){
		return itemService.updateItem(id, dto);

	}

}
