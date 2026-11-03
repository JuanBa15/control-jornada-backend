package cl.bennu.controljornadabackend.model;

import cl.bennu.commons.domain.base.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class JobType extends BaseDomain implements Serializable {

    private String name;
    private Long companyId;
    private Long branchId;
    private String companyName;
    private String branchName;

}
