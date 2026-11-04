package cl.bennu.controljornadabackend.controller;

import cl.bennu.controljornadabackend.constants.Constants;
import cl.bennu.controljornadabackend.controller.base.BaseController;
import cl.bennu.controljornadabackend.service.MaritalStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import cl.bennu.controljornadabackend.model.MaritalStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = Constants.BASE_API, produces = MediaType.APPLICATION_JSON_VALUE)
public class MaritalStatusController extends BaseController {

    private static final String ENDPOINT = "/maritialStatus";

    private @Autowired MaritalStatusService maritialStatusService;

    @GetMapping(ENDPOINT)
    public ResponseEntity<?> getAll() {
        List<MaritalStatus> maritialStatus = maritialStatusService.getAllMaritalStatus();
        return ok(maritialStatus);
    }

    @PostMapping(ENDPOINT)
    public ResponseEntity<?> save(@RequestBody MaritalStatus maritialStatus) {
        try {
            maritialStatusService.save(maritialStatus);
            return ok(maritialStatus);
        } catch (DuplicateKeyException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of("message", "Ya existe un estado civil con ese nombre"));
        }
    }

    @GetMapping(ENDPOINT + "/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        MaritalStatus maritialStatus = maritialStatusService.getById(id);
        if (maritialStatus == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "El estado civil no existe"));
        }
        return ok(maritialStatus);
    }

    @DeleteMapping(ENDPOINT + "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        maritialStatusService.delete(id);
        return ok();
    }

}