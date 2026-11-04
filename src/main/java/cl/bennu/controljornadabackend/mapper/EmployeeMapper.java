package cl.bennu.controljornadabackend.mapper;

import cl.bennu.commons.mapper.base.BaseMapper;
import cl.bennu.controljornadabackend.model.Country;
import cl.bennu.controljornadabackend.model.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
