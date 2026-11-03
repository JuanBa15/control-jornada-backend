package cl.bennu.controljornadabackend.controller;

import cl.bennu.controljornadabackend.constants.Constants;
import cl.bennu.controljornadabackend.model.Country;
import cl.bennu.controljornadabackend.service.CountryService;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class CountryControllerTest {

    private static final String ENDPOINT = Constants.BASE_API + "/country";

    @Mock
    private CountryService countryService;

    @InjectMocks
    private CountryController countryController;

    private MockMvc mockMvc;

    private final Long idOk = 1L;
    private final Long idError = 99L;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(countryController).build();
    }

    private Country country() {
        Country country = new Country();
        country.setId(1L);
        country.setName("Chile");
        country.setNationality("Chilena");
        return country;
    }

    // ---------- GET /v1/template/country ----------

    @Test
    void getAll_ok_retornaLista_return200() throws Exception {
        when(countryService.getAll()).thenReturn(List.of(country()));

        mockMvc.perform(get(ENDPOINT))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Chile"))
                .andExpect(jsonPath("$[0].nationality").value("Chilena"));

        verify(countryService).getAll();
    }

    @Test
    void getAll_vacio_retornaListaVacia_return200() throws Exception {
        when(countryService.getAll()).thenReturn(List.of());

        mockMvc.perform(get(ENDPOINT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    // ---------- GET /v1/template/country/{id} ----------

    @Test
    void getById_ok_retorna200() throws Exception {
        when(countryService.getById(idOk)).thenReturn(country());

        mockMvc.perform(get(ENDPOINT + "/" + idOk))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Chile"))
                .andExpect(jsonPath("$.nationality").value("Chilena"));

        verify(countryService).getById(idOk);
    }

    @Test
    void getById_noExiste_retorna404() throws Exception {
        when(countryService.getById(idError)).thenReturn(null);

        mockMvc.perform(get(ENDPOINT + "/" + idError))
                .andExpect(status().isNotFound());

        verify(countryService).getById(idError);
    }

    @Test
    void getById_idInvalido_retorna400() throws Exception {
        mockMvc.perform(get(ENDPOINT + "/" + "abc"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(countryService);
    }

    // ---------- POST /v1/template/country ----------

    @Test
    void save_insert_conId_return200() throws Exception {
        String json = """
                {
                  "name": "Chile",
                  "nationality": "Chilena"
                }
                """;

        mockMvc.perform(post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Chile"))
                .andExpect(jsonPath("$.nationality").value("Chilena"));

        ArgumentCaptor<Country> captor = ArgumentCaptor.forClass(Country.class);
        verify(countryService).insert(captor.capture());
        assertThat(captor.getValue().getId()).isNull();
        assertThat(captor.getValue().getName()).isEqualTo("Chile");
        assertThat(captor.getValue().getNationality()).isEqualTo("Chilena");
    }

    @Test
    void save_update_conId_return200() throws Exception {
        String body = """
                {
                  "id": 1,
                  "name": "Chile",
                  "nationality": "Chilena"
                }
                """;

        mockMvc.perform(post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        ArgumentCaptor<Country> captor = ArgumentCaptor.forClass(Country.class);
        verify(countryService).update(captor.capture());
        assertThat(captor.getValue().getId()).isEqualTo(1L);
    }

    @Test
    void save_sinId_Body_retorna400() throws Exception {
        mockMvc.perform(post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(countryService, never()).insert(any());
    }

    // ---------- DELETE /v1/template/country/{id} ----------

    @Test
    void delete_ok_return200() throws Exception {
        mockMvc.perform(delete(ENDPOINT + "/" + idOk))
                .andExpect(status().isOk());

        verify(countryService).delete(idOk);
    }

    @Test
    void delete_idInvalido_retorna400() throws Exception {
        mockMvc.perform(delete(ENDPOINT + "/" + "abc"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(countryService);
    }
}