package cl.bennu.controljornadabackend.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import cl.bennu.controljornadabackend.constants.Constants;
import cl.bennu.controljornadabackend.service.GenderService;
import cl.bennu.controljornadabackend.model.Gender;

@ExtendWith(MockitoExtension.class)
public class GenderControllerTest {
    private static final String ENDPOINT = Constants.BASE_API + "/gender";

    @Mock
    private GenderService genderService;

    @InjectMocks
    private GenderController genderController;

    private MockMvc mockMvc;

    private final Long idOk = 1L;
    private final Long idError = 99L;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(genderController).build();
    }

    private Gender Gender() {
        Gender gender = new Gender();
        gender.setId(1L);
        gender.setName("Masculino");

        return gender;
    }

    // ---------- GET /v1/template/justification-type ----------

    @Test
    void getAll_ok_retornaLista_return200() throws Exception {
        when(genderService.getAll()).thenReturn(List.of(Gender()));

        mockMvc.perform(get(ENDPOINT))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Masculino"));

        verify(genderService).getAll();
    }

    @Test
    void getAll_vacio_retornaListaVacia_return200() throws Exception {
        when(genderService.getAll()).thenReturn(List.of());

        mockMvc.perform(get(ENDPOINT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    // ---------- GET /v1/template/justification-type/{id} ----------

    @Test
    void getById_ok_retorna200() throws Exception {
        when(genderService.getById(idOk)).thenReturn(Gender());

        mockMvc.perform(get(ENDPOINT + "/" + idOk))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Masculino"));

        verify(genderService).getById(idOk);
    }

    @Test
    void getById_noExiste_retorna404() throws Exception {
        when(genderService.getById(idError)).thenReturn(null);

        mockMvc.perform(get(ENDPOINT + "/" + idError))
                .andExpect(status().isNotFound());

        verify(genderService).getById(idError);
    }

    @Test
    void getById_idInvalido_retorna400() throws Exception {
        mockMvc.perform(get(ENDPOINT + "/" + "abc"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(genderService);
    }
}

