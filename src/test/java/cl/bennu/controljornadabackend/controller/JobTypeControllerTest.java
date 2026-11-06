package cl.bennu.controljornadabackend.controller;

import cl.bennu.controljornadabackend.constants.Constants;
import cl.bennu.controljornadabackend.model.JobType;
import cl.bennu.controljornadabackend.model.Company;
import cl.bennu.controljornadabackend.model.Branch;
import cl.bennu.controljornadabackend.service.JobTypeService;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class JobTypeControllerTest {
    private static final String URL = Constants.BASE_API + "/job-type";
    private static final String JSON = "{\"name\":\"Analista\",\"company\":{\"id\":1},\"branch\":{\"id\":1}}";
    @Mock private JobTypeService service;
    @InjectMocks private JobTypeController controller;
    private MockMvc mockMvc;

    @BeforeEach void setUp() { mockMvc = MockMvcBuilders.standaloneSetup(controller).build(); }

    @Test void getAll_responde200() throws Exception {
        JobType value = jobType();
        when(service.getAllJobTypes()).thenReturn(List.of(value));
        mockMvc.perform(get(URL)).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].company.name").value("Bennu SA"))
                .andExpect(jsonPath("$[0].branch.name").value("Sucursal Centro"));
    }

    @Test void getById_responde200() throws Exception {
        JobType value = jobType();
        when(service.getJobTypeById(1L)).thenReturn(value);
        mockMvc.perform(get(URL + "/1")).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.company.name").value("Bennu SA"))
                .andExpect(jsonPath("$.branch.name").value("Sucursal Centro"));
    }

    private JobType jobType() {
        JobType value = new JobType();
        value.setId(1L);
        value.setName("Analista");
        Company company = new Company(); company.setId(1L); company.setName("Bennu SA");
        Branch branch = new Branch(); branch.setId(1L); branch.setName("Sucursal Centro");
        value.setCompany(company);
        value.setBranch(branch);
        return value;
    }

    @Test void create_llamaAlInsert() throws Exception {
        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(JSON)).andExpect(status().isOk());
        ArgumentCaptor<JobType> captor = ArgumentCaptor.forClass(JobType.class);
        verify(service).insertJobType(captor.capture());
        assertThat(captor.getValue().getCompany().getId()).isEqualTo(1L);
    }

    @Test void update_usaElIdDeLaUrl() throws Exception {
        mockMvc.perform(put(URL + "/4").contentType(MediaType.APPLICATION_JSON).content(JSON)).andExpect(status().isOk());
        ArgumentCaptor<JobType> captor = ArgumentCaptor.forClass(JobType.class);
        verify(service).updateJobType(captor.capture());
        assertThat(captor.getValue().getId()).isEqualTo(4L);
    }

    @Test void delete_llamaAlDelete() throws Exception {
        mockMvc.perform(delete(URL + "/1")).andExpect(status().isOk());
        verify(service).deleteJobType(1L);
    }
}
