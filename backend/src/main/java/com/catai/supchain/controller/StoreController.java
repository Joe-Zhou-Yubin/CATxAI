package com.catai.supchain.controller;

import com.catai.supchain.model.Store;
import com.catai.supchain.payload.response.MessageResponse;
import com.catai.supchain.repository.StoreRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/stores")
public class StoreController {

	@Autowired
    private StoreRepository storeRepository;

	@PostMapping("/create")
	public ResponseEntity<?> createStore(@RequestBody Store store) {
	    try {
	        // Generate a unique storeId
	        String uniqueStoreId = generateUnique8CharString();
	        store.setStoreId(uniqueStoreId);
	        
	        // Save the store to the database
	        Store createdStore = storeRepository.save(store);
	        
	        return new ResponseEntity<>("Store created with ID: " + createdStore.getId(), HttpStatus.CREATED);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(new MessageResponse("Error: Unable to create the store."));
	    }
	}
    
    private String generateUnique8CharString() {
        UUID uuid = UUID.randomUUID();
        String uuidStr = uuid.toString().replace("-", "").substring(0, 8);
        return uuidStr;
    }

    @GetMapping("/list")
    public ResponseEntity<List<Store>> listStores() {
        List<Store> stores = storeRepository.findAll();
        return ResponseEntity.ok(stores);
    }
    
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getStoreById(@PathVariable Long id) {
        try {
            // Find the store by ID
            Optional<Store> storeOptional = storeRepository.findById(id);

            if (storeOptional.isPresent()) {
                Store store = storeOptional.get();
                return ResponseEntity.ok(store);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Error: Unable to retrieve the store."));
        }
    }
    
    @GetMapping("/getByStoreId/{storeId}")
    public ResponseEntity<?> getStoreByStoreId(@PathVariable String storeId) {
        try {
            // Find the store by storeId
            Optional<Store> storeOptional = storeRepository.findByStoreId(storeId);

            if (storeOptional.isPresent()) {
                Store store = storeOptional.get();
                return ResponseEntity.ok(store);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Error: Unable to retrieve the store by storeId."));
        }
    }

    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteStore(@PathVariable Long id) {
        try {
            // Check if the store with the given ID exists
            if (storeRepository.existsById(id)) {
                // Delete the store from the database
                storeRepository.deleteById(id);
                return ResponseEntity.ok(new MessageResponse("Store deleted successfully!"));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Error: Unable to delete the store."));
        }
    }
    
  

}
