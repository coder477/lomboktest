package de.iplytics.codingchallenge_backend_webapp.declaration;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import de.iplytics.codingchallenge_backend_webapp.patents.PatentNotFoundException;
import de.iplytics.codingchallenge_backend_webapp.standards.StandardNotFoundException;

@RestController
@RequestMapping("/declarations")
public class DeclarationController {

	@Autowired
	DeclarationService declarationService;

	@GetMapping("/{declarationId}")
	public ResponseEntity<Declaration> getDeclaration(@PathVariable("declarationId") String id) {
		return ResponseEntity.of(declarationService.getSingleDeclaration(id));
	}

	@PostMapping("/create")
	public Declaration createDeclaration(@RequestBody DeclarationRequest declarationRequest) {
		return declarationService.insertSingleDeclaration(declarationRequest);
	}

	@PutMapping("/{declarationId}")
	public Declaration updateDeclaration(@RequestBody DeclarationRequest declarationRequest,
			@PathVariable("declarationId") String id) {
		return declarationService.updateDeclaration(declarationRequest, id);
	}

	@DeleteMapping("/{declarationId}")
	public ResponseEntity deleteDeclaration(@PathVariable("declarationId") String id) {
		declarationService.deleteDeclaration(id);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	@ExceptionHandler(DeclarationNotFoundException.class)
	public ResponseEntity<DeclarationErrorResponse> handleNotFoundException(DeclarationNotFoundException exception) {
		return new ResponseEntity<DeclarationErrorResponse>(
				new DeclarationErrorResponse(HttpStatus.NOT_FOUND, exception.getMessage()), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(PatentNotFoundException.class)
	public ResponseEntity<DeclarationErrorResponse> handlePatentNotFoundException(PatentNotFoundException exception) {
		return new ResponseEntity<DeclarationErrorResponse>(
				new DeclarationErrorResponse(HttpStatus.NOT_FOUND, exception.getMessage()), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(StandardNotFoundException.class)
	public ResponseEntity<DeclarationErrorResponse> handleStandardNotFoundException(
			StandardNotFoundException exception) {
		return new ResponseEntity<DeclarationErrorResponse>(
				new DeclarationErrorResponse(HttpStatus.NOT_FOUND, exception.getMessage()), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InvalidFormatException.class)
	public ResponseEntity<DeclarationErrorResponse> handleInvalidFormatException(InvalidFormatException exception) {
		return ResponseEntity.badRequest()
				.body(new DeclarationErrorResponse(HttpStatus.BAD_REQUEST, "Given input Format is Incorrect"));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<DeclarationErrorResponse> handleonMethodArgumentValidationExcepti(
			MethodArgumentNotValidException exception) {
		return ResponseEntity.badRequest()
				.body(new DeclarationErrorResponse(HttpStatus.BAD_REQUEST, "The Input request has Invalid data."));
	}
	
	

}
