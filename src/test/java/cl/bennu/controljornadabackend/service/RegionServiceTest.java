package cl.bennu.controljornadabackend.service;

import cl.bennu.controljornadabackend.mapper.RegionMapper;
import cl.bennu.controljornadabackend.model.Region;
import cl.bennu.controljornadabackend.model.Country;
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
class RegionServiceTest {

    @Mock
    private RegionMapper regionMapper;

    @InjectMocks
    private RegionService regionService;

    private Region region() {
        Region region = new Region();
        region.setId(1L);
        region.setCountry(Country.valueOf(1L));
        region.setName("RM");
        return region;
    }

    @Test
    void getAllRegions_devuelveLoDelMapper() {
        when(regionMapper.getAll()).thenReturn(List.of(region()));

        List<Region> result = regionService.getAllRegions();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("RM");
    }

    @Test
    void getRegionById_existente_laDevuelve() {
        Region region = region();
        when(regionMapper.get(1L)).thenReturn(region);

        assertThat(regionService.getRegionById(1L)).isSameAs(region);
    }

    @Test
    void getRegionById_inexistente_devuelveNull() {
        when(regionMapper.get(99L)).thenReturn(null);

        assertThat(regionService.getRegionById(99L)).isNull();
    }

    @Test
    void createRegion_sinId_llamaAlInsertDelMapper() {
        Region region = region();
        region.setId(null);

        regionService.createRegion(region);

        verify(regionMapper).insert(region);
        verify(regionMapper, never()).update(any());
    }

    @Test
    void updateRegion_existente_llamaAlUpdateDelMapper() {
        Region region = region();
        when(regionMapper.get(1L)).thenReturn(region);

        regionService.updateRegion(1L, region);

        verify(regionMapper).update(region);
        verify(regionMapper, never()).insert(any());
    }

    @Test
    void deleteRegion_llamaAlDeleteDelMapper() {
        regionService.deleteRegion(1L);

        verify(regionMapper).delete(1L);
    }
}
