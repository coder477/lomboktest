package de.iplytics.codingchallenge_backend_webapp.patents;


import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatentErrorResponse {
	private HttpStatus value;
    private String message;

    
}
