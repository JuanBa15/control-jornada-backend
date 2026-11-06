package cl.bennu.controljornadabackend.service;

import cl.bennu.controljornadabackend.mapper.BranchMapper;
import cl.bennu.controljornadabackend.model.Branch;
import cl.bennu.controljornadabackend.model.Company;
import cl.bennu.controljornadabackend.model.Commune;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BranchServiceTest {

    @Mock
    private BranchMapper branchMapper;

    @InjectMocks
    private BranchService branchService;

    private Branch branch() {
        Branch branch = new Branch();
        branch.setId(1L);
        Company company = new Company();
        company.setId(1L);
        company.setName("Bennu SA");
        Commune commune = new Commune();
        commune.setId(1L);
        commune.setName("Santiago");
        branch.setCompany(company);
        branch.setCommune(commune);
        branch.setName("Sucursal Centro");
        branch.setAddress("Av. Siempre Viva 123");
        branch.setPhone("+56912345678");
        branch.setAlias("Centro");
        branch.setActive(true);
        return branch;
    }

    @Test
    void getAll_devuelveLoDelMapper() {
        when(branchMapper.getAll()).thenReturn(List.of(branch()));

        List<Branch> result = branchService.getAll();

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getName()).isEqualTo("Sucursal Centro");
        assertThat(result.getFirst().getCompany().getName()).isEqualTo("Bennu SA");
        assertThat(result.getFirst().getCommune().getName()).isEqualTo("Santiago");
    }

    @Test
    void getById_existente_laDevuelve() {
        Branch branch = branch();
        when(branchMapper.get(1L)).thenReturn(branch);

        assertThat(branchService.getById(1L)).isSameAs(branch);
    }

    @Test
    void insert_llamaAlInsertDelMapper() {
        Branch branch = branch();
        branch.setId(null);

        branchService.insert(branch);

        verify(branchMapper).insert(branch);
        verify(branchMapper, never()).update(any());
    }

    @Test
    void update_llamaAlUpdateDelMapper() {
        Branch branch = branch();

        branchService.update(branch);

        verify(branchMapper).update(branch);
        verify(branchMapper, never()).insert(any());
    }

    @Test
    void delete_llamaAlDeleteDelMapper() {
        branchService.delete(1L);

        verify(branchMapper).delete(1L);
    }
}
