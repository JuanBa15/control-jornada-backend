package cl.bennu.controljornadabackend.controller;

import cl.bennu.controljornadabackend.constants.Constants;
import cl.bennu.controljornadabackend.controller.base.BaseController;
import cl.bennu.controljornadabackend.model.Region;
import cl.bennu.controljornadabackend.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = Constants.BASE_API, produces = MediaType.APPLICATION_JSON_VALUE)
public class RegionController extends BaseController {

    private static final String ENDPOINT = "/region";

    private @Autowired RegionService regionService;

    @GetMapping(ENDPOINT)
    public ResponseEntity<List<Region>> getAll() {
        List<Region> regions = regionService.getAllRegions();
        return ResponseEntity.ok(regions);
    }

    @PostMapping(ENDPOINT)
    public ResponseEntity<Region> create(@RequestBody Region region) {
        regionService.createRegion(region);
        return ResponseEntity.ok(region);
    }

    @PutMapping(ENDPOINT + "/{id}")
    public ResponseEntity<Region> update(@PathVariable Long id, @RequestBody Region region) {
        regionService.updateRegion(id, region);
        return ResponseEntity.ok(region);
    }

   
    @GetMapping(ENDPOINT + "/{id}")
    public ResponseEntity<Region> getById(@PathVariable Long id) {
        Region region = regionService.getRegionById(id);
        if (region == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(region);
    }

    @DeleteMapping(ENDPOINT + "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        regionService.deleteRegion(id);
        return ResponseEntity.ok().build();
    }
}
