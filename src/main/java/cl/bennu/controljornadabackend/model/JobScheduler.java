package cl.bennu.controljornadabackend.model;

import cl.bennu.commons.domain.base.BaseDomain;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Time;

@EqualsAndHashCode(callSuper=true)
@Data
@NoArgsConstructor
public class JobScheduler extends BaseDomain implements Serializable {

    private String name;//	, name VARCHAR(500) NOT NULL

    private Boolean monday;//	, monday BOOLEAN NOT NULL
    private Time monday_from;//	, monday_from time NULL
    private Time monday_to;//	, monday_to time NULL

    private Boolean tuesday;//	, monday BOOLEAN NOT NULL
    private Time tuesday_from;//	, monday_from time NULL
    private Time tuesday_to;//	, monday_to time NULL

    private Boolean wednesday;//	, monday BOOLEAN NOT NULL
    private Time    wednesday_from;//	, monday_from time NULL
    private Time    wednesday_to;//	, monday_to time NULL

    private Boolean thursday;//	, monday BOOLEAN NOT NULL
    private Time    thursday_from;//	, monday_from time NULL
    private Time    thursday_to;//	, monday_to time NULL

    private Boolean friday;//	, monday BOOLEAN NOT NULL
    private Time    friday_from;//	, monday_from time NULL
    private Time    friday_to;//	, monday_to time NULL

    private Boolean saturday;//	, monday BOOLEAN NOT NULL
    private Time    saturday_from;//	, monday_from time NULL
    private Time    saturday_to;//	, monday_to time NULL

    private Boolean sunday;//	, monday BOOLEAN NOT NULL
    private Time    sunday_from;//	, monday_from time NULL
    private Time    sunday_to;//	, monday_to time NULL

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static JobScheduler valueOf(Long id) {
        JobScheduler o = new JobScheduler();
        o.setId(id);
        return o;
    }
}
