package cl.bennu.controljornadabackend.model;

import cl.bennu.commons.domain.base.BaseDomain;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper=true)
@Data
@NoArgsConstructor
public class Employee extends BaseDomain implements Serializable {

    private String uuid;                 //    , UUID VARCHAR(50)  NULL
    private String code;                //	, code VARCHAR(20) NOT NULL
    private String name;                //    , name VARCHAR(200) NOT NULL
    private String lastName;            //    , last_name VARCHAR(200) NOT NULL
    private String motherLastName;      //    , mother_last_name VARCHAR(200) NULL
    private LocalDate birthDate;
    private String phone;
    private String email;

    private String address;             //    , address VARCHAR(500) NOT NULL
    private LocalDate contractDate;     //	, contract_date DATE NULL
    private LocalDate contractEndDate;  //	, contract_end_date DATE NULL

    private Branch branch;              //    , branch_id INTEGER NOT NULL
    private JobScheduler jobScheduler;  //	, job_scheduler_id INTEGER NOT NULL
    private Commune commune;            //    , commune_id INTEGER NOT NULL
    private Country country;            //    , nationality_id INTEGER NOT NULL
    private Gender gender;              //	, gender_id INTEGER NOT NULL
    private MaritalStatus maritalStatus;//	, marital_status_id INTEGER NOT NULL
    private ContractType contractType;  //	, contract_type_id INTEGER NULL
    private JobType jobType;            //    , job_type_id INTEGER NOT NULL

    private Boolean active;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static Employee valueOf(Long id) {
        Employee o = new Employee();
        o.setId(id);
        return o;
    }
}
