package de.iplytics.codingchallenge_backend_webapp.declaration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeclarationRequest {
	private String publicationNumber;
	private String standardId;

}
