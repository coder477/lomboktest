package de.iplytics.codingchallenge_backend_webapp.declaration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeclarationNotFoundException extends RuntimeException {

	private String message;

}
