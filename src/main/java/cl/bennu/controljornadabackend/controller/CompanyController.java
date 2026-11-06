package cl.bennu.controljornadabackend.controller;

import cl.bennu.controljornadabackend.constants.Constants;
import cl.bennu.controljornadabackend.controller.base.BaseController;
import cl.bennu.controljornadabackend.model.Company;
import cl.bennu.controljornadabackend.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = Constants.BASE_API, produces = MediaType.APPLICATION_JSON_VALUE)
public class CompanyController extends BaseController {

    private static final String ENDPOINT = "/company";

    private @Autowired CompanyService companyService;

    @GetMapping(ENDPOINT)
    public ResponseEntity<?> getAll() {
        List<Company> companies = companyService.getAll();
        return ok(companies);
    }

    @PostMapping(ENDPOINT)
    public ResponseEntity<?> create(@RequestBody Company company) {
        companyService.insert(company);
        return ok(company);
    }

    @PutMapping(ENDPOINT + "/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Company company) {
        if (companyService.getById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        company.setId(id);
        companyService.update(company);
        return ok(company);
    }

    @GetMapping(ENDPOINT + "/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Company company = companyService.getById(id);
        if (company == null) {
            return ResponseEntity.notFound().build();
        }
        return ok(company);
    }

    @DeleteMapping(ENDPOINT + "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (companyService.getById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        companyService.delete(id);
        return ok();
    }

}