package cl.bennu.controljornadabackend.service;

import cl.bennu.controljornadabackend.mapper.CompanyMapper;
import cl.bennu.controljornadabackend.model.Commune;
import cl.bennu.controljornadabackend.model.Company;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CompanyServiceTest {

    @Mock
    private CompanyMapper companyMapper;

    @InjectMocks
    private CompanyService companyService;

    private Company company() {
        Company c = new Company();
        c.setId(1L);
        c.setCommune(new Commune());
        c.getCommune().setId(1L);
        c.setCode("83686157-4");
        c.setName("Bennu SA");
        c.setAddress("Av. Siempre Viva 123");
        c.setPhone("+56912345678");
        c.setAlias("Bennu");
        c.setGiro("Software");
        c.setEmail("contact@bennu.cl");
        c.setGeolocation(true);
        c.setSelfie(false);
        c.setLag(true);
        return c;
    }

    @Test
    void getAll_devuelveLoDelMapper() {
        when(companyMapper.getAll()).thenReturn(List.of(company()));

        List<Company> result = companyService.getAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Bennu SA");
    }

    @Test
    void getAll_sinDatos_devuelveListaVacia() {
        when(companyMapper.getAll()).thenReturn(List.of());

        assertThat(companyService.getAll()).isEmpty();
    }

    @Test
    void getById_existente_laDevuelve() {
        Company c = company();
        when(companyMapper.get(1L)).thenReturn(c);

        assertThat(companyService.getById(1L)).isSameAs(c);
    }

    @Test
    void getById_inexistente_devuelveNull() {
        when(companyMapper.get(99L)).thenReturn(null);

        assertThat(companyService.getById(99L)).isNull();
    }

    @Test
    void insert_sinId_llamaAlInsertDelMapper() {
        Company c = company();
        c.setId(null); // el id lo genera la BD

        companyService.insert(c);

        verify(companyMapper).insert(c);
        verify(companyMapper, never()).update(any());
    }

    @Test
    void update_llamaAlUpdateDelMapper() {
        Company c = company();

        companyService.update(c);

        verify(companyMapper).update(c);
        verify(companyMapper, never()).insert(any());
    }

    @Test
    void delete_llamaAlDeleteDelMapper() {
        companyService.delete(1L);

        verify(companyMapper).delete(1L);
    }
}
