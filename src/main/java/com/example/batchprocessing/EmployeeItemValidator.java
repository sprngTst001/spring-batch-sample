package com.example.batchprocessing;

import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;

public class EmployeeItemValidator implements ItemProcessor<Employee, Employee> {

	private static final Logger log = LoggerFactory.getLogger(EmployeeItemValidator.class);

	@Override
	public Employee process(final Employee employee) throws Exception {
		
		// // working_day　日付文字数チェック (YYYY/M/Dは許容しない)
		// if (employee.getWorking_day().length() != 10) {
		// 	throw new Exception();
		// }
		// // working_day　日付整合性チェック
		// SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		// dateFormat.parse(employee.getWorking_day());

		// // employee_id　数値・桁数チェック
		// if (!employee.getEmployee_id().matches("[0-9]{6}")){
		// 	throw new Exception();
		// }

		// // working_hours　整数値チェック
		// if (!employee.getWorking_hours().matches("[0-9]|[1-9][0-9]")) {

		// }

		final String working_day = employee.getWorking_day().replace("/","");
		final String employee_id = employee.getEmployee_id();
		final String working_hours = employee.getWorking_hours();

		final Employee transformedEmployee = new Employee(working_day, employee_id, working_hours);

		log.info("validate (" + transformedEmployee.toString() + ")");

		return transformedEmployee;
	}

}
