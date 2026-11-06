package cl.bennu.controljornadabackend.mapper;

import cl.bennu.commons.mapper.base.BaseMapper;
import cl.bennu.controljornadabackend.model.ContractType;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ContractTypeMapper extends BaseMapper<ContractType> {

    Long nextId();

}
