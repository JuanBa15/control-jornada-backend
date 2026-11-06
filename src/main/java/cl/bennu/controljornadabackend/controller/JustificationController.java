package cl.bennu.controljornadabackend.controller;

import cl.bennu.controljornadabackend.constants.Constants;
import cl.bennu.controljornadabackend.controller.base.BaseController;
import cl.bennu.controljornadabackend.model.Justification;
import cl.bennu.controljornadabackend.service.JustificationService;
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

import java.util.List;

@RestController
@RequestMapping(path = Constants.BASE_API, produces = MediaType.APPLICATION_JSON_VALUE)
public class JustificationController extends BaseController {

    private static final String ENDPOINT = "/justification";

    private @Autowired JustificationService justificationService;

    @GetMapping(ENDPOINT)
    public ResponseEntity<List<Justification>> getAll() {
        List<Justification> justifications = justificationService.getAllJustifications();
        return ResponseEntity.ok(justifications);
    }

    @PostMapping(ENDPOINT)
    public ResponseEntity<Justification> create(@RequestBody Justification justification) {
        justificationService.createJustification(justification);
        return ResponseEntity.ok(justification);
    }

    @PutMapping(ENDPOINT + "/{id}")
    public ResponseEntity<Justification> update(@PathVariable Long id, @RequestBody Justification justification) {
        justificationService.updateJustification(id, justification);
        return ResponseEntity.ok(justification);
    }


    @GetMapping(ENDPOINT + "/{id}")
    public ResponseEntity<Justification> getById(@PathVariable Long id) {
        Justification justification = justificationService.getJustificationById(id);
        if (justification == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(justification);
    }

    @DeleteMapping(ENDPOINT + "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        justificationService.deleteJustification(id);
        return ResponseEntity.ok().build();
    }
}
