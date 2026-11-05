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

import cl.bennu.controljornadabackend.mapper.CommuneMapper;
import cl.bennu.controljornadabackend.model.City;
import cl.bennu.controljornadabackend.model.Commune;
import cl.bennu.controljornadabackend.model.Country;
import cl.bennu.controljornadabackend.model.Region;

@ExtendWith(MockitoExtension.class)
public class CommuneServiceTest {
    @Mock
    private CommuneMapper communeMapper;

    @InjectMocks
    private CommuneService communeService;

    private Commune commune() {
        Commune c = new Commune();
        c.setId(1L);
        c.setName("Santiago");
        c.setSiiCode("13101");
        c.setTgrCode("070");

        City city = new City();
        city.setId(1L);
        city.setName("Santiago");
        c.setCity(city);

        Region region = new Region();
        region.setId(1L);
        region.setName("RM");
        c.setRegion(region);

        Country country = new Country();
        country.setId(1L);
        country.setName("Chile");
        c.setCountry(country);

        return c;
    }

    @Test
    void getAllCommune_devuelveLoDelMapper() {
        when(communeMapper.getAll()).thenReturn(List.of(commune()));

        List<Commune> result = communeService.getAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Santiago");
        assertThat(result.get(0).getCity().getName()).isEqualTo("Santiago");
        assertThat(result.get(0).getRegion().getName()).isEqualTo("RM");
        assertThat(result.get(0).getCountry().getName()).isEqualTo("Chile");
    }

    @Test
    void getCommuneById_existente_laDevuelve() {
        Commune c = commune();
        when(communeMapper.get(1L)).thenReturn(c);

        assertThat(communeService.getById(1L)).isSameAs(c);
    }

    @Test
    void getCommuneById_inexistente_devuelveNull() {
        when(communeMapper.get(99L)).thenReturn(null);

        assertThat(communeService.getById(99L)).isNull();
    }

    @Test
    void create_llamaAlInsertDelMapper() {
        Commune c = commune();
        c.setId(null);

        communeService.create(c);

        verify(communeMapper).insert(c);
        verify(communeMapper, never()).update(any());
    }

    @Test
    void update_llamaAlUpdateDelMapper() {
        Commune c = commune();
        // => ID no nulo

        communeService.update(c);

        verify(communeMapper).update(c);
        verify(communeMapper, never()).insert(any());
    }

    @Test
    void deleteCommune_llamaAlDeleteDelMapper() {
        communeService.delete(1L);

        verify(communeMapper).delete(1L);
    }
}
