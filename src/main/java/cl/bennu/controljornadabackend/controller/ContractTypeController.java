package cl.bennu.controljornadabackend.controller;

import cl.bennu.controljornadabackend.constants.Constants;
import cl.bennu.controljornadabackend.controller.base.BaseController;
import cl.bennu.controljornadabackend.model.ContractType;
import cl.bennu.controljornadabackend.service.ContractTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = Constants.BASE_API, produces = MediaType.APPLICATION_JSON_VALUE)
public class ContractTypeController extends BaseController {

    private static final String ENDPOINT = "/contract-type";

    private @Autowired ContractTypeService contractTypeService;

    @GetMapping(ENDPOINT)
    public ResponseEntity<?> getAll() {
        List<ContractType> contractTypes = contractTypeService.getAllContractTypes();
        return ok(contractTypes);
    }

    @PostMapping(ENDPOINT)
    public ResponseEntity<?> create(@RequestBody ContractType contractType) {
        contractTypeService.insertContractType(contractType);
        return ok(contractType);
    }

    @PutMapping(ENDPOINT + "/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ContractType contractType) {
        contractType.setId(id);
        contractTypeService.updateContractType(contractType);
        return ok(contractType);
    }

    @GetMapping(ENDPOINT + "/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        ContractType contractType = contractTypeService.getContractTypeById(id);
        return ok(contractType);
    }

    @DeleteMapping(ENDPOINT + "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        contractTypeService.deleteContractType(id);
        return ok();
    }

}
