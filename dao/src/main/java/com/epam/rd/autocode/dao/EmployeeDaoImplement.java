package com.epam.rd.autocode.dao;

import com.epam.rd.autocode.ConnectionSource;
import com.epam.rd.autocode.domain.Department;
import com.epam.rd.autocode.domain.Employee;
import com.epam.rd.autocode.domain.FullName;
import com.epam.rd.autocode.domain.Position;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeDaoImplement implements EmployeeDao {
    private static final int PARAMETER_ONE = 1;
    private static final String FROM_EMPLOYEE_WHERE_ID = "SELECT * FROM employee WHERE id = ";
    private static final String FROM_EMPLOYEE = "SELECT * FROM employee";
    private static final String ID = "id";
    private static final String FIRST_NAME = "firstname";
    private static final String LAST_NAME = "lastname";
    private static final String MIDDLE_NAME = "middleName";
    private static final String POSITION = "position";
    private static final String HIREDATE = "hiredate";
    private static final String SALARY = "salary";
    private static final String MANAGER = "manager";
    private static final String DEPARTMENT = "department";
    private static final String BUILD_INSERT = "INSERT INTO employee (id, firstname, lastname, middlename, " +
            "position, hiredate, salary, manager, department) " +
            "values ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s');";
    private static final String BUILD_UPDATE = "UPDATE employee SET " +
            "firstname = '%s', lastname = '%s', middlename = '%s', position = '%s', hiredate = '%s', " +
            "salary = '%s', manager = '%s', department = '%s', ' WHERE id = '%s'";
    private static final String BUILD_DELETE = "DELETE FROM employee WHERE id = '%s'";
    private static final String GET_BY_DEPARTMENT = "SELECT * FROM employee WHERE department = ?";
    private static final String GET_BY_MANAGER = "SELECT * FROM employee WHERE manager = ?";

    @Override
    public Optional<Employee> getById(BigInteger id) {
        return Optional.ofNullable(getEmployee(FROM_EMPLOYEE_WHERE_ID + id));
    }

    @Override
    public List<Employee> getAll() {
        List<Employee> employees = new ArrayList<>();
        try {
            Connection connection = ConnectionSource.instance().createConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(FROM_EMPLOYEE);

            while (rs.next()) {
                getDataFromResultSet(employees, rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    @Override
    public Employee save(Employee employee) {
        try {
            Connection connection = ConnectionSource.instance().createConnection();
            Statement statement = connection.createStatement();
            Optional<Employee> foundEmployee = getById(employee.getId());
            if (!foundEmployee.isPresent()) {
                statement.executeUpdate(buildInsert(employee));
            } else {
                statement.executeUpdate(buildUpdate(employee));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return getEmployee(FROM_EMPLOYEE_WHERE_ID + employee.getId());
    }

    @Override
    public void delete(Employee employee) {
        try {
            Connection connection = ConnectionSource.instance().createConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(buildDelete(employee.getId().toString()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Employee> getByDepartment(Department department) {
        List<Employee> employees = new ArrayList<>();
        try {
            Connection connection = ConnectionSource.instance().createConnection();
            PreparedStatement statement = connection.prepareStatement(GET_BY_DEPARTMENT);
            statement.setString(PARAMETER_ONE, String.valueOf(department.getId()));
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    getDataFromResultSet(employees, rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    @Override
    public List<Employee> getByManager(Employee employee) {
        List<Employee> employees = new ArrayList<>();
        try {
            Connection connection = ConnectionSource.instance().createConnection();
            PreparedStatement statement = connection.prepareStatement(GET_BY_MANAGER);
            statement.setString(PARAMETER_ONE, String.valueOf(employee.getId()));
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    getDataFromResultSet(employees, rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    private Employee constructEmployee (ResultSet rs) throws SQLException {
        return new Employee(
                new BigInteger(String.valueOf(rs.getString(ID))),
                new FullName(
                        rs.getString(FIRST_NAME),
                        rs.getString(LAST_NAME),
                        rs.getString(MIDDLE_NAME)
                ),
                Position.valueOf(rs.getString(POSITION)),
                rs.getDate(HIREDATE).toLocalDate(),
                new BigDecimal(rs.getInt(SALARY)),
                new BigInteger(String.valueOf(rs.getInt(MANAGER))),
                new BigInteger(String.valueOf(rs.getInt(DEPARTMENT))));
    }

    private void getDataFromResultSet(List<Employee> employeeList, ResultSet rs) throws SQLException {
        Employee employee = constructEmployee(rs);
        employeeList.add(employee);
    }

    private Employee getEmployee(String query) {
        Employee employee = null;
        try {
            Connection connection = ConnectionSource.instance().createConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                employee = constructEmployee(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }

    public String buildInsert(Employee employee) {
        return String.format(BUILD_INSERT,
                employee.getId(),
                employee.getFullName().getFirstName(),
                employee.getFullName().getLastName(),
                employee.getFullName().getMiddleName(),
                employee.getPosition(),
                employee.getHired(),
                employee.getSalary(),
                employee.getManagerId(),
                employee.getDepartmentId());
    }

    public String buildUpdate(Employee employee) {
        return String.format(BUILD_UPDATE,
                employee.getFullName().getFirstName(),
                employee.getFullName().getLastName(),
                employee.getFullName().getMiddleName(),
                employee.getPosition(),
                employee.getHired(),
                employee.getSalary(),
                employee.getManagerId(),
                employee.getDepartmentId(),
                employee.getId());
    }

    public String buildDelete(String id) {
        return String.format(BUILD_DELETE, id);
    }
}
