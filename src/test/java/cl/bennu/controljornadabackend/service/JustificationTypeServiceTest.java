package cl.bennu.controljornadabackend.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

import cl.bennu.controljornadabackend.mapper.JustificationTypeMapper;
import cl.bennu.controljornadabackend.model.JustificationType;

@ExtendWith(MockitoExtension.class)
public class JustificationTypeServiceTest {
    @Mock
    private JustificationTypeMapper justificationTypeMapper;

    @InjectMocks
    private JustificationTypeService justificationTypeService;

    private JustificationType justificationType() {
        JustificationType j = new JustificationType();
        j.setId(1L);
        j.setName("Descanso médico");
        return j;
    }

    @Test
    void getAllJustificationType_devuelveLoDelMapper() {
        when(justificationTypeMapper.getAll()).thenReturn(List.of(justificationType()));

        List<JustificationType> result = justificationTypeService.getAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Descanso médico");
    }

    @Test
    void getJustificationTypeById_existente_laDevuelve() {
        JustificationType j = justificationType();
        when(justificationTypeMapper.get(1L)).thenReturn(j);

        assertThat(justificationTypeService.getById(1L)).isSameAs(j);
    }

    @Test
    void getJustificationTypeById_inexistente_devuelveNull() {
        when(justificationTypeMapper.get(99L)).thenReturn(null);

        assertThat(justificationTypeService.getById(99L)).isNull();
    }

    @Test
    void create_llamaAlInsertDelMapper() {
        JustificationType j = justificationType();
        j.setId(null);

        justificationTypeService.create(j);

        verify(justificationTypeMapper).insert(j);
        verify(justificationTypeMapper, never()).update(any());
    }

    @Test
    void update_llamaAlUpdateDelMapper() {
        JustificationType j = justificationType();
        // => ID no nulo

        justificationTypeService.update(j);

        verify(justificationTypeMapper).update(j);
        verify(justificationTypeMapper, never()).insert(any());
    }

    @Test
    void deleteJustificationType_llamaAlDeleteDelMapper() {
        justificationTypeService.delete(1L);

        verify(justificationTypeMapper).delete(1L);
    }
}
