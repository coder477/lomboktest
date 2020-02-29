package de.iplytics.codingchallenge_backend_webapp.standards;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Builder
@Entity(name = "standard")
@AllArgsConstructor
@NoArgsConstructor
public class Standard {

    @Id
    private String standardId;

    private String name;
    private String description;
}
