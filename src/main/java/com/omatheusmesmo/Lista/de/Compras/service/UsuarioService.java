package com.omatheusmesmo.Lista.de.Compras.service;

import com.omatheusmesmo.Lista.de.Compras.Entity.Usuario;
import com.omatheusmesmo.Lista.de.Compras.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario adicionarUser(Usuario usuario){
        validarSeDadosEstaoNulosOuBrancos(usuario);
        validarSeUsuarioExiste(usuario.getEmail());
        usuarioRepository.save(usuario);
        return usuario;
    }

    public void validarSeUsuarioExiste(String email){
        Optional<Usuario> usuario = usuarioRepository
                .findByEmail(email);
        if(usuario.isPresent()){
            throw new IllegalArgumentException("e-mail já está sendo utilizado!");
        }
    }

    public void validarSeDadosEstaoNulosOuBrancos(Usuario usuario){
        String email = usuario.getEmail();
        if(email.isBlank()){
            throw new IllegalArgumentException("Preencha o e-mail corretamente!");
        }
        String senha = usuario.getSenha();
        if (senha.isBlank()){
            throw new IllegalArgumentException("É necessário preencher a senha!")
        }
    }
    public Usuario editarUsuario(Usuario usario){
        usuarioRepository.save(usario);
        return usario;
    }
}
