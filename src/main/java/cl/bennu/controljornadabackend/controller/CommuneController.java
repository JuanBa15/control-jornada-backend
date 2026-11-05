package cl.bennu.controljornadabackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.bennu.controljornadabackend.constants.Constants;
import cl.bennu.controljornadabackend.controller.base.BaseController;
import cl.bennu.controljornadabackend.model.Commune;
import cl.bennu.controljornadabackend.service.CommuneService;

@RestController 
@RequestMapping(path = Constants.BASE_API, produces = MediaType.APPLICATION_JSON_VALUE)
public class CommuneController extends BaseController {
    private static final String ENDPOINT = "/commune";

    private @Autowired CommuneService communeService;

    @GetMapping(ENDPOINT)
    public ResponseEntity<?> getAll() {
        List<Commune> communes = communeService.getAll();
        return ok(communes);
    }

    @PostMapping(ENDPOINT)
    public ResponseEntity<?> create(@RequestBody Commune commune) {
        communeService.create(commune);
        return ok(commune);
    }

    @PutMapping(ENDPOINT)
    public ResponseEntity<?> update(@RequestBody Commune commune) {
        communeService.update(commune);
        return ok(commune);
    }

    @GetMapping(ENDPOINT + "/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Commune commune = communeService.getById(id);
        if(commune == null) {
            return ResponseEntity.notFound().build();
        }

        return ok(commune);
    }

    @DeleteMapping(ENDPOINT + "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        communeService.delete(id);
        return ok();
    }

}
