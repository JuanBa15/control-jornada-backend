package cl.bennu.controljornadabackend.controller;

import cl.bennu.controljornadabackend.constants.Constants;
import cl.bennu.controljornadabackend.controller.base.BaseController;
import cl.bennu.controljornadabackend.model.JobType;
import cl.bennu.controljornadabackend.service.JobTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = Constants.BASE_API, produces = MediaType.APPLICATION_JSON_VALUE)
public class JobTypeController extends BaseController {

    private static final String ENDPOINT = "/job-type";

    private @Autowired JobTypeService jobTypeService;

    @GetMapping(ENDPOINT)
    public ResponseEntity<?> getAll() {
        List<JobType> jobTypes = jobTypeService.getAllJobTypes();
        return ok(jobTypes);
    }

    @PostMapping(ENDPOINT)
    public ResponseEntity<?> create(@RequestBody JobType jobType) {
        jobTypeService.insertJobType(jobType);
        return ok(jobType);
    }

    @PutMapping(ENDPOINT + "/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody JobType jobType) {
        jobType.setId(id);
        jobTypeService.updateJobType(jobType);
        return ok(jobType);
    }

    @GetMapping(ENDPOINT + "/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        JobType jobType = jobTypeService.getJobTypeById(id);
        return ok(jobType);
    }

    @DeleteMapping(ENDPOINT + "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        jobTypeService.deleteJobType(id);
        return ok();
    }

}
