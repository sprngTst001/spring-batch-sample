package com.example.batchprocessing;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface EmployeeMapper {

    public List<Employee> select();

    public void savecsv(Employee employee);

    public void savesalary(Employee employee);
}