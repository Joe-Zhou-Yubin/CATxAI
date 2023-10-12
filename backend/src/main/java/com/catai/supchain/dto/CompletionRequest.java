package com.catai.supchain.dto;

public record CompletionRequest(String model, String prompt, 
		double temperature, int max_tokens) {
	
	public static CompletionRequest defaultWith(String prompt) {
		return new CompletionRequest("text-davinci-003", prompt, 0.7, 1000);//token limit
	}
	
}