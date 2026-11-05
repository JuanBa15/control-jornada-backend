package cl.bennu.controljornadabackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.bennu.controljornadabackend.constants.Constants;
import cl.bennu.controljornadabackend.controller.base.BaseController;
import cl.bennu.controljornadabackend.model.Gender;
import cl.bennu.controljornadabackend.service.GenderService;

@RestController
@RequestMapping (path = Constants.BASE_API, produces = MediaType.APPLICATION_JSON_VALUE)
public class GenderController extends BaseController {
    private static final String ENDPOINT = "/gender";

    private @Autowired GenderService genderService;

    @GetMapping(ENDPOINT)
    public ResponseEntity<?> getAll() {
        List<Gender> genders = genderService.getAll();
        return ok(genders);
    }

    @GetMapping(ENDPOINT + "/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Gender gender = genderService.getById(id);
        if(gender == null) {
            return ResponseEntity.notFound().build();
        }

        return ok(gender);
    }
}
