package cl.bennu.controljornadabackend.model;

import java.io.Serializable;

import cl.bennu.commons.domain.base.BaseDomain;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode (callSuper = true)
@Data
@NoArgsConstructor
public class Commune extends BaseDomain implements Serializable {
    // propiedades de entidad
    private Long cityId;
    private String name;
    private String siiCode; // código de comuna de SII (servicio de impuestos internos)
    private String tgrCode; // código de tesorería (Tesorería General de la República)
    
    // Propiedades de respuesta
    private City city;
    private Region region;
    private Country country;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static Commune valueOf(Long id) {
        Commune o = new Commune();
        o.setId(id);
        return o;
    }

}
