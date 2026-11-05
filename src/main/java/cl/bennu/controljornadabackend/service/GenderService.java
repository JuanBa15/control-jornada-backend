package cl.bennu.controljornadabackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.bennu.controljornadabackend.mapper.GenderMapper;
import cl.bennu.controljornadabackend.model.Gender;

@Service 
public class GenderService {
    private @Autowired GenderMapper genderMapper;

    public List<Gender> getAll() {
        return genderMapper.getAll();
    }

    public Gender getById(Long id) {
        return genderMapper.get(id);
    }
}
