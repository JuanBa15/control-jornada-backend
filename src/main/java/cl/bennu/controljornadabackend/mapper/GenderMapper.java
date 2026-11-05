package cl.bennu.controljornadabackend.mapper;

import org.apache.ibatis.annotations.Mapper;

import cl.bennu.commons.mapper.base.BaseMapper;
import cl.bennu.controljornadabackend.model.Gender;

@Mapper 
public interface GenderMapper extends BaseMapper<Gender> { /* impl. en XML */ }
