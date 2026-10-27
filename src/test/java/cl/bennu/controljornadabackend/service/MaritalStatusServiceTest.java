package cl.bennu.controljornadabackend.service;

import cl.bennu.controljornadabackend.mapper.MaritalStatusMapper;
import cl.bennu.controljornadabackend.model.MaritalStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MaritalStatusServiceTest {

    @Mock
    private MaritalStatusMapper maritalStatusMapper;

    @InjectMocks
    private MaritalStatusService maritalStatusService;

    private MaritalStatus maritalStatus() {
        MaritalStatus m = new MaritalStatus();
        m.setId(1L);
        m.setName("Casado");
        return m;
    }

    @Test
    void getAllMaritalStatus_devuelveLoDelMapper() {
        when(maritalStatusMapper.getAll()).thenReturn(List.of(maritalStatus()));

        List<MaritalStatus> result = maritalStatusService.getAllMaritalStatus();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Casado");
    }

    @Test
    void getMaritalStatusById_existente_laDevuelve() {
        MaritalStatus m = maritalStatus();
        when(maritalStatusMapper.get(1L)).thenReturn(m);

        assertThat(maritalStatusService.getById(1L)).isSameAs(m);
    }

    @Test
    void getMaritalStatusById_inexistente_devuelveNull() {
        when(maritalStatusMapper.get(99L)).thenReturn(null);

        assertThat(maritalStatusService.getById(99L)).isNull();
    }

    @Test
    void saveMaritalStatus_llamaAlInsertDelMapper() {
        MaritalStatus m = maritalStatus();
        m.setId(null);

        maritalStatusService.save(m);

        verify(maritalStatusMapper).insert(m);
        verify(maritalStatusMapper, never()).update(any());
    }

    @Test
    void deleteMaritalStatus_llamaAlDeleteDelMapper() {
        maritalStatusService.delete(1L);

        verify(maritalStatusMapper).delete(1L);
    }
}
