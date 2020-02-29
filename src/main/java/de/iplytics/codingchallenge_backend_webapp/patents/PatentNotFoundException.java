package de.iplytics.codingchallenge_backend_webapp.patents;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatentNotFoundException extends RuntimeException {

	private String message;

}
