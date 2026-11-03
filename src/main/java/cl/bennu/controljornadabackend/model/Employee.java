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
public class Employee extends BaseDomain implements Serializable {

    private String uud;//    , UUID VARCHAR(50)  NULL
    private String code;//	, code VARCHAR(20) NOT NULL
    private String name;//    , name VARCHAR(200) NOT NULL
    private String last_name;//    , last_name VARCHAR(200) NOT NULL
    private String mother_last_name;//    , mother_last_name VARCHAR(200) NULL

    //private Branch branch;//    , branch_id INTEGER NOT NULL
    //private JobScheduler jobscheduler;//	, job_scheduler_id INTEGER NOT NULL
    //private Commune commune;//    , commune_id INTEGER NOT NULL
    private Country country;//    , nationality_id INTEGER NOT NULL
    //private Gender gender;//	, gender_id INTEGER NOT NULL
    private MaritalStatus maritalStatus;//	, marital_status_id INTEGER NOT NULL
    private ContractType contractType;//	, contract_type_id INTEGER NULL
    private JobType jobType;//    , job_type_id INTEGER NOT NULL

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static Employee valueOf(Long id) {
        Employee o = new Employee();
        o.setId(id);
        return o;
    }
}
