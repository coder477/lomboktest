package de.iplytics.codingchallenge_backend_webapp.patents;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import de.iplytics.codingchallenge_backend_webapp.declaration.DeclarationNotFoundException;

@Service
public class PatentService {

	public PatentRepository patentRepository;

	@Autowired
	public PatentService(PatentRepository patentRepository) {
		this.patentRepository = patentRepository;
	}

	public Optional<Patent> getSinglePatent(String publicationNumber) {
		return patentRepository.findById(publicationNumber);
	}

	public Patent insertSinglePatent(Patent patent) {
		return patentRepository.save(patent);
	}

	public Patent updatePatent(Patent patent, String publicationNumber) {
		Patent patentToUpdate = patentRepository.findById(publicationNumber)
				.orElseThrow(() -> new PatentNotFoundException("Cannot find patent ID " + publicationNumber));
		patentToUpdate.setPublicationNumber(patent.getPublicationNumber());
		patentToUpdate.setPublicationDate(patent.getPublicationDate());
		patentToUpdate.setTitle(patent.getTitle());
		return patentRepository.save(patentToUpdate);

	}

	public void deletePatent(String publicationNumber) {
		System.out.println("hereeee dels");

		try {
			System.out.println("hereeee del try");

			patentRepository.deleteById(publicationNumber);
		} catch (EmptyResultDataAccessException e) {
			System.out.println("hereeee del catch");

			throw new PatentNotFoundException("Given Patent cannot be deleted");
		}
	}
}
