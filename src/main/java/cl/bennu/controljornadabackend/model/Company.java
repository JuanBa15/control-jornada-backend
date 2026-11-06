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
public class Company extends BaseDomain implements Serializable {
    @JsonIncludeProperties({"id", "name"})
    private Commune commune;            //    , commune_id INTEGER NOT NULL
    private String code;
    private String name;
    private String address;
    private String phone;
    private String alias;
    private String giro;
    private String email;
    private Boolean geolocation;
    private Boolean selfie;
    private Boolean lag;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static Company valueOf(Long id) {
        Company o = new Company();
        o.setId(id);
        return o;
    }
}
