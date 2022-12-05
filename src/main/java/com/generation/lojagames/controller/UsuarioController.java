package com.generation.lojagames.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.generation.lojagames.model.Usuario;
import com.generation.lojagames.model.UsuarioLogin;
import com.generation.lojagames.repository.UsuarioRepository;
import com.generation.lojagames.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService; // Importa as regras de negocio e adiciona a variavel usuarioService( var auxiliar)

    @Autowired
    private UsuarioRepository usuarioRepository; // Importa a funcao buscar usuario da classe usuarioRepository.

    @GetMapping("/all")
    public ResponseEntity <List<Usuario>> getAll(){

        return ResponseEntity.ok(usuarioRepository.findAll());

    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getById(@PathVariable Long id){
        return usuarioRepository.findById(id)
                .map(rId -> ResponseEntity.ok(rId))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/logar")
    public ResponseEntity<UsuarioLogin> loginUsuario(@RequestBody Optional <UsuarioLogin> usuarioLogin){

        return usuarioService.autenticarUsuario(usuarioLogin)
                .map(rLogin -> ResponseEntity.status(HttpStatus.OK).body(rLogin))
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Usuario> postUsuario(@Valid @RequestBody Usuario usuario){

        return usuarioService.cadastrarUsuario(usuario)
                .map(rCadastrar-> ResponseEntity.status(HttpStatus.CREATED).body(rCadastrar))
                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Usuario> putUsuario(@Valid @RequestBody Usuario usuario) {
        return usuarioService.atualizarUsuario(usuario)
                .map(rAtualizar-> ResponseEntity.status(HttpStatus.OK).body(rAtualizar))
                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePostagem(@PathVariable Long id) {

        return usuarioRepository.findById(id)
                .map(rDelete -> {
                    usuarioRepository.deleteById(id);
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

}
