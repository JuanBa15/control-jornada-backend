package cl.bennu.controljornadabackend.service;

import cl.bennu.controljornadabackend.mapper.CountryMapper;
import cl.bennu.controljornadabackend.model.Country;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CountryServiceTest {

    @Mock
    private CountryMapper countryMapper;

    @InjectMocks
    private CountryService countryService;

    private Long idOk = 1L;
    private Long idError = 99L;

    private Country country(){
        Country country = new Country();
        country.setId(1L);
        country.setName("Chile");
        country.setNationality("Chilena");
        return country;
    }

    @Test
    void getCountries_invocaMapper(){
        when(countryMapper.getAll()).thenReturn(List.of(country()));

        List<Country> result = countryService.getAll();

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getName()).isEqualTo("Chile");
        assertThat(result.get(0).getNationality()).isEqualTo("Chilena");
    }

    @Test
    void getById_ok_invokeMapper(){
        Country countryTest = country();
        when(countryMapper.get(1L)).thenReturn(countryTest);
        Country result = countryService.getById(idOk);
        assertThat(result).isSameAs(countryTest);
    }

    @Test
    void getById_fails_invokeMapper(){
        when(countryMapper.get(idError)).thenReturn(null);
        assertThat(countryService.getById(idError)).isNull();
    }

    @Test
    void insert_invokeMapper(){
        Country countryTest = country();
        countryTest.setId(null);
        countryService.insert(countryTest);

        verify(countryMapper).insert(countryTest);
        verify(countryMapper,never()).update(any());
    }

    @Test
    void update_invokeMapper(){
        Country countryTest = country();
        countryService.update(countryTest);

        verify(countryMapper).update(countryTest);
        verify(countryMapper,never()).insert(any());
    }

    @Test
    void delete_invokeMapper(){
        countryService.delete(idOk);
        verify(countryMapper).delete(idOk);
    }
}
