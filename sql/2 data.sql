BEGIN;

-- GENDER
INSERT INTO gender (id, name) VALUES
  (1, 'Masculino'),
  (2, 'Femenino');

-- MARITAL STATUS
INSERT INTO marital_status (id, name) VALUES
  (1, 'Casado'),
  (2, 'Viudo'),
  (3, 'Divorciado'),
  (4, 'Soltero');

-- CONTRACT TYPE
INSERT INTO contract_type (id, name) VALUES
  (1, 'Fijo'),
  (2, 'Indefinido'),
  (3, 'Obra');

-- COUNTRY
INSERT INTO country (id, name, nationality, active) VALUES
  (1, 'Chile', 'Chilena', true);

-- REGION
INSERT INTO region (id, country_id, name) VALUES
  (1, 1, 'RM');

-- CITY
INSERT INTO city (id, region_id, name) VALUES
  (1, 1, 'Santiago');

-- COMMUNE
INSERT INTO commune (id, city_id, name, sii_code, tgr_code) VALUES
  (1, 1, 'El Bosque', NULL, NULL);

-- JUSTIFICATION TYPE
INSERT INTO justification_type (id, name) VALUES
  (1, 'Licencia Medica'),
  (2, 'Licencia Personal'),
  (3, 'Dia Administrativo'),
  (4, 'Vacaciones'),
  (5, 'Cumpleaños'),
  (6, 'Otros');

-- COMPANY
INSERT INTO company (id, commune_id, code, name, address, phone, alias, giro, email, geolocation, selfie, lag) VALUES
  (1, 1, '83686157-4', 'Bennu SA', 'Av. Siempre Viva 123', '+56912345678', 'Bennu', 'Software', 'contact@bennu.cl', TRUE, FALSE, TRUE);

-- COMPANY USER
INSERT INTO staff (id, company_id, sub_uuid, email, admin_id, phone, is_owner) VALUES
  (1, 1, '550e8400-e29b-41d4-a716-446655440000', 'contact_company_user@bennu.cl', NULL, '+56912345678', true);

-- BRANCH
INSERT INTO branch (id, company_id, name, address, phone, alias, active, commune_id) VALUES
  (1, 1, 'Headquarters', 'Av. Siempre Viva 123', '+56912345678', 'HQ', TRUE, 1);

-- JOB TYPE
INSERT INTO job_type (id, name, company_id, branch_id) VALUES
  (1, 'Developer', 1, 1);

-- JOB SCHEDULER
INSERT INTO job_scheduler (
    id, name,
    monday, monday_from, monday_to,
    tuesday, tuesday_from, tuesday_to,
    wednesday, wednesday_from, wednesday_to,
    thursday, thursday_from, thursday_to,
    friday, friday_from, friday_to,
    saturday, saturday_from, saturday_to,
    sunday, sunday_from, sunday_to
) VALUES (
    1, 'Standard Day',
    TRUE, '09:00:00', '18:00:00',
    TRUE, '09:00:00', '18:00:00',
    TRUE, '09:00:00', '18:00:00',
    TRUE, '09:00:00', '18:00:00',
    TRUE, '09:00:00', '18:00:00',
    FALSE, NULL, NULL,
    FALSE, NULL, NULL
);

-- EMPLOYEE
INSERT INTO employee (
    id, uuid, branch_id, job_scheduler_id, commune_id,
    nationality_id, gender_id, marital_status_id, contract_type_id,
    job_type_id, code, name, last_name, mother_last_name,
    birth_date, phone, email, address,
    contract_date, contract_end_date, active
) VALUES (
    1, '550e8400-e29b-41d4-a716-446655440000',
    1, 1, 1,
    1, 1, 1, 1,
    1, 'E001', 'John', 'Doe', 'Smith',
    '1990-01-01', '+56987654321', 'john.doe@example.com', 'Calle Falsa 123',
    '2020-01-01', NULL, TRUE
);

-- JUSTIFICATION (ejemplo)
INSERT INTO justification (id, comments, attachment, justification_type_id) VALUES
  (1, 'Medical leave', NULL, 1);

-- REGISTER (ejemplo)
INSERT INTO register (
    id, employee_id, start_of_the_day, entry, exit,
    break_time, back_to_work, day, justification_id
) VALUES
  (1, 1, '08:00:00', '08:00:00', NULL,
   NULL, NULL, CURRENT_DATE, 1);

COMMIT;