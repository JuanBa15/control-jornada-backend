package cl.bennu.controljornadabackend.service;

import cl.bennu.controljornadabackend.mapper.ContractTypeMapper;
import cl.bennu.controljornadabackend.model.ContractType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ContractTypeService {

    private @Autowired ContractTypeMapper contractTypeMapper;

    public List<ContractType> getAllContractTypes() {
        return contractTypeMapper.getAll();
    }

    @Transactional
    public void insertContractType(ContractType contractType) {
        contractType.setId(contractTypeMapper.nextId());
        contractTypeMapper.insert(contractType);
    }

    @Transactional
    public void updateContractType(ContractType contractType) {
        contractTypeMapper.update(contractType);
    }

    public ContractType getContractTypeById(Long contractTypeId) {
        return contractTypeMapper.get(contractTypeId);
    }

    public void deleteContractType(Long contractTypeId) {
        contractTypeMapper.delete(contractTypeId);
    }

}
