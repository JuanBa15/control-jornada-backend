package cl.bennu.controljornadabackend.service;

import cl.bennu.controljornadabackend.mapper.EmployeeMapper;
import cl.bennu.controljornadabackend.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeService {
    private @Autowired EmployeeMapper employeeMapper;

    public List<Employee> getAll() {
        return employeeMapper.getAll();
    }

    @Transactional
    public void insert(Employee o) {
        employeeMapper.insert(o);
    }

    @Transactional
    public void update(Employee o) {
        employeeMapper.update(o);
    }


    public Employee getById(Long id) {
        return employeeMapper.get(id);
    }

    public void delete(Long id) {
        employeeMapper.delete(id);
    }
}
