package cl.bennu.controljornadabackend.model;

import cl.bennu.commons.domain.base.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class Branch extends BaseDomain implements Serializable {

    private Long companyId;
    private Long communeId;
    private String name;
    private String address;
    private String phone;
    private String alias;
    private Boolean active;
}
