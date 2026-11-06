package cl.bennu.controljornadabackend.service;

import cl.bennu.controljornadabackend.mapper.RegionMapper;
import cl.bennu.controljornadabackend.model.Country;
import cl.bennu.controljornadabackend.model.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class RegionService {

    private @Autowired RegionMapper regionMapper;

    public List<Region> getAllRegions() {
        return regionMapper.getAll();
    }

    @Transactional
    public void createRegion(Region region) {
        if (region.getId() != null && regionMapper.get(region.getId()) != null) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Ya existe una región con ese id");
        }

        if (regionMapper.buscarPorNombre(region.getName()) != null) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Ya existe una región con ese nombre");
        }

        regionMapper.insert(region);
    }

    @Transactional
    public void updateRegion(Long id, Region region) {
        if (regionMapper.get(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe una región con ese id");
        }

        region.setId(id);
        Region regionConEseNombre = regionMapper.buscarPorNombre(region.getName());
        if (regionConEseNombre != null && !regionConEseNombre.getId().equals(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe una región con ese nombre");
        }

        regionMapper.update(region);
    }

    public Region getRegionById(Long id) {
        return regionMapper.get(id);
    }

    public void deleteRegion(Long id) {
        regionMapper.delete(id);
    }
}
