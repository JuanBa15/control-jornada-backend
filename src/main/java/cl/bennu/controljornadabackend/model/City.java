package cl.bennu.controljornadabackend.model;

import cl.bennu.commons.domain.base.BaseDomain;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class City extends BaseDomain implements Serializable {

    private String name;
    @JsonIncludeProperties({"id", "name"})
    private Region region;              //    , region_id INTEGER NOT NULL

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static City valueOf(Long id) {
        City o = new City();
        o.setId(id);
        return o;
    }
}
