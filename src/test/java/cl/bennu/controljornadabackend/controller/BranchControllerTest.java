package cl.bennu.controljornadabackend.controller;

import cl.bennu.controljornadabackend.constants.Constants;
import cl.bennu.controljornadabackend.model.Branch;
import cl.bennu.controljornadabackend.service.BranchService;
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
class BranchControllerTest {

    private static final String URL = Constants.BASE_API + "/branch";

    private static final String BRANCH_JSON = """
            {
                "companyId": 1,
                "communeId": 1,
                "name": "Sucursal Centro",
                "address": "Av. Siempre Viva 123",
                "phone": "+56912345678",
                "alias": "Centro",
                "active": true
            }
            """;

    @Mock
    private BranchService branchService;

    @InjectMocks
    private BranchController branchController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(branchController).build();
    }

    private Branch branch() {
        Branch branch = new Branch();
        branch.setId(1L);
        branch.setCompanyId(1L);
        branch.setCommuneId(1L);
        branch.setCompanyName("Bennu SA");
        branch.setCommuneName("Santiago");
        branch.setName("Sucursal Centro");
        branch.setAddress("Av. Siempre Viva 123");
        branch.setPhone("+56912345678");
        branch.setAlias("Centro");
        branch.setActive(true);
        return branch;
    }

    @Test
    void getAll_responde200ConLaLista() throws Exception {
        when(branchService.getAll()).thenReturn(List.of(branch()));

        mockMvc.perform(get(URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Sucursal Centro"))
                .andExpect(jsonPath("$[0].companyName").value("Bennu SA"))
                .andExpect(jsonPath("$[0].communeName").value("Santiago"));
    }

    @Test
    void getById_responde200ConLaBranch() throws Exception {
        when(branchService.getById(1L)).thenReturn(branch());

        mockMvc.perform(get(URL + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Sucursal Centro"))
                .andExpect(jsonPath("$.companyName").value("Bennu SA"))
                .andExpect(jsonPath("$.communeName").value("Santiago"));
    }

    @Test
    void insert_convierteElJsonYLlamaAlServicio() throws Exception {
        mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(BRANCH_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Sucursal Centro"));

        ArgumentCaptor<Branch> captor = ArgumentCaptor.forClass(Branch.class);
        verify(branchService).insert(captor.capture());
        assertThat(captor.getValue().getCompanyId()).isEqualTo(1L);
        assertThat(captor.getValue().getActive()).isTrue();
    }

    @Test
    void update_usaElIdDeLaUrl() throws Exception {
        mockMvc.perform(put(URL + "/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(BRANCH_JSON))
                .andExpect(status().isOk());

        ArgumentCaptor<Branch> captor = ArgumentCaptor.forClass(Branch.class);
        verify(branchService).update(captor.capture());
        assertThat(captor.getValue().getId()).isEqualTo(5L);
    }

    @Test
    void delete_responde200YLlamaAlDelete() throws Exception {
        mockMvc.perform(delete(URL + "/1"))
                .andExpect(status().isOk());

        verify(branchService).delete(1L);
    }
}
