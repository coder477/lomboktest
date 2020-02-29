package de.iplytics.codingchallenge_backend_webapp.declaration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeclarationRequest {
	private String declarationId;
	private String publicationNumber;
	private String standardId;

}
