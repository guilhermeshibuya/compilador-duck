package br.com.duck.duck;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import br.com.duck.compilador.parser.Compilador;
import br.com.duck.compilador.parser.ParseException;
import br.com.duck.compilador.parser.SimpleNode;
import br.com.duck.compilador.recovery.ParseEOFException;

@Service
public class DuckService {
	private Compilador compiler;
	SimpleNode tree;
	ObjectMapper objectMapper = new ObjectMapper();
	
	
//	public ResponseEntity<String> compileCode(String sourceCode) {
//	    try {
//	        if (compiler == null) {
//	            compiler = new Compilador(new StringReader(sourceCode));
//	        } else {
//	            compiler.ReInit(new StringReader(sourceCode));
//	        }
//	        tree = compiler.Start();
//	        tree.dump(" ");
//
//	        ObjectNode treeJson = convertTreeToJson(tree, objectMapper.createObjectNode());
//
//	        return ResponseEntity.ok(treeJson.toString());
//	    } catch (Exception e) {
//	        System.out.println(String.join("\n", compiler.errors.getErrors()));
//	        return ResponseEntity.ok(String.join("\n", compiler.errors.getErrors()));
//	    }
//	}
	
	public String compileCode(String sourceCode) throws ParseException, ParseEOFException {


			if (compiler == null) {
				compiler = new Compilador(new StringReader(sourceCode));
			} else {
				compiler.ReInit(new StringReader(sourceCode));;
			}
			tree = compiler.Start();
			tree.dump(" ");
			
//			StringBuilder sb = new StringBuilder();
//			treeRepresentation(tree, "", sb);
			ObjectNode treeJson = convertTreeToJson(tree, objectMapper.createObjectNode());
			
			if (compiler.errors.getErrors() != null) {
				return String.join("\n", compiler.errors.getErrors());
			} else {
				return treeJson.toString();
			}
			
			
//			return sb.toString();
			
//			System.out.println(String.join("\n", compiler.errors.getErrors()));
//			

	}
	
	private void treeRepresentation(SimpleNode node, String ident, StringBuilder stringBuilder) {
		stringBuilder.append(ident).append(node).append("\n");
		
		for (int i = 0; i < node.jjtGetNumChildren(); i++) {
			treeRepresentation((SimpleNode) node.jjtGetChild(i), ident + " ", stringBuilder);
		}
	}
	
	private ObjectNode convertTreeToJson(SimpleNode node, ObjectNode jsonNode) {
		jsonNode.put("name", node.toString());
		
		ArrayNode children = jsonNode.putArray("children");
		for (int i = 0; i < node.jjtGetNumChildren(); i++) {
			ObjectNode child = objectMapper.createObjectNode();
			children.add(convertTreeToJson((SimpleNode) node.jjtGetChild(i), child));
		}
		
		return jsonNode;
	}
}
