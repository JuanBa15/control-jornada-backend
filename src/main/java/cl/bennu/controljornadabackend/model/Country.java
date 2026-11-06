package cl.bennu.controljornadabackend.model;

import cl.bennu.commons.domain.base.BaseDomain;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper=true)
@Data
@NoArgsConstructor
public class Country extends BaseDomain implements Serializable {
    private String name;
    private String nationality;
    private List<Region> regions;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static Country valueOf(Long id) {
        Country o = new Country();
        o.setId(id);
        return o;
    }
}
