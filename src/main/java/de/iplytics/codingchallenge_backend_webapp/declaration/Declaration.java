package de.iplytics.codingchallenge_backend_webapp.declaration;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import de.iplytics.codingchallenge_backend_webapp.patents.Patent;
import de.iplytics.codingchallenge_backend_webapp.standards.Standard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity(name = "declaration")
@AllArgsConstructor
@NoArgsConstructor
public class Declaration {

    @Id
    private String declarationId;
    @ManyToOne
    @JoinColumn(name = "publicationNumber")
    private Patent patent;
    @ManyToOne
    @JoinColumn(name = "standardId")
    private Standard standard;
}
