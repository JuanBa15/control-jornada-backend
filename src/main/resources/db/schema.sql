-- COUNTRY
-- @formatter:off
DROP TABLE IF EXISTS country;

CREATE TABLE country (
    id SERIAL PRIMARY KEY
    , name VARCHAR(200) NOT NULL
    , nationality VARCHAR(200) NOT NULL

    , UNIQUE(id)
    , UNIQUE(name)
);

INSERT INTO country (name, nationality) VALUES
    ('Chile', 'Chilena');


-- REGISTER
DROP TABLE IF EXISTS register;

CREATE TABLE register (
                          id SERIAL NOT NULL
    , employee_id INTEGER NOT NULL
    , start_of_the_day TIME NOT NULL
    , entry TIME NULL
    , exit TIME NULL
    , break_time TIME NULL
    , back_to_work TIME NULL
    , day DATE NOT NULL
    , justification_id INTEGER NULL

    , PRIMARY KEY (id)
    , UNIQUE(id)
    , FOREIGN KEY (employee_id) REFERENCES employee(id)
    , FOREIGN KEY (justification_id) REFERENCES justification(id)
);

INSERT INTO register (
    id, employee_id, start_of_the_day, entry, exit,
    break_time, back_to_work, day, justification_id
) VALUES
    (1, 1, '08:00:00', '08:00:00', NULL,
     NULL, NULL, CURRENT_DATE, 1);