package de.iplytics.codingchallenge_backend_webapp.declarations;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import de.iplytics.codingchallenge_backend_webapp.declaration.Declaration;
import de.iplytics.codingchallenge_backend_webapp.declaration.DeclarationController;
import de.iplytics.codingchallenge_backend_webapp.declaration.DeclarationService;
import de.iplytics.codingchallenge_backend_webapp.patents.Patent;
import de.iplytics.codingchallenge_backend_webapp.standards.Standard;

@RunWith(SpringRunner.class)
@WebMvcTest(DeclarationController.class)
class DeclarationControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private DeclarationService DeclarationService;

    @Test
    public void requestDeclaration_presentInRepo_returnsDeclarationWithCorrectTitle() throws Exception {
    	
        Declaration mockDeclaration = Declaration.builder()
                .patent(new Patent())
                .standard(new Standard())
                .build();
        Optional<Declaration> DeclarationOptional=Optional.of(mockDeclaration);

        given(DeclarationService.getSingleDeclaration(any())).willReturn(DeclarationOptional);

        mvc.perform(get("/Declarations/DE1234A1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("title", is("Method of making cheese")));
    }

    @Test
    public void requestDeclaration_notPresentInRepo_returns404notFound() throws Exception {
        given(DeclarationService.getSingleDeclaration(any())).willThrow(new IllegalArgumentException());

        mvc.perform(get("/Declarations/US12345")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}