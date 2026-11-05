package cl.bennu.controljornadabackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.bennu.controljornadabackend.mapper.CommuneMapper;
import cl.bennu.controljornadabackend.model.Commune;

@Service 
public class CommuneService {
    private @Autowired CommuneMapper communeMapper;

    public List<Commune> getAll() {
        return communeMapper.getAll();
    }

    public void create(Commune commune) {
        communeMapper.insert(commune);
    }

    public void update(Commune commune) {
        communeMapper.update(commune);
    }

    public Commune getById(Long id) {
        return communeMapper.get(id);
    }

    public void delete(Long id) {
        communeMapper.delete(id);
    }
}
