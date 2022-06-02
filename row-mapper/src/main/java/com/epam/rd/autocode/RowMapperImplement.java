package com.epam.rd.autocode;

import com.epam.rd.autocode.domain.Employee;
import com.epam.rd.autocode.domain.FullName;
import com.epam.rd.autocode.domain.Position;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RowMapperImplement implements RowMapper<Employee> {

    @Override
    public Employee mapRow(ResultSet resultSet) {
        Employee employee = null;
        try {
            employee = new Employee(
                    new BigInteger(String.valueOf(resultSet.getInt("id"))),
                    new FullName(
                            resultSet.getString("firstname"),
                            resultSet.getString("lastname"),
                            resultSet.getString("middleName")),
                    Position.valueOf(resultSet.getString("position")),
                    resultSet.getDate("hiredate").toLocalDate(),
                    resultSet.getBigDecimal("salary")
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }
}
