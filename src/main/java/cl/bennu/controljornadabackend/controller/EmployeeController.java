package cl.bennu.controljornadabackend.controller;

import cl.bennu.controljornadabackend.constants.Constants;
import cl.bennu.controljornadabackend.controller.base.BaseController;
import cl.bennu.controljornadabackend.model.Country;
import cl.bennu.controljornadabackend.model.Employee;
import cl.bennu.controljornadabackend.service.CountryService;
import cl.bennu.controljornadabackend.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = Constants.BASE_API, produces = MediaType.APPLICATION_JSON_VALUE)
public class EmployeeController extends BaseController {

    private static final String ENDPOINT = "/employee";

    private @Autowired EmployeeService employeeService;

    @GetMapping(ENDPOINT)
    public ResponseEntity<?> getAll() {
        List<Employee> list = employeeService.getAll();
        return ok(list);
    }

    @PostMapping(ENDPOINT)
    public ResponseEntity<?> insert(@RequestBody Employee employee) {
        employeeService.insert(employee);
        return ok(employee);
    }

    @PutMapping(ENDPOINT + "/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Employee employee) {
        employee.setId(id);
        employeeService.update(employee);
        return ok(employee);
    }

    @GetMapping(ENDPOINT + "/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Employee employee = employeeService.getById(id);
        if (employee == null) {
            return ResponseEntity.notFound().build();
        }
        return ok(employee);
    }

    @DeleteMapping(ENDPOINT + "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        employeeService.delete(id);
        return ok();
    }
}
