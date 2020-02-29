package de.iplytics.codingchallenge_backend_webapp.declaration;


import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeclarationErrorResponse {
	private HttpStatus value;
    private String message;

    
}
