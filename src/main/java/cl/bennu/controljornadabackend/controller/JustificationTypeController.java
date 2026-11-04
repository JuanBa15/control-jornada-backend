package cl.bennu.controljornadabackend.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import cl.bennu.controljornadabackend.constants.Constants;
import cl.bennu.controljornadabackend.controller.base.BaseController;
import cl.bennu.controljornadabackend.model.JustificationType;
import cl.bennu.controljornadabackend.service.JustificationTypeService;

@RestController
@RequestMapping(path = Constants.BASE_API, produces = MediaType.APPLICATION_JSON_VALUE)
public class JustificationTypeController extends BaseController {
    
    private static final String ENDPOINT = "/justification-type";

    private @Autowired JustificationTypeService justificationTypeService;

    @GetMapping(ENDPOINT)
    public ResponseEntity<?> getAll() {
        List<JustificationType> justTypes = justificationTypeService.getAll();
        return ok(justTypes);
    }

    @PostMapping(ENDPOINT)
    public ResponseEntity<?> create(@RequestBody JustificationType justTypes) {
        justificationTypeService.create(justTypes);
        return ok(justTypes); // viene con el ID generado (autoGenerateKeys = true)
    }

    @PutMapping(ENDPOINT)
    public ResponseEntity<?> update(@RequestBody JustificationType justTypes) {
        justificationTypeService.update(justTypes);
        return ok(justTypes);
    }

    @GetMapping(ENDPOINT + "/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        JustificationType justType = justificationTypeService.getById(id);
        if(justType == null) {
            return ResponseEntity.notFound().build();
        }

        return ok(justType);
    }

    @DeleteMapping(ENDPOINT + "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        justificationTypeService.delete(id);
        return ok();
    }
}
