package com.catai.supchain.controller;

import com.catai.supchain.dto.CompletionRequest;
import com.catai.supchain.dto.CompletionResponse;
import com.catai.supchain.dto.OpenAiApiClient;
import com.catai.supchain.model.Incident;
import com.catai.supchain.model.Rice;
import com.catai.supchain.payload.response.MessageResponse;
import com.catai.supchain.repository.IncidentRepository;
import com.catai.supchain.repository.RiceRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Optional;
import java.util.Date;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/pre")
public class PredictionController {
	
	@Autowired
	private final OpenAiApiClient openAiApiClient;
	
	@Autowired
    private final ObjectMapper objectMapper;

    @Autowired
    private RiceRepository riceRepository;
    
    @Autowired
    private IncidentRepository incidentRepository;
    
    
    @Autowired
    public PredictionController(OpenAiApiClient openAiApiClient, ObjectMapper objectMapper,
			RiceRepository riceRepository, IncidentRepository incidentRepository) {
		super();
		this.openAiApiClient = openAiApiClient;
		this.objectMapper = objectMapper;
		this.riceRepository = riceRepository;
		this.incidentRepository = incidentRepository;
	}

    @PostMapping("/createpre/{riceId}")
    public ResponseEntity<?> updatePrediction(@PathVariable Long riceId) {
        try {
            // Check if the rice with the given riceId exists
            Optional<Rice> riceOptional = riceRepository.findById(riceId);
            if (!riceOptional.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            // Get the Rice entity by riceId
            Rice rice = riceOptional.get();

            // Retrieve the month from the Rice object
            Date dateField = rice.getMonth();

            // Add the preset prompts
            List<String> presetPrompts = new ArrayList<>();
            presetPrompts.add("you are to help predict the amount of rice needed based on past rice amounts ");
            presetPrompts.add("the current month is " + dateField);
            presetPrompts.add("return me your answer only in json format (datatype Long prediction)");

            // Select the GPT-3 model service
            OpenAiApiClient.OpenAiService service = OpenAiApiClient.OpenAiService.GPT_3;

            // Retrieve the Rice entries by storeId
            String storeId = riceOptional.get().getStoreId();
            List<Rice> riceList = riceRepository.findByStoreId(storeId);

            // Retrieve incidents by storeId from the "affstore" field
            List<Incident> incidents = incidentRepository.findByAffstore(storeId);

            // Combine the prompts (previous answer, preset prompts, and chunk prompt)
            List<String> combinedPrompts = new ArrayList<>();

            combinedPrompts.addAll(presetPrompts);
            combinedPrompts.add("this is the list of past rice amounts" + riceList.toString());
            combinedPrompts.add("this is the list of incidents " + incidents.toString());

            String combinedPrompt = String.join("\n\n", combinedPrompts);

            // Create a completion request with the combined prompt
            CompletionRequest completionRequest = CompletionRequest.defaultWith(combinedPrompt);
            String requestBodyAsJson = objectMapper.writeValueAsString(completionRequest);
            System.out.println("Request JSON: " + requestBodyAsJson); // Print the request JSON for debugging

            // Send request to OpenAI API and get the response JSON
            String responseJson = openAiApiClient.postToOpenAiApi(requestBodyAsJson, service);

            // Parse the JSON response using ObjectMapper
            CompletionResponse completionResponse = objectMapper.readValue(responseJson, CompletionResponse.class);

            // Get the answer from the response
            String answer = completionResponse.firstAnswer().orElse("");
            System.out.println("answer JSON: " + answer); // Print the request JSON for debugging

         // Extract the prediction field from the JSON response
            JsonNode jsonResponse = objectMapper.readTree(answer);
            long extractedPrediction = jsonResponse.get("prediction").asLong();

            // Update the Rice entity with the extracted prediction value
            rice.setPrediction(extractedPrediction);
            riceRepository.save(rice);

            return ResponseEntity.ok(new MessageResponse("Prediction updated successfully!"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Error: Unable to update the prediction."));
        }
    }


    @DeleteMapping("/deletepre/{riceId}")
    public ResponseEntity<?> deletePrediction(@PathVariable Long riceId) {
        try {
            // Check if the rice with the given riceId exists
            Optional<Rice> riceOptional = riceRepository.findById(riceId);
            if (!riceOptional.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            // Remove the prediction field for the rice entity
            Rice rice = riceOptional.get();
            rice.setPrediction(null); // Set prediction to null
            riceRepository.save(rice);

            return ResponseEntity.ok(new MessageResponse("Prediction deleted successfully!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Error: Unable to delete the prediction."));
        }
    }
    
    
}
