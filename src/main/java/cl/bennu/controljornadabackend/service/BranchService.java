package cl.bennu.controljornadabackend.service;

import cl.bennu.controljornadabackend.mapper.BranchMapper;
import cl.bennu.controljornadabackend.model.Branch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BranchService {

    private @Autowired BranchMapper branchMapper;

    public List<Branch> getAll() {
        return branchMapper.getAll();
    }

    @Transactional
    public void insert(Branch branch) {
        branchMapper.insert(branch);
    }

    @Transactional
    public void update(Branch branch) {
        branchMapper.update(branch);
    }

    public Branch getById(Long branchId) {
        return branchMapper.get(branchId);
    }

    public void delete(Long branchId) {
        branchMapper.delete(branchId);
    }
}
