package de.iplytics.codingchallenge_backend_webapp.declaration;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeclarationRepository extends CrudRepository<Declaration, String> {
}
