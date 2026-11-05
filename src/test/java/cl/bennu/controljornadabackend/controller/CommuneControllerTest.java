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
import cl.bennu.controljornadabackend.service.CommuneService;
import cl.bennu.controljornadabackend.model.Commune;
import cl.bennu.controljornadabackend.model.City;
import cl.bennu.controljornadabackend.model.Region;
import cl.bennu.controljornadabackend.model.Country;

@ExtendWith(MockitoExtension.class)
public class CommuneControllerTest {
    private static final String ENDPOINT = Constants.BASE_API + "/commune";

    @Mock
    private CommuneService communeService;

    @InjectMocks
    private CommuneController communeController;

    private MockMvc mockMvc;

    private final Long idOk = 1L;
    private final Long idError = 99L;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(communeController).build();
    }

    private Commune Commune() {
        Commune c = new Commune();

        c.setId(1L);
        c.setName("Santiago");
        c.setSiiCode("13101");
        c.setTgrCode("070");

        City city = new City();
        city.setId(1L);
        city.setName("Santiago");
        c.setCity(city);

        Region region = new Region();
        region.setId(1L);
        region.setName("RM");
        c.setRegion(region);

        Country country = new Country();
        country.setId(1L);
        country.setName("Chile");
        c.setCountry(country);

        return c;
    }

    // ---------- GET /v1/template/commune ----------

    @Test
    void getAll_ok_retornaLista_return200() throws Exception {
        when(communeService.getAll()).thenReturn(List.of(Commune()));

        mockMvc.perform(get(ENDPOINT))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Santiago"))
                .andExpect(jsonPath("$[0].siiCode").value("13101"))
                .andExpect(jsonPath("$[0].tgrCode").value("070"))
                .andExpect(jsonPath("$[0].city.name").value("Santiago"))
                .andExpect(jsonPath("$[0].region.name").value("RM"))
                .andExpect(jsonPath("$[0].country.name").value("Chile"));

        verify(communeService).getAll();
    }

    @Test
    void getAll_vacio_retornaListaVacia_return200() throws Exception {
        when(communeService.getAll()).thenReturn(List.of());

        mockMvc.perform(get(ENDPOINT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    // ---------- GET /v1/template/commune/{id} ----------

    @Test
    void getById_ok_retorna200() throws Exception {
        when(communeService.getById(idOk)).thenReturn(Commune());

        mockMvc.perform(get(ENDPOINT + "/" + idOk))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Santiago"))
                .andExpect(jsonPath("$.siiCode").value("13101"))
                .andExpect(jsonPath("$.tgrCode").value("070"))
                .andExpect(jsonPath("$.city.name").value("Santiago"))
                .andExpect(jsonPath("$.region.name").value("RM"))
                .andExpect(jsonPath("$.country.name").value("Chile"));

        verify(communeService).getById(idOk);
    }

    @Test
    void getById_noExiste_retorna404() throws Exception {
        when(communeService.getById(idError)).thenReturn(null);

        mockMvc.perform(get(ENDPOINT + "/" + idError))
                .andExpect(status().isNotFound());

        verify(communeService).getById(idError);
    }

    @Test
    void getById_idInvalido_retorna400() throws Exception {
        mockMvc.perform(get(ENDPOINT + "/" + "abc"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(communeService);
    }

    // ---------- POST /v1/template/commune ----------

    @Test
    void create_sinId_return200() throws Exception {
        String json = """
                {
                  "cityId": 1,
                  "name": "Santiago",
                  "siiCode": "13101",
                  "tgrCode": "070"
                }
        """;

        mockMvc.perform(post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Santiago"));

        ArgumentCaptor<Commune> captor = ArgumentCaptor.forClass(Commune.class);
        verify(communeService).create(captor.capture());
        assertThat(captor.getValue().getId()).isNull();
        assertThat(captor.getValue().getName()).isEqualTo("Santiago");
    }

    @Test
    void create_sinBody_retorna400() throws Exception {
        mockMvc.perform(post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(communeService, never()).create(any());
    }

    // ---------- PUT /v1/template/commune ----------

    @Test
    void update_conId_return200() throws Exception {
        String body = """
                {
                  "id": 1,  
                  "cityId": 1,
                  "name": "Santiago",
                  "siiCode": "13101",
                  "tgrCode": "070"
                }
        """;

        mockMvc.perform(put(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        ArgumentCaptor<Commune> captor = ArgumentCaptor.forClass(Commune.class);
        verify(communeService).update(captor.capture());
        assertThat(captor.getValue().getId()).isEqualTo(1L);
    }

    @Test
    void update_sinBody_retorna400() throws Exception {
        mockMvc.perform(put(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(communeService, never()).update(any());
    }

    // ---------- DELETE /v1/template/commune/{id} ----------

    @Test
    void delete_ok_return200() throws Exception {
        mockMvc.perform(delete(ENDPOINT + "/" + idOk))
                .andExpect(status().isOk());

        verify(communeService).delete(idOk);
    }

    @Test
    void delete_idInvalido_retorna400() throws Exception {
        mockMvc.perform(delete(ENDPOINT + "/" + "abc"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(communeService);
    }
}
