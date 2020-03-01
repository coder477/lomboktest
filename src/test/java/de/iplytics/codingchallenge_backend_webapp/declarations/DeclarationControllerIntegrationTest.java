//package de.iplytics.codingchallenge_backend_webapp.declarations;
//
//import static org.hamcrest.core.Is.is;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.doThrow;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import java.time.LocalDate;
//import java.util.Optional;
//
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import de.iplytics.codingchallenge_backend_webapp.declaration.Declaration;
//import de.iplytics.codingchallenge_backend_webapp.declaration.DeclarationController;
//import de.iplytics.codingchallenge_backend_webapp.declaration.DeclarationNotFoundException;
//import de.iplytics.codingchallenge_backend_webapp.declaration.DeclarationService;
//
//@RunWith(SpringRunner.class)
//@WebMvcTest(DeclarationController.class)
//class DeclarationControllerIntegrationTest {
//
//    @Autowired
//    private MockMvc mvc;
//
//    @MockBean
//    private DeclarationService declarationService;
//
//    @Test
//    public void requestDeclaration_presentInRepo_returnsDeclarationWithCorrectTitle() throws Exception {
//    	
//        Declaration mockDeclaration = Declaration.builder()
//                .publicationDate(LocalDate.of(2019,1,1))
//                .publicationNumber("DE1234A1")
//                .title("Method of making cheese")
//                .build();
//        Optional<Declaration> declarationOptional=Optional.of(mockDeclaration);
//
//        given(declarationService.getSingleDeclaration(any())).willReturn(declarationOptional);
//
//        mvc.perform(get("/declarations/DE1234A1")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("title", is("Method of making cheese")));
//    }
//
//    @Test
//    public void requestDeclaration_notPresentInRepo_returns404notFound() throws Exception {
//        given(declarationService.getSingleDeclaration(any())).willThrow(new DeclarationNotFoundException());
//        mvc.perform(get("/declarations/US12345")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotFound());
//    }
//    @Test
//    public void createDeclaration_saveToDb_returnSuccessful() throws Exception {
//    	
//        Declaration mockDeclaration = Declaration.builder()
//                .publicationDate(LocalDate.of(2019,1,1))
//                .publicationNumber("DE1234A2")
//                .title("Method of making tea")
//                .build();
//        given(declarationService.insertSingleDeclaration((any()))).willReturn(mockDeclaration);
//        mvc.perform(post("/declarations/create")
//        		.content(new ObjectMapper().writeValueAsString(mockDeclaration))
//        	    .contentType(MediaType.APPLICATION_JSON)
//        		.accept(MediaType.APPLICATION_JSON))
//        	    .andExpect(status().isOk())
//                .andExpect(jsonPath("publicationNumber", is("DE1234A2")));
//        	   
//    }
//    
//    @Test
//    public void createDeclaration_withInvalidData_return400BadRequest() throws Exception {
//    	 Declaration mockDeclaration = Declaration.builder()
//                 .publicationDate(LocalDate.of(2019,1,1))
//                 .publicationNumber("DE1234A2")
//                 .title("Method of making tea "
//                 		+ "Method of making tea"
//                 		+ "Method of making tea"
//                 		+ "Method of making teaMethod of making teaMethod of making teaMethod of making teaMethod of making teaMethod of making tea"
//                 		+ "Method of making teaMethod of making teaMethod of making teaMethod of making tea")
//                 .build();
//         mvc.perform(post("/declarations/create")
//         		.content(new ObjectMapper().writeValueAsString(mockDeclaration))
//         	    .contentType(MediaType.APPLICATION_JSON)
//         		.accept(MediaType.APPLICATION_JSON))
//         	    .andExpect(status().isBadRequest());
//    	
//    }
//    
//
//    @Test
//    public void updateDeclaration_saveToDb_returnSuccessful() throws Exception {
//    	
//        Declaration mockDeclaration = Declaration.builder()
//                .publicationDate(LocalDate.of(2019,1,1))
//                .publicationNumber("DE1234A2")
//                .title("Method of making tea")
//                .build();
//        Declaration modifiedDeclaration = Declaration.builder()
//        		.publicationDate(LocalDate.of(2019,1,1))
//        		.publicationNumber("DE1234A2")
//        		.title("Method of making tea from Tea leaves")
//        		.build();
//        given(declarationService.updateDeclaration(any(),any())).willReturn(modifiedDeclaration);
//        mvc.perform(put("/declarations/DE1234A2")
// 				.content(new ObjectMapper().writeValueAsString(mockDeclaration))
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("title", is("Method of making tea from Tea leaves")));
//    	
//    }
//    
//    @Test
//    public void updateDeclaration_notPresentinDb_return404NotFound() throws Exception {
//        Declaration mockDeclaration = Declaration.builder()
//                .publicationDate(LocalDate.of(2019,1,1))
//                .publicationNumber("DE1234A2")
//                .title("Method of making tea")
//                .build();
//        given(declarationService.updateDeclaration(any(),any())).willThrow(new DeclarationNotFoundException());
//        mvc.perform(put("/declarations/US12345")
// 				.content(new ObjectMapper().writeValueAsString(mockDeclaration))
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotFound());
//    }
//    
//    @Test
//    public void deleteDeclaration_deleteFromDb_returnSuccessful() throws Exception {
//    	doNothing().when(declarationService).deleteDeclaration(any());
//        mvc.perform(delete("/declarations/DE1234A2")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNoContent());
//    	
//    }
//    
//    @Test
//    public void deleteDeclaration_notPresentinDb_return404NotFound() throws Exception {
//    	
//		doThrow(DeclarationNotFoundException.class).when(declarationService).deleteDeclaration(any());
//        mvc.perform(delete("/declarations/{publicationNumber}","US12345")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotFound());
//    	
//    }
//    
//}