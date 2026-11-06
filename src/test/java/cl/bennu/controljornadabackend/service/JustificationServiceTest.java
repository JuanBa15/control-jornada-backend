package cl.bennu.controljornadabackend.service;

import cl.bennu.controljornadabackend.mapper.JustificationMapper;
import cl.bennu.controljornadabackend.model.Justification;
import cl.bennu.controljornadabackend.model.JustificationType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JustificationServiceTest {

    @Mock
    private JustificationMapper justificationMapper;

    @InjectMocks
    private JustificationService justificationService;

    private Justification justification() {
        Justification justification = new Justification();
        justification.setId(1L);
        justification.setComments("Licencia médica");
        JustificationType type = new JustificationType();
        type.setId(1L);
        justification.setJustificationType(type);
        return justification;
    }

    @Test
    void getAllJustifications_devuelveLoDelMapper() {
        when(justificationMapper.getAll()).thenReturn(List.of(justification()));

        List<Justification> result = justificationService.getAllJustifications();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getComments()).isEqualTo("Licencia médica");
    }

    @Test
    void getJustificationById_existente_laDevuelve() {
        Justification justification = justification();
        when(justificationMapper.get(1L)).thenReturn(justification);

        assertThat(justificationService.getJustificationById(1L)).isSameAs(justification);
    }

    @Test
    void getJustificationById_inexistente_devuelveNull() {
        when(justificationMapper.get(99L)).thenReturn(null);

        assertThat(justificationService.getJustificationById(99L)).isNull();
    }

    @Test
    void saveJustification_sinId_llamaAlInsertDelMapper() {
        Justification justification = justification();
        justification.setId(null);

        justificationService.createJustification(justification);

        verify(justificationMapper).insert(justification);
        verify(justificationMapper, never()).update(any());
    }

    @Test
    void deleteJustification_llamaAlDeleteDelMapper() {
        justificationService.deleteJustification(1L);

        verify(justificationMapper).delete(1L);
    }
}
