drop table if exists employeeWorkingTimeFromCsv;

create table employeeWorkingTimeFromCsv(
  working_day VARCHAR(8)
  , employee_id VARCHAR (6)
  , working_hours VARCHAR (2)
  , PRIMARY KEY (working_day, employee_id)
);

drop table if exists employeeSalary;

create table employeeSalary(
  working_day VARCHAR(8)
  , employee_id VARCHAR (6)
  , working_hours VARCHAR (2)
  , salary VARCHAR (6)
  , PRIMARY KEY (working_day, employee_id)
);