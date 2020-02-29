package de.iplytics.codingchallenge_backend_webapp.declaration;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.iplytics.codingchallenge_backend_webapp.patents.Patent;
import de.iplytics.codingchallenge_backend_webapp.patents.PatentRepository;
import de.iplytics.codingchallenge_backend_webapp.standards.Standard;
import de.iplytics.codingchallenge_backend_webapp.standards.StandardRepository;

@Service
public class DeclarationService {

	private DeclarationRepository declarationRepository;
	@Autowired
	private PatentRepository patentRepository;
	@Autowired
	private StandardRepository standardRepository;

	@Autowired
	public DeclarationService(DeclarationRepository declarationRepository) {
		this.declarationRepository = declarationRepository;
	}

	public Optional<Declaration> getSingleDeclaration(String declarationId) {
		return declarationRepository.findById(declarationId);
	}

	public Declaration insertSingleDeclaration(DeclarationRequest declarationRequest) {
		System.out.println(declarationRequest);
		String standardId=declarationRequest.getStandardId();
		Optional<Standard> stand=standardRepository.findById(standardId);
		Declaration declaration = Declaration.builder()
                .declarationId(UUID.randomUUID().toString())
                .patent(patentRepository.findById(declarationRequest.getPublicationNumber()).get())
                .standard(standardRepository.findById(standardId))
                .build();
		
		System.out.println(declaration+" qwbhfevidg ");

		return declarationRepository.save(declaration);
	}

	public Declaration updateDeclaration(Declaration declaration, String declarationId) {
		Declaration declarationToUpdate = declarationRepository.findById(declarationId)
				.orElseThrow(() -> new IllegalArgumentException("Cannot find declaration ID " + declarationId));

		return declarationRepository.save(declarationToUpdate);

	}

	public void deleteDeclaration(String declarationId) {
		declarationRepository.deleteById(declarationId);
	}
}
