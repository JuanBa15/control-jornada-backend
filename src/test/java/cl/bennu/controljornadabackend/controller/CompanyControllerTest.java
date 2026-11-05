package cl.bennu.controljornadabackend.controller;

import cl.bennu.controljornadabackend.constants.Constants;
import cl.bennu.controljornadabackend.model.Commune;
import cl.bennu.controljornadabackend.model.Company;
import cl.bennu.controljornadabackend.service.CompanyService;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class CompanyControllerTest {

    private static final String URL = Constants.BASE_API + "/company";

    private static final String COMPANY_JSON = """
            {
                "commune": { "id": 1 },
                "code": "76000011-1",
                "name": "Taller Mecánico Rueda",
                "address": "Av. Lo Espejo 500",
                "phone": "+56911112222",
                "alias": "Rueda",
                "giro": "Mantención de vehículos",
                "email": "taller@rueda.cl",
                "geolocation": true,
                "selfie": false,
                "lag": true
            }
            """;

    @Mock
    private CompanyService companyService;

    @InjectMocks
    private CompanyController companyController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(companyController).build();
    }

    private Company company() {
        Company c = new Company();
        c.setId(1L);
        c.setCommune(new Commune());
        c.getCommune().setId(1L);
        c.setCode("76000001-1");
        c.setName("Bennu SA");
        c.setAddress("Av. Siempre Viva 123");
        c.setGeolocation(true);
        c.setSelfie(false);
        c.setLag(true);
        return c;
    }

    @Test
    void getAll_responde200ConLaLista() throws Exception {
        when(companyService.getAll()).thenReturn(List.of(company()));

        mockMvc.perform(get(URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Bennu SA"));
    }

    @Test
    void getById_responde200ConLaCompany() throws Exception {
        when(companyService.getById(1L)).thenReturn(company());

        mockMvc.perform(get(URL + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.code").value("76000001-1"));
    }

    @Test
    void getById_noExiste_responde404() throws Exception {
        when(companyService.getById(99L)).thenReturn(null);

        mockMvc.perform(get(URL + "/99"))
                .andExpect(status().isNotFound());

        verify(companyService).getById(99L);
    }

    @Test
    void create_convierteElJsonYLlamaAlInsert() throws Exception {
        mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(COMPANY_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Taller Mecánico Rueda"));

        ArgumentCaptor<Company> captor = ArgumentCaptor.forClass(Company.class);
        verify(companyService).insert(captor.capture());
        assertThat(captor.getValue().getCode()).isEqualTo("76000011-1");
        assertThat(captor.getValue().getGeolocation()).isTrue();
    }

    @Test
    void update_usaElIdDeLaUrl() throws Exception {
        when(companyService.getById(5L)).thenReturn(company());

        mockMvc.perform(put(URL + "/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(COMPANY_JSON))
                .andExpect(status().isOk());

        ArgumentCaptor<Company> captor = ArgumentCaptor.forClass(Company.class);
        verify(companyService).update(captor.capture());
        assertThat(captor.getValue().getId()).isEqualTo(5L);
    }

    @Test
    void update_noExiste_responde404() throws Exception {
        when(companyService.getById(99L)).thenReturn(null);

        mockMvc.perform(put(URL + "/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(COMPANY_JSON))
                .andExpect(status().isNotFound());

        verify(companyService, never()).update(any());
    }

    @Test
    void delete_responde200YLlamaAlDelete() throws Exception {
        when(companyService.getById(1L)).thenReturn(company());

        mockMvc.perform(delete(URL + "/1"))
                .andExpect(status().isOk());

        verify(companyService).delete(1L);
    }

    @Test
    void delete_noExiste_responde404() throws Exception {
        when(companyService.getById(99L)).thenReturn(null);

        mockMvc.perform(delete(URL + "/99"))
                .andExpect(status().isNotFound());

        verify(companyService, never()).delete(any());
    }
}