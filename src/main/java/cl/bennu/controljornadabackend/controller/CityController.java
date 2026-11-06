package cl.bennu.controljornadabackend.controller;

import cl.bennu.controljornadabackend.constants.Constants;
import cl.bennu.controljornadabackend.controller.base.BaseController;
import cl.bennu.controljornadabackend.model.City;
import cl.bennu.controljornadabackend.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = Constants.BASE_API, produces = MediaType.APPLICATION_JSON_VALUE)
public class CityController extends BaseController {

    private static final String ENDPOINT = "/city";

    private @Autowired CityService cityService;

    @GetMapping(ENDPOINT)
    public ResponseEntity<?> getAll() {
        List<City> cities = cityService.getAll();
        return ok(cities);
    }

    @PostMapping(ENDPOINT)
    public ResponseEntity<?> create(@RequestBody City city) {
        cityService.insert(city);
        return ok(city);
    }

    @PutMapping(ENDPOINT + "/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody City city) {
        if (cityService.getById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        city.setId(id);
        cityService.update(city);
        return ok(city);
    }

    @GetMapping(ENDPOINT + "/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        City city = cityService.getById(id);
        if (city == null) {
            return ResponseEntity.notFound().build();
        }
        return ok(city);
    }

    @DeleteMapping(ENDPOINT + "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (cityService.getById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        cityService.delete(id);
        return ok();
    }

}