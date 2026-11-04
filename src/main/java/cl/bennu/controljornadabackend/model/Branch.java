package cl.bennu.controljornadabackend.model;

import cl.bennu.commons.domain.base.BaseDomain;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode(callSuper=true)
@Data
@NoArgsConstructor
public class Branch extends BaseDomain implements Serializable {
    private Company company;//	, company_id INTEGER NOT NULL
    private Commune commune;//	, commune_id INTEGER NOT NULL
    private String name;//	, name VARCHAR(200) NOT NULL
    private String address;//    , address VARCHAR(500) NOT NULL
    private String phone;//	, phone VARCHAR(20) NULL
    private String alias;//    , alias VARCHAR(200) NULL
    private Boolean active;//    , active BOOLEAN  NULL
    
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static Branch valueOf(Long id) {
        Branch o = new Branch();
        o.setId(id);
        return o;
    }
}
