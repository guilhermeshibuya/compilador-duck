package br.com.duck.duck;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/compiler")
@CrossOrigin(origins = "http://localhost:5173")
public class DuckController {
	@Autowired
	private DuckService compilerService;
	
	@PostMapping("/compile")
	public ResponseEntity<String> compile(@RequestBody String sourceCode) {
		String result = compilerService.compileCode(sourceCode);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
//	public String compile(@RequestBody String codigoFonte) {
//		return compilerService.compileCode(codigoFonte);
//	}
}
