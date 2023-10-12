package com.catai.supchain.controller;

import com.catai.supchain.model.Rice;
import com.catai.supchain.model.Store;
import com.catai.supchain.payload.response.MessageResponse;
import com.catai.supchain.repository.RiceRepository;
import com.catai.supchain.repository.StoreRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/rice")
public class RiceController {

    @Autowired
    private RiceRepository riceRepository;

    @Autowired
    private StoreRepository storeRepository;

    @PostMapping("/create/{storeId}")
    public ResponseEntity<?> createRice(@PathVariable String storeId, @RequestBody Rice rice) {
        try {
            // Check if the store with the given storeId exists
            Optional<Store> storeOptional = storeRepository.findByStoreId(storeId);
            if (!storeOptional.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            // Set the storeId for the rice entry
            rice.setStoreId(storeId);

            // Save the rice entry to the database
            Rice createdRice = riceRepository.save(rice);

            return new ResponseEntity<>("Rice entry created with ID: " + createdRice.getId(), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Error: Unable to create the rice entry."));
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<Rice>> listRice() {
        List<Rice> riceList = riceRepository.findAll();
        return ResponseEntity.ok(riceList);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getRiceById(@PathVariable Long id) {
        Optional<Rice> riceOptional = riceRepository.findById(id);

        if (riceOptional.isPresent()) {
            return ResponseEntity.ok(riceOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRice(@PathVariable Long id) {
        try {
            // Check if the rice entry with the given ID exists
            Optional<Rice> riceOptional = riceRepository.findById(id);
            if (!riceOptional.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            // Delete the rice entry from the database
            riceRepository.deleteById(id);

            return ResponseEntity.ok(new MessageResponse("Rice entry deleted successfully!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Error: Unable to delete the rice entry."));
        }
    }
    
    @GetMapping("/getbystore/{storeId}")
    public ResponseEntity<List<Rice>> getRiceByStoreId(@PathVariable String storeId) {
        try {
            // Retrieve Rice entries by storeId
            List<Rice> riceList = riceRepository.findByStoreId(storeId);

            if (!riceList.isEmpty()) {
                return ResponseEntity.ok(riceList);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    



}
