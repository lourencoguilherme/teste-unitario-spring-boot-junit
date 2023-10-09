package com.teste.unitario.service;

import com.teste.unitario.usuario.dto.UsuarioDTO;
import com.teste.unitario.usuario.entity.Usuario;
import com.teste.unitario.usuario.repository.UsuarioRepository;
import com.teste.unitario.usuario.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    private UsuarioService usuarioService;

    @MockBean
    private UsuarioRepository usuarioRepository;

    @Test
    public void testCadastrarUsuario() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setCpf("12345678900");
        usuarioDTO.setNome("Nome do Usuário");

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setCpf(usuarioDTO.getCpf());
        usuario.setNome(usuarioDTO.getNome());

        Mockito.when(usuarioRepository.save(Mockito.any())).thenReturn(usuario);

        Usuario result = usuarioService.cadastrarUsuario(usuarioDTO);

        assertEquals(usuarioDTO.getCpf(), result.getCpf());
        assertEquals(usuarioDTO.getNome(), result.getNome());
    }

    @Test
    public void testBuscarUsuarioPorCpf() {
        String cpf = "12345678900";
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setCpf(cpf);
        usuario.setNome("Nome do Usuário");

        Mockito.when(usuarioRepository.findByCpf(cpf)).thenReturn(Optional.of(usuario));

        Usuario result = usuarioService.buscarUsuarioPorCpf(cpf);

        assertEquals(cpf, result.getCpf());
        assertEquals(usuario.getNome(), result.getNome());
    }
}