package br.com.springboot.curso_jdev_treinamento.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.springboot.curso_jdev_treinamento.model.Usuario;
import br.com.springboot.curso_jdev_treinamento.repository.UsuarioRepository;

/**
 * Um controlador de saudação de amostra para retornar o texto de saudação
 */
@RestController
public class GreetingsController {

	@Autowired /* IC/CD ou CDI - Injeção de dependencia */
	private UsuarioRepository usuarioRepository;

	/**
	 *
	 * @param name the name to greet
	 * @return greeting text
	 */
	@RequestMapping(value = "/mostranome/{name}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public String greetingText(@PathVariable String name) {
		return "Curso de spring boot API: " + name + "!";
	}

	@RequestMapping(value = "/olamundo/{nome}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public String retornaOlaMundo(@PathVariable String nome) {

		Usuario usuario = new Usuario();
		usuario.setNome(nome);

		usuarioRepository.save(usuario);/* grava no banco de dados */

		return "Olá mundo " + nome;

	}

	@GetMapping(value = "listatodos")/*Nosso primeiro metodo de API*/
	@ResponseBody /* retorna os dados para o corpo da resposta, retorna em json */
	
	public ResponseEntity<List<Usuario>> listaUsuario() {

		List<Usuario> usuarios = usuarioRepository.findAll();/* executa a consulta no banco de dados */

		return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK); /* Retorna a listaem JSON */

	}
	
	@PostMapping(value = "salvar") /*Mapea a url*/
	@ResponseBody /*Descrição da resposta */
	
	public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario) {/*Recebe os dados para salvar*/

		Usuario user = usuarioRepository.save(usuario);/* executa a consulta no banco de dados */

		return new ResponseEntity<Usuario>(user, HttpStatus.CREATED); /* Retorna a listaem JSON */

	}
	
	@PutMapping(value = "atualizar") /*Mapea a url*/
	@ResponseBody /*Descrição da resposta */
	
	public ResponseEntity<?> atualizar(@RequestBody Usuario usuario) {/*Recebe os dados para salvar*/
		
		if(usuario.getId() == null) {
			
			return new ResponseEntity<String>("Id não foi informado para atualização", HttpStatus.OK); /* Retorna a listaem JSON */
		}

		Usuario user = usuarioRepository.saveAndFlush(usuario);/* executa a consulta no banco de dados */

		return new ResponseEntity<Usuario>(user, HttpStatus.OK); /* Retorna a listaem JSON */
	
	}
		
	@DeleteMapping(value = "delete") /*Mapea a url*/
	@ResponseBody /*Descrição da resposta */
	
	public ResponseEntity<String> delete(@RequestParam Long iduser) {/*Recebe os dados para salvar*/

	 usuarioRepository.deleteById(iduser);/* executa a consulta no banco de dados */

		return new ResponseEntity<String>("User deletado com sucesso", HttpStatus.OK); /* Retorna a listaem JSON */

	}
	
	@GetMapping(value = "buscaruserid") /*Mapea a url*/
	@ResponseBody /*Descrição da resposta */
	
	public ResponseEntity<Usuario> buscaruserid(@RequestParam (name = "iduser")Long iduser) {/*Recebe os dados para salvar*/

			Usuario usuario = usuarioRepository.findById(iduser).get();/* executa a consulta no banco de dados */

		return new ResponseEntity<Usuario>(usuario, HttpStatus.OK); /* Retorna a listaem JSON */

	}
		
	@GetMapping(value = "buscarPorNome") /*Mapea a url*/
	@ResponseBody /*Descrição da resposta */
	
	public ResponseEntity<List<Usuario>> buscarPorNome(@RequestParam (name = "name")String name) {/*Recebe os dados para salvar*/

			List<Usuario> usuario = usuarioRepository.buscarPorNome(name.trim().toUpperCase());/* executa a consulta no banco de dados */

		return new ResponseEntity<List<Usuario>>(usuario, HttpStatus.OK); /* Retorna a listaem JSON */

	}
	
		
	
}
