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
public class Gender extends BaseDomain implements Serializable {
    private String name;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static Gender valueOf(Long id) {
        Gender o = new Gender();
        o.setId(id);
        return o;
    }
}
