package cl.bennu.controljornadabackend.service;

import cl.bennu.controljornadabackend.mapper.JustificationMapper;
import cl.bennu.controljornadabackend.model.Justification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class JustificationService {

    private @Autowired JustificationMapper justificationMapper;

    public List<Justification> getAllJustifications() {
        return justificationMapper.getAll();
    }

    @Transactional
    public void createJustification(Justification justification) {
        justificationMapper.insert(justification);
    }

    @Transactional
    public void updateJustification(Long id, Justification justification) {
        if (justificationMapper.get(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe una justificación con ese id");
        }

        justification.setId(id);
        justificationMapper.update(justification);
    }

    public Justification getJustificationById(Long id) {
        return justificationMapper.get(id);
    }

    public void deleteJustification(Long id) {
        justificationMapper.delete(id);
    }
}
