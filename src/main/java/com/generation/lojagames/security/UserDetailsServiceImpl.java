package com.generation.lojagames.security;

import java.util.Optional;

import com.generation.lojagames.model.Usuario;
import com.generation.lojagames.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

//sufixo Impl para indicar que se trata de uma implementação.

// A annotation @Service indica que esta é uma Classe de Serviço, ou seja, implementa regras de negócio da aplicação
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;


    // loadUserByUsername obtem os dados de um usuário com um determinado nome de usuário.  O nome do usuário deve ser único.


    @Override
    public UserDetails loadUserByUsername(String NomeUsuario) throws UsernameNotFoundException {

        // Buscar usuario no banco ==> findByUsuario (foi adicionado a Classe UsuarioRepository)

        Optional<Usuario> usuario = usuarioRepository.findByUsuario(NomeUsuario);

        // SE o usuario existe retorna um objeto UserDetailsImpl com os dados presentes no banco.
        // Caso contrario retorna um Forbidden.(HTTPSTATUS)

        if (usuario.isPresent())
            return new UserDetailsImpl(usuario.get());
        else
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);

    }
}
