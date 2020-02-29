package de.iplytics.codingchallenge_backend_webapp.declaration;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import de.iplytics.codingchallenge_backend_webapp.patents.Patent;
import de.iplytics.codingchallenge_backend_webapp.patents.PatentNotFoundException;
import de.iplytics.codingchallenge_backend_webapp.patents.PatentRepository;
import de.iplytics.codingchallenge_backend_webapp.standards.Standard;
import de.iplytics.codingchallenge_backend_webapp.standards.StandardNotFoundException;
import de.iplytics.codingchallenge_backend_webapp.standards.StandardRepository;

@Service
public class DeclarationService {

	private DeclarationRepository declarationRepository;
	private PatentRepository patentRepository;
	private StandardRepository standardRepository;

	@Autowired
	public DeclarationService(DeclarationRepository declarationRepository, PatentRepository patentRepository,
			StandardRepository standardRepository) {
		this.declarationRepository = declarationRepository;
		this.patentRepository = patentRepository;
		this.standardRepository = standardRepository;
	}

	public Optional<Declaration> getSingleDeclaration(String declarationId) {
		return declarationRepository.findById(declarationId);
	}

	public Declaration insertSingleDeclaration(DeclarationRequest declarationRequest) {
		Patent patentToAdd = patentRepository.findById(declarationRequest.getPublicationNumber())
				.orElseThrow(() -> new PatentNotFoundException(
						"Cannot find patent ID " + declarationRequest.getPublicationNumber()));
		Standard standardToAdd = standardRepository.findById(declarationRequest.getStandardId()).orElseThrow(
				() -> new StandardNotFoundException("Cannot find standard ID " + declarationRequest.getStandardId()));

		Declaration declaration = Declaration.builder().declarationId(UUID.randomUUID().toString()).patent(patentToAdd)
				.standard(standardToAdd).build();
		return declarationRepository.save(declaration);
	}

	public Declaration updateDeclaration(DeclarationRequest declarationRequest, String declarationId) {

		Declaration declarationToUpdate = declarationRepository.findById(declarationId)
				.orElseThrow(() -> new DeclarationNotFoundException("Cannot find declaration ID " + declarationId));
		if (declarationRequest.getPublicationNumber() != null) {
			Patent patentToAdd = patentRepository.findById(declarationRequest.getPublicationNumber())
					.orElseThrow(() -> new PatentNotFoundException(
							"Cannot find patent ID " + declarationRequest.getPublicationNumber()));
			declarationToUpdate.setPatent(patentToAdd);
		}
		if (declarationRequest.getStandardId() != null) {
			Standard standardToAdd = standardRepository.findById(declarationRequest.getStandardId())
					.orElseThrow(() -> new StandardNotFoundException(
							"Cannot find standard ID " + declarationRequest.getStandardId()));
			declarationToUpdate.setStandard(standardToAdd);

		}

		return declarationRepository.save(declarationToUpdate);

	}

	public void deleteDeclaration(String declarationId) {
		try{
		declarationRepository.deleteById(declarationId);
		}
		catch(EmptyResultDataAccessException e){
			throw new DeclarationNotFoundException("Given Declaration cannot be deleted");
		}
	}
}
