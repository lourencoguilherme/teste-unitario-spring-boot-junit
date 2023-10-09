package com.teste.unitario.repository;

import com.teste.unitario.usuario.entity.Usuario;
import com.teste.unitario.usuario.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UsuarioRepositoryIntegrationTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testFindByCpf() {
        // Criar um usuário de exemplo e salvá-lo no banco de dados
        Usuario usuario = new Usuario();
        usuario.setCpf("123456789");
        usuario.setNome("Nome do Usuário");
        entityManager.persistAndFlush(usuario);

        // Realizar a consulta no repositório pelo CPF
        Optional<Usuario> usuarioEncontrado = usuarioRepository.findByCpf("123456789");

        // Verificar se o usuário foi encontrado
        assertTrue(usuarioEncontrado.isPresent());

        // Verificar se os detalhes do usuário correspondem ao esperado
        Usuario usuarioRetornado = usuarioEncontrado.get();
        assertEquals("123456789", usuarioRetornado.getCpf());
        assertEquals("Nome do Usuário", usuarioRetornado.getNome());
    }
}

