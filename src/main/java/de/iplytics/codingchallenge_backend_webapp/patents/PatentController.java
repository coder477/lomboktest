package de.iplytics.codingchallenge_backend_webapp.patents;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

@RestController
@RequestMapping("/patents")
public class PatentController {

	@Autowired
	PatentService patentService;

	@GetMapping("/{publicationNumber}")
	public ResponseEntity<Patent> getPatent(@PathVariable("publicationNumber") String id) {
		return ResponseEntity.of(patentService.getSinglePatent(id));
	}

	@PostMapping("/create")
	public Patent createPatent(@Valid @RequestBody Patent patent) {
		return patentService.insertSinglePatent(patent);
	}

	@PutMapping("/{publicationNumber}")
	public Patent updatePatent(@Valid @RequestBody Patent patent, @PathVariable("publicationNumber") String id) {
		return patentService.updatePatent(patent, id);
	}

	@DeleteMapping("/{publicationNumber}")
	public ResponseEntity deletePatent(@PathVariable("publicationNumber") String id) {
		patentService.deletePatent(id);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	@ExceptionHandler(PatentNotFoundException.class)
	public ResponseEntity<PatentErrorResponse> handleNotFoundException(PatentNotFoundException exception) {
		return new ResponseEntity<PatentErrorResponse>(
				new PatentErrorResponse(HttpStatus.NOT_FOUND, exception.getMessage()), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InvalidFormatException.class)
	public ResponseEntity<PatentErrorResponse> handleInvalidFormatException(InvalidFormatException exception) {
		return ResponseEntity.badRequest()
				.body(new PatentErrorResponse(HttpStatus.BAD_REQUEST, "Given input Format is Incorrect"));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<PatentErrorResponse> handleonMethodArgumentValidationExcepti(
			MethodArgumentNotValidException exception) {
		return ResponseEntity.badRequest()
				.body(new PatentErrorResponse(HttpStatus.BAD_REQUEST, "Given Input request has invalid data."));
	}

}
