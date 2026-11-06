package cl.bennu.controljornadabackend.controller;

import cl.bennu.controljornadabackend.constants.Constants;
import cl.bennu.controljornadabackend.model.ContractType;
import cl.bennu.controljornadabackend.service.ContractTypeService;
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
class ContractTypeControllerTest {
    private static final String URL = Constants.BASE_API + "/contract-type";
    private static final String JSON = "{\"name\":\"Indefinido\"}";
    @Mock private ContractTypeService service;
    @InjectMocks private ContractTypeController controller;
    private MockMvc mockMvc;

    @BeforeEach void setUp() { mockMvc = MockMvcBuilders.standaloneSetup(controller).build(); }

    private ContractType contractType() { ContractType value = new ContractType(); value.setId(1L); value.setName("Indefinido"); return value; }

    @Test void getAll_responde200() throws Exception {
        when(service.getAllContractTypes()).thenReturn(List.of(contractType()));
        mockMvc.perform(get(URL)).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(1)));
    }

    @Test void getById_responde200() throws Exception {
        when(service.getContractTypeById(1L)).thenReturn(contractType());
        mockMvc.perform(get(URL + "/1")).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(1));
    }

    @Test void create_llamaAlInsert() throws Exception {
        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(JSON)).andExpect(status().isOk());
        ArgumentCaptor<ContractType> captor = ArgumentCaptor.forClass(ContractType.class);
        verify(service).insertContractType(captor.capture());
        assertThat(captor.getValue().getName()).isEqualTo("Indefinido");
    }

    @Test void update_usaElIdDeLaUrl() throws Exception {
        mockMvc.perform(put(URL + "/7").contentType(MediaType.APPLICATION_JSON).content(JSON)).andExpect(status().isOk());
        ArgumentCaptor<ContractType> captor = ArgumentCaptor.forClass(ContractType.class);
        verify(service).updateContractType(captor.capture());
        assertThat(captor.getValue().getId()).isEqualTo(7L);
    }

    @Test void delete_llamaAlDelete() throws Exception {
        mockMvc.perform(delete(URL + "/1")).andExpect(status().isOk());
        verify(service).deleteContractType(1L);
    }
}
