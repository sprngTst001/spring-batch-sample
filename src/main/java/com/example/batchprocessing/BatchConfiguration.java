package com.example.batchprocessing;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.MyBatisCursorItemReader;
import org.mybatis.spring.batch.builder.MyBatisBatchItemWriterBuilder;
import org.mybatis.spring.batch.builder.MyBatisCursorItemReaderBuilder;

import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;


@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	
	@Autowired
	public DataSource dataSource;


	// Reader employee_csv_import
	@Bean
	public FlatFileItemReader<Employee> employeeCsvReader() {
		return new FlatFileItemReaderBuilder<Employee>()
			.name("EmployeeItemReader")
			.resource(new ClassPathResource("employeeWorkingTime.csv"))
			.delimited()
			.names(new String[]{"working_day", "employee_id", "working_hours"})
			.fieldSetMapper(new BeanWrapperFieldSetMapper<Employee>() {{
				setTargetType(Employee.class);
			}})
			.build();
	}
	
	// processor employee_csv_import
	@Bean
	public EmployeeItemValidator employeeCsvValidator() {
		return new EmployeeItemValidator();
	}

	// writer employee_csv_import
	@Bean
	public MyBatisBatchItemWriter<Employee> employeeCsvWriter(DataSource dataSource) {
		return new MyBatisBatchItemWriterBuilder<Employee>()
			.sqlSessionFactory(sqlSessionFactory)
			.statementId("com.example.batchprocessing.EmployeeMapper.savecsv")
			.build();
	}

	// reader employee_db_export
    @Bean
    public MyBatisCursorItemReader<Employee> employeeDbReader() {
        return new MyBatisCursorItemReaderBuilder<Employee>()
                .sqlSessionFactory(sqlSessionFactory)
                .queryId("com.example.batchprocessing.EmployeeMapper.select")
                .build();
    }

	// processor employee_db_export
	@Bean
	public EmployeeSalaryCalculation employeeSalaryCalculation() {
		return new EmployeeSalaryCalculation();
	}

	// writer employee_db_export
	@Bean
	public MyBatisBatchItemWriter<Employee> employeeSalaryWriter(DataSource dataSource) {
		return new MyBatisBatchItemWriterBuilder<Employee>()
			.sqlSessionFactory(sqlSessionFactory)
			.statementId("com.example.batchprocessing.EmployeeMapper.savesalary")
			.build();
	}

	@Bean
	public Job importUserJob(JobCompletionNotificationListener listener, Step step1, Step step2) {
		return jobBuilderFactory.get("importUserJob")
			.incrementer(new RunIdIncrementer())
			.listener(listener)
			.start(step1)
			.next(step2)
			.build();
	}


	// import from csv
	@Bean
	public Step step1(MyBatisBatchItemWriter<Employee> employeeCsvWriter) {
		return stepBuilderFactory.get("step1")
			.<Employee, Employee> chunk(10)
			.reader(employeeCsvReader())
			.processor(employeeCsvValidator())
			.writer(employeeCsvWriter)
			.build();
	}


	// export to db
	@Bean
	public Step step2(MyBatisBatchItemWriter<Employee> employeeSalaryWriter) {
		return stepBuilderFactory.get("step2")
			.<Employee, Employee> chunk(10)
			.reader(employeeDbReader())
			.processor(employeeSalaryCalculation())
			.writer(employeeSalaryWriter)
			.build();
	}
}
