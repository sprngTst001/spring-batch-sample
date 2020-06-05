package com.example.batchprocessing;

public class Employee {

    private String working_day;
    private String employee_id;
    private String working_hours;
    private String salary;

    public String getWorking_day() {
        return this.working_day;
    }

    public void setWorking_day(String working_day) {
        this.working_day = working_day;
    }

    public String getEmployee_id() {
        return this.employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    public String getWorking_hours() {
        return this.working_hours;
    }

    public void setWorking_hours(String working_hours) {
        this.working_hours = working_hours;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public Employee() {
    }

    public Employee(String working_day, String employee_id, String working_hours) {
        this.working_day = working_day;
        this.employee_id = employee_id;
        this.working_hours = working_hours;
    }
    
    public Employee(String working_day, String employee_id, String working_hours, String salary) {
        this.working_day = working_day;
        this.employee_id = employee_id;
        this.working_hours = working_hours;
        this.salary = salary;
    }
    @Override
    public String toString() {
        return "working_day: " + working_day + ", employee_id: " + employee_id + ", working_hours: " + working_hours;
    }

}
