package cl.bennu.controljornadabackend.service;

import cl.bennu.controljornadabackend.mapper.ContractTypeMapper;
import cl.bennu.controljornadabackend.model.ContractType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class ContractTypeServiceTest {
    @Mock private ContractTypeMapper mapper;
    @InjectMocks private ContractTypeService service;

    @Test void insert_asignaElSiguienteIdYllamaAlMapper() {
        ContractType value = new ContractType(); value.setName("Indefinido");
        when(mapper.nextId()).thenReturn(3L);
        service.insertContractType(value);
        assertThat(value.getId()).isEqualTo(3L);
        verify(mapper).insert(value);
    }

    @Test void getAll_devuelveLoDelMapper() {
        when(mapper.getAll()).thenReturn(List.of(new ContractType()));
        assertThat(service.getAllContractTypes()).hasSize(1);
    }

    @Test void update_llamaAlUpdateDelMapper() {
        ContractType value = new ContractType(); value.setId(1L);
        service.updateContractType(value);
        verify(mapper).update(value);
        verify(mapper, never()).insert(any());
    }

    @Test void getById_devuelveLoDelMapper() {
        ContractType value = new ContractType();
        when(mapper.get(1L)).thenReturn(value);
        assertThat(service.getContractTypeById(1L)).isSameAs(value);
    }

    @Test void delete_llamaAlDeleteDelMapper() {
        service.deleteContractType(1L);
        verify(mapper).delete(1L);
    }
}
