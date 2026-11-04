package cl.bennu.controljornadabackend.controller;

import cl.bennu.controljornadabackend.constants.Constants;
import cl.bennu.controljornadabackend.model.Branch;
import cl.bennu.controljornadabackend.model.Commune;
import cl.bennu.controljornadabackend.model.ContractType;
import cl.bennu.controljornadabackend.model.Country;
import cl.bennu.controljornadabackend.model.Employee;
import cl.bennu.controljornadabackend.model.Gender;
import cl.bennu.controljornadabackend.model.JobScheduler;
import cl.bennu.controljornadabackend.model.JobType;
import cl.bennu.controljornadabackend.model.MaritalStatus;
import cl.bennu.controljornadabackend.service.EmployeeService;
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

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
        import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
        import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {

    private static final String ENDPOINT = Constants.BASE_API + "/employee";

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    private MockMvc mockMvc;

    private final Long idOk = 1L;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
    }

    private Employee employee() {
        Employee employee = new Employee();
        employee.setId(idOk);
        employee.setUuid("a1b2c3d4-0000-0000-0000-000000000001");
        employee.setCode("EMP001");
        employee.setName("Juan");
        employee.setLastName("Pérez");
        employee.setMotherLastName("González");
        employee.setBirthDate(LocalDate.of(1990, 5, 15));
        employee.setPhone("+56912345678");
        employee.setEmail("juan.perez@correo.cl");
        employee.setAddress("Av. Siempre Viva 123");
        employee.setContractDate(LocalDate.of(2024, 1, 1));
        employee.setActive(true);

        employee.setBranch(Branch.valueOf(idOk));
        employee.setJobScheduler(JobScheduler.valueOf(idOk));

        employee.setCommune(Commune.valueOf(idOk));
        employee.setCountry(Country.valueOf(idOk));
        employee.setGender(Gender.valueOf(idOk));
        employee.setMaritalStatus(MaritalStatus.valueOf(idOk));
        employee.setContractType(ContractType.valueOf(idOk));
        employee.setJobType(JobType.valueOf(idOk));
        return employee;
    }

    // Body base para POST; las relaciones van como id gracias a valueOf(Long)
    private static final String BODY_INSERT = """
            {
              "uuid": "a1b2c3d4-0000-0000-0000-000000000001",
              "code": "EMP001",
              "name": "Juan",
              "lastName": "Pérez",
              "motherLastName": "González",
              "birthDate": "1990-05-15",
              "phone": "+56912345678",
              "email": "juan.perez@correo.cl",
              "address": "Av. Siempre Viva 123",
              "contractDate": "2024-01-01",
              "active": true,
              "branch": 1,
              "jobScheduler": 1,
              "commune": 1,
              "country": 1,
              "gender": 1,
              "maritalStatus": 1,
              "contractType": 1,
              "jobType": 1
            }
            """;

    // ---------- GET /employee ----------

    @Test
    void getAll_ok_retornaLista_return200() throws Exception {
        when(employeeService.getAll()).thenReturn(List.of(employee()));

        mockMvc.perform(get(ENDPOINT))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].code").value("EMP001"))
                .andExpect(jsonPath("$[0].name").value("Juan"))
                .andExpect(jsonPath("$[0].lastName").value("Pérez"))
                .andExpect(jsonPath("$[0].active").value(true))
                .andExpect(jsonPath("$[0].branch.id").value(1));

        verify(employeeService).getAll();
    }

    @Test
    void getAll_vacio_retornaListaVacia_return200() throws Exception {
        when(employeeService.getAll()).thenReturn(List.of());

        mockMvc.perform(get(ENDPOINT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    // ---------- GET /employee/{id} ----------

    @Test
    void getById_ok_retorna200() throws Exception {
        when(employeeService.getById(idOk)).thenReturn(employee());

        mockMvc.perform(get(ENDPOINT + "/" + idOk))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.code").value("EMP001"))
                .andExpect(jsonPath("$.name").value("Juan"))
                .andExpect(jsonPath("$.lastName").value("Pérez"))
                .andExpect(jsonPath("$.email").value("juan.perez@correo.cl"))
                .andExpect(jsonPath("$.country.id").value(1))
                .andExpect(jsonPath("$.jobType.id").value(1));

        verify(employeeService).getById(idOk);
    }

    @Test
    void getById_noExiste_retorna404() throws Exception {
        Long idError = 99L;
        when(employeeService.getById(idError)).thenReturn(null);

        mockMvc.perform(get(ENDPOINT + "/" + idError))
                .andExpect(status().isNotFound());

        verify(employeeService).getById(idError);
    }

    @Test
    void getById_idInvalido_retorna400() throws Exception {
        mockMvc.perform(get(ENDPOINT + "/" + "abc"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(employeeService);
    }

    // ---------- POST /employee ----------

    @Test
    void insert_sinId_return200() throws Exception {
        mockMvc.perform(post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(BODY_INSERT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("EMP001"))
                .andExpect(jsonPath("$.name").value("Juan"))
                .andExpect(jsonPath("$.lastName").value("Pérez"));

        ArgumentCaptor<Employee> captor = ArgumentCaptor.forClass(Employee.class);
        verify(employeeService).insert(captor.capture());
        verify(employeeService, never()).update(any());

        Employee enviado = captor.getValue();
        assertThat(enviado.getId()).isNull();
        assertThat(enviado.getCode()).isEqualTo("EMP001");
        assertThat(enviado.getName()).isEqualTo("Juan");
        assertThat(enviado.getLastName()).isEqualTo("Pérez");
        assertThat(enviado.getBirthDate()).isEqualTo(LocalDate.of(1990, 5, 15));
        assertThat(enviado.getActive()).isTrue();
        assertThat(enviado.getBranch().getId()).isEqualTo(1L);
        assertThat(enviado.getCountry().getId()).isEqualTo(1L);
        assertThat(enviado.getJobType().getId()).isEqualTo(1L);
    }

    @Test
    void insert_sinTipoContrato_return200() throws Exception {
        String body = """
                {
                  "code": "EMP002",
                  "name": "Ana",
                  "lastName": "Soto",
                  "address": "Calle Falsa 456",
                  "active": true,
                  "branch": 1,
                  "jobScheduler": 1,
                  "commune": 1,
                  "country": 1,
                  "gender": 2,
                  "maritalStatus": 1,
                  "jobType": 1
                }
                """;

        mockMvc.perform(post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());

        ArgumentCaptor<Employee> captor = ArgumentCaptor.forClass(Employee.class);
        verify(employeeService).insert(captor.capture());
        assertThat(captor.getValue().getContractType()).isNull();
        assertThat(captor.getValue().getGender().getId()).isEqualTo(2L);
    }

    @Test
    void update_return200() throws Exception {
        String body = """
                {
                  "code": "EMP001",
                  "name": "Juan",
                  "lastName": "Pérez",
                  "address": "Av. Siempre Viva 123",
                  "active": false,
                  "branch": 1,
                  "jobScheduler": 1,
                  "commune": 1,
                  "country": 1,
                  "gender": 1,
                  "maritalStatus": 1,
                  "jobType": 1
                }
                """;

        mockMvc.perform(put(ENDPOINT + "/" + idOk)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        ArgumentCaptor<Employee> captor = ArgumentCaptor.forClass(Employee.class);
        verify(employeeService).update(captor.capture());
        verify(employeeService, never()).insert(any());
        assertThat(captor.getValue().getId()).isEqualTo(1L);
        assertThat(captor.getValue().getActive()).isFalse();
    }

    @Test
    void save_sinBody_retorna400() throws Exception {
        mockMvc.perform(post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(employeeService, never()).insert(any());
        verify(employeeService, never()).update(any());
    }

    // ---------- DELETE /employee/{id} ----------

    @Test
    void delete_ok_return200() throws Exception {
        mockMvc.perform(delete(ENDPOINT + "/" + idOk))
                .andExpect(status().isOk());

        verify(employeeService).delete(idOk);
    }

    @Test
    void delete_idInvalido_retorna400() throws Exception {
        mockMvc.perform(delete(ENDPOINT + "/" + "abc"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(employeeService);
    }
}