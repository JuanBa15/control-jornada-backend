package cl.bennu.controljornadabackend.mapper;

import cl.bennu.commons.mapper.base.BaseMapper;
import cl.bennu.controljornadabackend.model.Branch;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BranchMapper extends BaseMapper<Branch> {
}
