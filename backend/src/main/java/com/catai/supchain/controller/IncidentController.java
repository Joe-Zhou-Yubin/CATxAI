package com.catai.supchain.controller;

import com.catai.supchain.model.Incident;
import com.catai.supchain.payload.response.MessageResponse;
import com.catai.supchain.repository.IncidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/inci")
public class IncidentController {

    @Autowired
    private IncidentRepository incidentRepository;

    @PostMapping("/create")
    public ResponseEntity<?> createIncident(@RequestBody Incident incident) {
        try {
            // Save the incident to	 the database
            Incident createdIncident = incidentRepository.save(incident);
            return new ResponseEntity<>("Incident created with ID: " + createdIncident.getId(), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Error: Unable to create the incident."));
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<Incident>> listIncidents() {
        List<Incident> incidents = incidentRepository.findAll();
        return ResponseEntity.ok(incidents);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getIncidentById(@PathVariable Long id) {
        Optional<Incident> incidentOptional = incidentRepository.findById(id);

        if (incidentOptional.isPresent()) {
            return ResponseEntity.ok(incidentOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteIncident(@PathVariable Long id) {
        try {
            // Check if the incident with the given ID exists
            if (incidentRepository.existsById(id)) {
                // Delete the incident from the database
                incidentRepository.deleteById(id);
                return ResponseEntity.ok(new MessageResponse("Incident deleted successfully!"));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Error: Unable to delete the incident."));
        }
    }
    
    @GetMapping("/getbystore/{storeId}")
    public ResponseEntity<List<Incident>> getIncidentsByStoreId(@PathVariable String storeId) {
        try {
            // Retrieve incidents by storeId from the "affstore" field
            List<Incident> incidents = incidentRepository.findByAffstore(storeId);

            if (!incidents.isEmpty()) {
                return ResponseEntity.ok(incidents);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
