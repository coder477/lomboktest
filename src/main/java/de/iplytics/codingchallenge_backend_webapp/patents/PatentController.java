package de.iplytics.codingchallenge_backend_webapp.patents;

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
	@ResponseBody
	public Patent createPatent(@RequestBody Patent patent) {
		return patentService.insertSinglePatent(patent);
	}

	@PutMapping("/{publicationNumber}")
    @ResponseBody
    public Patent updatePatent(@RequestBody Patent patent,@PathVariable("publicationNumber") String id){
        return patentService.updatePatent(patent,id);
    }

	@DeleteMapping("/{publicationNumber}")
	@ResponseBody
	public ResponseEntity deletePatent(@PathVariable("publicationNumber") String id) {
		patentService.deletePatent(id);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Exception> handleNotFoundException(IllegalArgumentException exception) {
		return new ResponseEntity<Exception>(HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InvalidFormatException.class)
	public ResponseEntity<Exception> handleBadRequestException(InvalidFormatException exception) {
		return new ResponseEntity<Exception>(HttpStatus.BAD_REQUEST);
	}

}
