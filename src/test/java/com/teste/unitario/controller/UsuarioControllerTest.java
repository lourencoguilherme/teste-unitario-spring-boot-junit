package com.teste.unitario.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.teste.unitario.usuario.dto.UsuarioDTO;
import com.teste.unitario.usuario.entity.Usuario;
import com.teste.unitario.usuario.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;


@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Test
    public void dado_que_informo_usuario_correto_entao_cadastra_usuario_com_status_201_created() throws Exception {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setCpf("12345678900");
        usuarioDTO.setNome("Nome do Usuário");

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setCpf(usuarioDTO.getCpf());
        usuario.setNome(usuarioDTO.getNome());

        Mockito.when(usuarioService.cadastrarUsuario(Mockito.any())).thenReturn(usuario);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(usuarioDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cpf", is(usuarioDTO.getCpf())))
                .andExpect(jsonPath("$.nome", is(usuarioDTO.getNome())));
    }

    @Test
    public void dado_que_informo_um_cpf_valido_entao_retorna_usuario_com_status_200_ok() throws Exception {
        String cpf = "12345678900";
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setCpf(cpf);
        usuario.setNome("Nome do Usuário");

        Mockito.when(usuarioService.buscarUsuarioPorCpf(cpf)).thenReturn(usuario);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/usuarios/{cpf}", cpf)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cpf", is(cpf)))
                .andExpect(jsonPath("$.nome", is(usuario.getNome())));
    }

    // Método utilitário para converter um objeto para JSON
    private String asJsonString(Object obj) throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.writeValueAsString(obj);
    }
}
