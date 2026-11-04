package cl.bennu.controljornadabackend.model;

import cl.bennu.commons.domain.base.BaseDomain;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class ContractType extends BaseDomain implements Serializable {

    private String name;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static ContractType valueOf(Long id) {
        ContractType o = new ContractType();
        o.setId(id);
        return o;
    }
}
