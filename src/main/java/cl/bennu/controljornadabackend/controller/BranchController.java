package cl.bennu.controljornadabackend.controller;

import cl.bennu.controljornadabackend.constants.Constants;
import cl.bennu.controljornadabackend.controller.base.BaseController;
import cl.bennu.controljornadabackend.model.Branch;
import cl.bennu.controljornadabackend.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = Constants.BASE_API, produces = MediaType.APPLICATION_JSON_VALUE)
public class BranchController extends BaseController {

    private static final String ENDPOINT = "/branch";

    private @Autowired BranchService branchService;

    @GetMapping(ENDPOINT)
    public ResponseEntity<?> getAll() {
        List<Branch> branches = branchService.getAll();
        return ok(branches);
    }

    @PostMapping(ENDPOINT)
    public ResponseEntity<?> create(@RequestBody Branch branch) {
        branchService.insert(branch);
        return ok(branch);
    }

    @PutMapping(ENDPOINT + "/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Branch branch) {
        branch.setId(id);
        branchService.update(branch);
        return ok(branch);
    }

    @GetMapping(ENDPOINT + "/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Branch branch = branchService.getById(id);
        return ok(branch);
    }

    @DeleteMapping(ENDPOINT + "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        branchService.delete(id);
        return ok();
    }
}
