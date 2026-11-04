package cl.bennu.controljornadabackend.service;

import cl.bennu.controljornadabackend.mapper.CountryMapper;
import cl.bennu.controljornadabackend.model.Country;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class CountryService {

    private @Autowired CountryMapper countryMapper;

    public List<Country> getAll() {
        return countryMapper.getAll();
    }

//    @Transactional
//    public void save(Country country) {
//        if (null == country.getId()) {
//            countryMapper.insert(country);
//        } else {
//            countryMapper.update(country);
//        }
//    }

    @Transactional
    public void insert(Country country) {
        countryMapper.insert(country);
    }

    @Transactional
    public void update(Country country) {
        countryMapper.update(country);
    }


    public Country getById(Long id) {
        return countryMapper.get(id);
    }

    public void delete(Long id) {
        countryMapper.delete(id);
    }

}