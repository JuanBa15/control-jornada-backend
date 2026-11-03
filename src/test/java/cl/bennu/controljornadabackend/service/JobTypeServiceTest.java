package cl.bennu.controljornadabackend.service;

import cl.bennu.controljornadabackend.mapper.JobTypeMapper;
import cl.bennu.controljornadabackend.model.JobType;
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
        when(mapper.getAll()).thenReturn(List.of(new JobType()));
        assertThat(service.getAllJobTypes()).hasSize(1);
    }

    @Test void getById_devuelveLoDelMapper() {
        JobType value = new JobType();
        when(mapper.get(1L)).thenReturn(value);
        assertThat(service.getJobTypeById(1L)).isSameAs(value);
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
