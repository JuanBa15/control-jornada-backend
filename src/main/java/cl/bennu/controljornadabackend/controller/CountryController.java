package cl.bennu.controljornadabackend.controller;

import cl.bennu.controljornadabackend.constants.Constants;
import cl.bennu.controljornadabackend.controller.base.BaseController;
import cl.bennu.controljornadabackend.model.Country;
import cl.bennu.controljornadabackend.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = Constants.BASE_API, produces = MediaType.APPLICATION_JSON_VALUE)
public class CountryController extends BaseController {

    private static final String ENDPOINT = "/country";

    private @Autowired CountryService countryService;

    @GetMapping(ENDPOINT)
    public ResponseEntity<?> getAll() {
        List<Country> list = countryService.getAll();
        return ok(list);
    }

//    @PostMapping(ENDPOINT)
//    public ResponseEntity<?> save(@RequestBody Country country) {
//        countryService.save(country);
//        return ok(country);
//    }

    @PostMapping(ENDPOINT)
    public ResponseEntity<?> insert(@RequestBody Country country) {
        countryService.insert(country);
        return ok(country);
    }

    @PutMapping(ENDPOINT + "/id")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Country country) {
        country.setId(id);
        countryService.update(country);
        return ok(country);
    }

    @GetMapping(ENDPOINT + "/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Country country = countryService.getById(id);
        if (country == null) {
            return ResponseEntity.notFound().build();
        }
        return ok(country);
    }

    @DeleteMapping(ENDPOINT + "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        countryService.delete(id);
        return ok();
    }
}


