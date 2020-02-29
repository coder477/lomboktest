package de.iplytics.codingchallenge_backend_webapp.declaration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	@ResponseBody
	public Declaration createDeclaration(@RequestBody DeclarationRequest declarationRequest) {
		return declarationService.insertSingleDeclaration(declarationRequest);
	}

	@PutMapping("/{declarationId}")
    @ResponseBody
    public Declaration updateDeclaration(@RequestBody Declaration declaration,@PathVariable("declarationId") String id){
        return declarationService.updateDeclaration(declaration,id);
    }

	@DeleteMapping("/{declarationId}")
	@ResponseBody
	public ResponseEntity deleteDeclaration(@PathVariable("declarationId") String id) {
		declarationService.deleteDeclaration(id);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Exception> handleIOException(IllegalArgumentException exception) {
		return new ResponseEntity<Exception>(HttpStatus.NOT_FOUND);
	}

}
