package de.iplytics.codingchallenge_backend_webapp.patents;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity(name = "patent")
@AllArgsConstructor
@NoArgsConstructor
public class Patent {

    @Id
    @Size(max=255)
    private String publicationNumber;
    private LocalDate publicationDate;
    @Size(max=255)
    private String title;
}
