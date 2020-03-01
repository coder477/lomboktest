package de.iplytics.codingchallenge_backend_webapp.patents;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito.BDDMyOngoingStubbing;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(PatentController.class)
class PatentControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PatentService patentService;

    @Test
    public void requestPatent_presentInRepo_returnsPatentWithCorrectTitle() throws Exception {
    	
        Patent mockPatent = Patent.builder()
                .publicationDate(LocalDate.of(2019,1,1))
                .publicationNumber("DE1234A1")
                .title("Method of making cheese")
                .build();
        Optional<Patent> patentOptional=Optional.of(mockPatent);

        given(patentService.getSinglePatent(any())).willReturn(patentOptional);

        mvc.perform(get("/patents/DE1234A1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("title", is("Method of making cheese")));
    }

    @Test
    public void requestPatent_notPresentInRepo_returns404notFound() throws Exception {
        given(patentService.getSinglePatent(any())).willThrow(new PatentNotFoundException());
        mvc.perform(get("/patents/US12345")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    @Test
    public void createPatent_saveToDb_returnSuccessful() throws Exception {
    	
        Patent mockPatent = Patent.builder()
                .publicationDate(LocalDate.of(2019,1,1))
                .publicationNumber("DE1234A2")
                .title("Method of making tea")
                .build();
        given(patentService.insertSinglePatent((any()))).willReturn(mockPatent);
        mvc.perform(post("/patents/create")
        		.content(new ObjectMapper().writeValueAsString(mockPatent))
        	    .contentType(MediaType.APPLICATION_JSON)
        		.accept(MediaType.APPLICATION_JSON))
        	    .andExpect(status().isOk())
                .andExpect(jsonPath("publicationNumber", is("DE1234A2")));
        	   
    }
    
    @Test
    public void createPatent_withInvalidData_return400BadRequest() throws Exception {
    	 Patent mockPatent = Patent.builder()
                 .publicationDate(LocalDate.of(2019,1,1))
                 .publicationNumber("DE1234A2")
                 .title("Method of making tea "
                 		+ "Method of making tea"
                 		+ "Method of making tea"
                 		+ "Method of making teaMethod of making teaMethod of making teaMethod of making teaMethod of making teaMethod of making tea"
                 		+ "Method of making teaMethod of making teaMethod of making teaMethod of making tea")
                 .build();
         mvc.perform(post("/patents/create")
         		.content(new ObjectMapper().writeValueAsString(mockPatent))
         	    .contentType(MediaType.APPLICATION_JSON)
         		.accept(MediaType.APPLICATION_JSON))
         	    .andExpect(status().isBadRequest());
    	
    }
    

    @Test
    public void updatePatent_saveToDb_returnSuccessful() throws Exception {
    	
        Patent mockPatent = Patent.builder()
                .publicationDate(LocalDate.of(2019,1,1))
                .publicationNumber("DE1234A2")
                .title("Method of making tea")
                .build();
        Patent modifiedPatent = Patent.builder()
        		.publicationDate(LocalDate.of(2019,1,1))
        		.publicationNumber("DE1234A2")
        		.title("Method of making tea from Tea leaves")
        		.build();
        given(patentService.updatePatent(any(),any())).willReturn(modifiedPatent);
        mvc.perform(put("/patents/DE1234A2")
 				.content(new ObjectMapper().writeValueAsString(mockPatent))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("title", is("Method of making tea from Tea leaves")));
    	
    }
    
    @Test
    public void updatePatent_notPresentinDb_return404NotFound() throws Exception {
        Patent mockPatent = Patent.builder()
                .publicationDate(LocalDate.of(2019,1,1))
                .publicationNumber("DE1234A2")
                .title("Method of making tea")
                .build();
        given(patentService.updatePatent(any(),any())).willThrow(new PatentNotFoundException());
        mvc.perform(put("/patents/US12345")
 				.content(new ObjectMapper().writeValueAsString(mockPatent))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    
    @Test
    public void deletePatent_deleteFromDb_returnSuccessful() throws Exception {
    	doNothing().when(patentService).deletePatent(any());
        mvc.perform(delete("/patents/DE1234A2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    	
    }
    
    @Test
    public void deletePatent_notPresentinDb_return404NotFound() throws Exception {
    	
		doThrow(PatentNotFoundException.class).when(patentService).deletePatent(any());
        mvc.perform(delete("/patents/{publicationNumber}","US12345")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    	
    }
    
}