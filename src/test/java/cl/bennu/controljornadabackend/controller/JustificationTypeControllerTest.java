package cl.bennu.controljornadabackend.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import cl.bennu.controljornadabackend.constants.Constants;
import cl.bennu.controljornadabackend.service.JustificationTypeService;
import cl.bennu.controljornadabackend.model.JustificationType;

@ExtendWith(MockitoExtension.class)
public class JustificationTypeControllerTest {
    private static final String ENDPOINT = Constants.BASE_API + "/justification-type";

    @Mock
    private JustificationTypeService justificationTypeService;

    @InjectMocks
    private JustificationTypeController justificationTypeController;

    private MockMvc mockMvc;

    private final Long idOk = 1L;
    private final Long idError = 99L;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(justificationTypeController).build();
    }

    private JustificationType JustificationType() {
        JustificationType justificationType = new JustificationType();
        justificationType.setId(1L);
        justificationType.setName("Descanso médico");

        return justificationType;
    }

    // ---------- GET /v1/template/justification-type ----------

    @Test
    void getAll_ok_retornaLista_return200() throws Exception {
        when(justificationTypeService.getAll()).thenReturn(List.of(JustificationType()));

        mockMvc.perform(get(ENDPOINT))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Descanso médico"));

        verify(justificationTypeService).getAll();
    }

    @Test
    void getAll_vacio_retornaListaVacia_return200() throws Exception {
        when(justificationTypeService.getAll()).thenReturn(List.of());

        mockMvc.perform(get(ENDPOINT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    // ---------- GET /v1/template/justification-type/{id} ----------

    @Test
    void getById_ok_retorna200() throws Exception {
        when(justificationTypeService.getById(idOk)).thenReturn(JustificationType());

        mockMvc.perform(get(ENDPOINT + "/" + idOk))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Descanso médico"));

        verify(justificationTypeService).getById(idOk);
    }

    @Test
    void getById_noExiste_retorna404() throws Exception {
        when(justificationTypeService.getById(idError)).thenReturn(null);

        mockMvc.perform(get(ENDPOINT + "/" + idError))
                .andExpect(status().isNotFound());

        verify(justificationTypeService).getById(idError);
    }

    @Test
    void getById_idInvalido_retorna400() throws Exception {
        mockMvc.perform(get(ENDPOINT + "/" + "abc"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(justificationTypeService);
    }

    // ---------- POST /v1/template/justification-type ----------

    @Test
    void create_sinId_return200() throws Exception {
        String json = """
                {
                  "name": "Descanso médico"
                }
        """;

        mockMvc.perform(post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Descanso médico"));

        ArgumentCaptor<JustificationType> captor = ArgumentCaptor.forClass(JustificationType.class);
        verify(justificationTypeService).create(captor.capture());
        assertThat(captor.getValue().getId()).isNull();
        assertThat(captor.getValue().getName()).isEqualTo("Descanso médico");
    }

    @Test
    void create_sinBody_retorna400() throws Exception {
        mockMvc.perform(post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(justificationTypeService, never()).create(any());
    }

    // ---------- PUT /v1/template/justification-type ----------

    @Test
    void update_conId_return200() throws Exception {
        String body = """
                {
                  "id": 1,
                  "name": "Descanso médico"
                }
        """;

        mockMvc.perform(put(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        ArgumentCaptor<JustificationType> captor = ArgumentCaptor.forClass(JustificationType.class);
        verify(justificationTypeService).update(captor.capture());
        assertThat(captor.getValue().getId()).isEqualTo(1L);
    }

    @Test
    void update_sinBody_retorna400() throws Exception {
        mockMvc.perform(put(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(justificationTypeService, never()).update(any());
    }

    // ---------- DELETE /v1/template/justification-type/{id} ----------

    @Test
    void delete_ok_return200() throws Exception {
        mockMvc.perform(delete(ENDPOINT + "/" + idOk))
                .andExpect(status().isOk());

        verify(justificationTypeService).delete(idOk);
    }

    @Test
    void delete_idInvalido_retorna400() throws Exception {
        mockMvc.perform(delete(ENDPOINT + "/" + "abc"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(justificationTypeService);
    }
}
