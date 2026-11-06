package cl.bennu.controljornadabackend.service;

import cl.bennu.controljornadabackend.mapper.CityMapper;
import cl.bennu.controljornadabackend.model.City;
import cl.bennu.controljornadabackend.model.Region;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CityServiceTest {

    @Mock
    private CityMapper cityMapper;

    @InjectMocks
    private CityService cityService;

    private City city() {
        City c = new City();
        c.setId(1L);
        c.setRegion(Region.valueOf(1L));
        c.setName("Santiago");
        return c;
    }

    @Test
    void getAll_devuelveLoDelMapper() {
        when(cityMapper.getAll()).thenReturn(List.of(city()));

        List<City> result = cityService.getAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Santiago");
    }

    @Test
    void getById_existente_laDevuelve() {
        City c = city();
        when(cityMapper.get(1L)).thenReturn(c);

        assertThat(cityService.getById(1L)).isSameAs(c);
        assertThat(cityService.getById(1L)).isEqualTo(city());
    }

    @Test
    void getById_inexistente_devuelveNull() {
        when(cityMapper.get(99L)).thenReturn(null);

        assertThat(cityService.getById(99L)).isNull();
    }

    @Test
    void insert_llamaAlInsertDelMapper() {
        City c = city();

        cityService.insert(c);

        verify(cityMapper).insert(c);
        verify(cityMapper, never()).update(any());
    }

    @Test
    void update_llamaAlUpdateDelMapper() {
        City c = city();

        cityService.update(c);

        verify(cityMapper).update(c);
        verify(cityMapper, never()).insert(any());
    }

    @Test
    void delete_llamaAlDeleteDelMapper() {
        cityService.delete(1L);

        verify(cityMapper).delete(1L);
    }
}