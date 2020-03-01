package de.iplytics.codingchallenge_backend_webapp.patents;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PatentUnitTests {

	@Mock
	PatentRepository patentRepository;
	@InjectMocks
	PatentService patentService ;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

	}

	@Test
	public void getASinglePatentTest() {
		String mockPublicationNumber = "DE1234A2";
		Patent mockPatent = Patent.builder().publicationDate(LocalDate.of(2019, 1, 1))
				.publicationNumber(mockPublicationNumber).title("Method of making tea").build();
		Optional<Patent> patentOptional = Optional.of(mockPatent);
		when(patentRepository.findById(mockPublicationNumber)).thenReturn(patentOptional);
		assertEquals(patentService.getSinglePatent(mockPublicationNumber), patentOptional);
		verify(patentRepository).findById(mockPublicationNumber);

	}
	
	@Test
	public void createASinglePatentTest() {
		String mockPublicationNumber = "DE1234A2";
		Patent mockPatent = Patent.builder().publicationDate(LocalDate.of(2019, 1, 1))
				.publicationNumber(mockPublicationNumber).title("Method of making tea").build();
		when(patentRepository.save(mockPatent)).thenReturn(mockPatent);
		assertEquals(patentService.insertSinglePatent(mockPatent).getTitle(),"Method of making tea");
		verify(patentRepository).save(mockPatent);

	}
	
	@Test
	public void updateASinglePatentTest() {
		String mockPublicationNumber = "DE1234A2";
		Patent mockPatent = Patent.builder().publicationDate(LocalDate.of(2019, 1, 1))
				.publicationNumber(mockPublicationNumber).title("Method of making tea").build();
		Optional<Patent> patentOptional = Optional.of(mockPatent);
		Patent updatedMockPatent = Patent.builder().publicationDate(LocalDate.of(2019, 1, 1))
				.publicationNumber(mockPublicationNumber).title("Method of making tea bags").build();
		when(patentRepository.findById(mockPublicationNumber)).thenReturn(patentOptional);
		when(patentRepository.save(updatedMockPatent)).thenReturn(updatedMockPatent);
		assertEquals(patentService.updatePatent(updatedMockPatent, mockPublicationNumber),updatedMockPatent);
		verify(patentRepository).save(updatedMockPatent);

	}
	
	@Test
	public void deleteASinglePatentTest() {
		String mockPublicationNumber = "DE1234A2";
		Patent mockPatent = Patent.builder().publicationDate(LocalDate.of(2019, 1, 1))
				.publicationNumber(mockPublicationNumber).title("Method of making tea").build();
		Optional<Patent> patentOptional = Optional.of(mockPatent);


	}
}
