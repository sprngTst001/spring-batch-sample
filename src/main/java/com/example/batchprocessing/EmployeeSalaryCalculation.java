package com.example.batchprocessing;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;

public class EmployeeSalaryCalculation implements ItemProcessor<Employee, Employee> {

	private static final Logger log = LoggerFactory.getLogger(EmployeeSalaryCalculation.class);

	@Override
	public Employee process(final Employee employee) throws Exception {
		
		int normal_salary = 1000;
		int overtime_salary = 1500;
		int holiday_salary = 1500;

		final String working_day = employee.getWorking_day().replace("/","");
		final String employee_id = employee.getEmployee_id();
		final String working_hours = employee.getWorking_hours();

		final String salary = String.valueOf(Integer.parseInt(working_hours) * normal_salary);

		final Employee transformedEmployee = new Employee(working_day, employee_id, working_hours, salary);

		log.info("SalaryCalculation (" + transformedEmployee.toString() + ")");

		return transformedEmployee;
	}

}
