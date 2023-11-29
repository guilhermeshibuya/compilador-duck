package br.com.duck.duck;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import br.com.duck.compilador.parser.ParseException;
import br.com.duck.compilador.parser.TokenMgrError;
import br.com.duck.compilador.recovery.ParseEOFException;

@RestController
@RequestMapping("/compiler")
@CrossOrigin(origins = "http://localhost:5173")
public class DuckController {
	@Autowired
	private DuckService compilerService;
	ObjectMapper objectMapper = new ObjectMapper();
	
	@PostMapping("/compile")
	public ResponseEntity<String> compile(@RequestBody String sourceCode) {
		try {
			String result = compilerService.compileCode(sourceCode);
			
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (ParseEOFException e) {		
			ObjectNode error = objectMapper.createObjectNode();
			ArrayNode errorArray = objectMapper.createArrayNode();
			errorArray.add(error);
			error.set("compileError", errorArray);

			return new ResponseEntity<>(errorArray.toString(), HttpStatus.BAD_REQUEST);
		} catch (ParseException e) {
			ObjectNode error = objectMapper.createObjectNode();
			ArrayNode errorArray = objectMapper.createArrayNode();
			errorArray.add(error);
			error.set("compileError", errorArray);

			return new ResponseEntity<>(errorArray.toString(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			ObjectNode error = objectMapper.createObjectNode();
			ArrayNode errorArray = objectMapper.createArrayNode();
			errorArray.add(error);
			error.set("compileError", errorArray);

			return new ResponseEntity<>(errorArray.toString(), HttpStatus.BAD_REQUEST);
		} 
	}
}
