package cl.bennu.controljornadabackend.service;

import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

import cl.bennu.controljornadabackend.mapper.GenderMapper;
import cl.bennu.controljornadabackend.model.Gender;

@ExtendWith(MockitoExtension.class)
public class GenderServiceTest {
    @Mock
    private GenderMapper genderMapper;

    @InjectMocks
    private GenderService genderService;

    private Gender gender() {
        Gender g = new Gender();
        g.setId(1L);
        g.setName("Masculino");
        return g;
    }

    @Test
    void getAllGender_devuelveLoDelMapper() {
        when(genderMapper.getAll()).thenReturn(List.of(gender()));

        List<Gender> result = genderService.getAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Masculino");
    }

    @Test
    void getGenderById_existente_laDevuelve() {
        Gender g = gender();
        when(genderMapper.get(1L)).thenReturn(g);

        assertThat(genderService.getById(1L)).isSameAs(g);
    }

    @Test
    void getGenderById_inexistente_devuelveNull() {
        when(genderMapper.get(99L)).thenReturn(null);

        assertThat(genderService.getById(99L)).isNull();
    }
}

