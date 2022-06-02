package com.epam.rd.autocode;

import com.epam.rd.autocode.domain.Employee;
import com.epam.rd.autocode.domain.FullName;
import com.epam.rd.autocode.domain.Position;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class SetMapperImplement implements SetMapper<Set<Employee>> {
    private static final String ID = "id";
    private static final String FIRST_NAME = "firstname";
    private static final String LAST_NAME = "lastname";
    private static final String MIDDLE_NAME = "middleName";
    private static final String POSITION = "position";
    private static final String HIREDATE = "hiredate";
    private static final String SALARY = "salary";
    private static final String MANAGER = "manager";

    @Override
    public Set<Employee> mapSet(ResultSet resultSet) {
        Set<Employee> employees = new HashSet<>();
        try {
            while (resultSet.next()) {
                Employee employee = getEmployee(resultSet, getManager(resultSet));
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    private Employee getEmployee(ResultSet resultSet, Employee manager) throws SQLException {
        return new Employee(
                new BigInteger(String.valueOf(resultSet.getInt(ID))),
                new FullName(
                        resultSet.getString(FIRST_NAME),
                        resultSet.getString(LAST_NAME),
                        resultSet.getString(MIDDLE_NAME)),
                Position.valueOf(resultSet.getString(POSITION)),
                resultSet.getDate(HIREDATE).toLocalDate(),
                resultSet.getBigDecimal(SALARY),
                manager
        );
    }

    private Employee getManager(ResultSet resultSet) throws SQLException {
        Employee manager = null;
        int managerId = resultSet.getInt(MANAGER);
        int currentRow = resultSet.getRow();
        resultSet.beforeFirst();
        while (resultSet.next()) {
            if (resultSet.getInt(ID) == managerId) {
                manager = getEmployee(resultSet, getManager(resultSet));
            }
        }
        resultSet.absolute(currentRow);
        return manager;
    }
}
