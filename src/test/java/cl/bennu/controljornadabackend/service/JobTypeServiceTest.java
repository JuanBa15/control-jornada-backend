package cl.bennu.controljornadabackend.service;

import cl.bennu.controljornadabackend.mapper.JobTypeMapper;
import cl.bennu.controljornadabackend.model.JobType;
import cl.bennu.controljornadabackend.model.Company;
import cl.bennu.controljornadabackend.model.Branch;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class JobTypeServiceTest {
    @Mock private JobTypeMapper mapper;
    @InjectMocks private JobTypeService service;

    @Test void getAll_devuelveLoDelMapper() {
        JobType value = jobType();
        when(mapper.getAll()).thenReturn(List.of(value));
        assertThat(service.getAllJobTypes()).hasSize(1);
        assertThat(service.getAllJobTypes().getFirst().getCompany().getName()).isEqualTo("Bennu SA");
        assertThat(service.getAllJobTypes().getFirst().getBranch().getName()).isEqualTo("Sucursal Centro");
    }

    @Test void getById_devuelveLoDelMapper() {
        JobType value = jobType();
        when(mapper.get(1L)).thenReturn(value);
        assertThat(service.getJobTypeById(1L)).isSameAs(value);
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

    @Test void insert_llamaAlInsertDelMapper() {
        JobType value = new JobType(); value.setName("Analista");
        service.insertJobType(value);
        verify(mapper).insert(value);
    }

    @Test void update_llamaAlUpdateDelMapper() {
        JobType value = new JobType(); value.setId(1L);
        service.updateJobType(value);
        verify(mapper).update(value);
        verify(mapper, never()).insert(any());
    }

    @Test void delete_llamaAlDeleteDelMapper() {
        service.deleteJobType(1L);
        verify(mapper).delete(1L);
    }
}
