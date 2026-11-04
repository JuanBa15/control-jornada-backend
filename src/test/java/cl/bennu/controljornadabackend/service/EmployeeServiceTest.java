package cl.bennu.controljornadabackend.service;

import cl.bennu.controljornadabackend.mapper.EmployeeMapper;
import cl.bennu.controljornadabackend.model.Branch;
import cl.bennu.controljornadabackend.model.Commune;
import cl.bennu.controljornadabackend.model.ContractType;
import cl.bennu.controljornadabackend.model.Country;
import cl.bennu.controljornadabackend.model.Employee;
import cl.bennu.controljornadabackend.model.Gender;
import cl.bennu.controljornadabackend.model.JobScheduler;
import cl.bennu.controljornadabackend.model.JobType;
import cl.bennu.controljornadabackend.model.MaritalStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeMapper employeeMapper;

    @InjectMocks
    private EmployeeService employeeService;

    private Long idOk = 1L;
    private Long idError = 99L;

    private Employee employee() {
        Employee e = new Employee();
        e.setId(1L);
        e.setUuid("a1b2c3d4-0000-0000-0000-000000000001");
        e.setCode("EMP001");
        e.setName("Juan");
        e.setLastName("Pérez");
        e.setMotherLastName("González");
        e.setBirthDate(LocalDate.of(1990, 5, 15));
        e.setPhone("+56912345678");
        e.setEmail("juan.perez@correo.cl");
        e.setAddress("Av. Siempre Viva 123");
        e.setContractDate(LocalDate.of(2024, 1, 1));
        e.setContractEndDate(null);
        e.setActive(true);

        e.setBranch(Branch.valueOf(idOk));
        e.setJobScheduler(JobScheduler.valueOf(idOk));
        e.setCommune(Commune.valueOf(idOk));
        e.setCountry(Country.valueOf(idOk));
        e.setGender(Gender.valueOf(idOk));
        e.setMaritalStatus(MaritalStatus.valueOf(idOk));
        e.setContractType(ContractType.valueOf(idOk));
        e.setJobType(JobType.valueOf(idOk));

        return e;
    }

    @Test
    void getEmployees_invocaMapper(){
        when(employeeMapper.getAll()).thenReturn(List.of(employee()));

        List<Employee> result = employeeService.getAll();

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getCode()).isEqualTo("EMP001");
        assertThat(result.get(0).getName()).isEqualTo("Juan");
        assertThat(result.get(0).getLastName()).isEqualTo("Pérez");
        assertThat(result.get(0).getActive()).isTrue();
        verify(employeeMapper).getAll();
    }

    @Test
    void getEmployees_listaVacia_invocaMapper(){
        when(employeeMapper.getAll()).thenReturn(List.of());

        List<Employee> result = employeeService.getAll();

        assertThat(result).isEmpty();
    }

    @Test
    void getById_ok_invokeMapper(){
        Employee employeeTest = employee();
        when(employeeMapper.get(idOk)).thenReturn(employeeTest);

        Employee result = employeeService.getById(idOk);

        assertThat(result).isSameAs(employeeTest);
        assertThat(result.getBranch().getId()).isEqualTo(1L);
        assertThat(result.getCountry().getId()).isEqualTo(1L);
    }

    @Test
    void getById_fails_invokeMapper(){
        when(employeeMapper.get(idError)).thenReturn(null);

        assertThat(employeeService.getById(idError)).isNull();
    }

    @Test
    void insert_invokeMapper(){
        Employee employeeTest = employee();
        employeeTest.setId(null);

        employeeService.insert(employeeTest);

        verify(employeeMapper).insert(employeeTest);
        verify(employeeMapper, never()).update(any());
    }

    @Test
    void insert_sinTipoContrato_invokeMapper(){
        Employee employeeTest = employee();
        employeeTest.setId(null);
        employeeTest.setContractType(null);

        employeeService.insert(employeeTest);
        verify(employeeMapper).insert(employeeTest);
    }

    @Test
    void update_invokeMapper(){
        Employee employeeTest = employee();

        employeeService.update(employeeTest);

        verify(employeeMapper).update(employeeTest);
        verify(employeeMapper, never()).insert(any());
    }

    @Test
    void delete_invokeMapper(){
        employeeService.delete(idOk);
        verify(employeeMapper).delete(idOk);
    }
}