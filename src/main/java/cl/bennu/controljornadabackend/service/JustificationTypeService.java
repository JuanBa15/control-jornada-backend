package cl.bennu.controljornadabackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.bennu.controljornadabackend.mapper.JustificationTypeMapper;
import cl.bennu.controljornadabackend.model.JustificationType;

@Service 
public class JustificationTypeService {
    private @Autowired JustificationTypeMapper justificationTypeMapper;

    public List<JustificationType> getAll() {
        return justificationTypeMapper.getAll();
    }

    public void create(JustificationType justType) {
        justificationTypeMapper.insert(justType);
    }

    public void update(JustificationType justType) {
        justificationTypeMapper.update(justType);
    }

    public JustificationType getById(Long id) {
        return justificationTypeMapper.get(id);
    }

    public void delete(Long id) {
        justificationTypeMapper.delete(id);
    }
}
