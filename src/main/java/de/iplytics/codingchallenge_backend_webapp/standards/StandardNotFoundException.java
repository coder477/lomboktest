package de.iplytics.codingchallenge_backend_webapp.standards;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StandardNotFoundException extends RuntimeException {

	private String message;

}
