package com.epam.rd.autocode.dao;

import com.epam.rd.autocode.ConnectionSource;
import com.epam.rd.autocode.domain.Department;

import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DepartmentDaoImplement implements DepartmentDao {
    private static final String FROM_DEPARTMENT_WHERE_ID = "SELECT * FROM department Where ID = ";
    private static final String FROM_DEPARTMENT = "SELECT * FROM department";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String LOCATION = "location";
    private static final String BUILD_UPDATE = "UPDATE department SET name = '%s', location = '%s' WHERE id = '%s'";
    private static final String BUILD_DELETE = "DELETE FROM department WHERE id = '%s'";
    private static final String BUILD_INSERT = "INSERT INTO department (id, name, location) VALUES ('%s', '%s', '%s')";

    @Override
    public Optional<Department> getById(BigInteger id) {
        return Optional.ofNullable(getDepartment(FROM_DEPARTMENT_WHERE_ID + id));
    }

    @Override
    public List<Department> getAll() {
        List<Department> departments = new ArrayList<>();
        try {
            Connection connection = ConnectionSource.instance().createConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(FROM_DEPARTMENT);
            while (rs.next()) {
                departments.add(new Department(
                        new BigInteger(String.valueOf(rs.getString(ID))),
                        rs.getString(NAME),
                        rs.getString(LOCATION))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return departments;
    }

    @Override
    public Department save(Department department) {
        try {
            Connection connection = ConnectionSource.instance().createConnection();
            Statement statement = connection.createStatement();
            Optional<Department> foundDepartment = getById(department.getId());
            if (!foundDepartment.isPresent()) {
                statement.executeUpdate(buildInsert(
                        department.getId().toString(),
                        department.getName(),
                        department.getLocation())
                );
            } else {
                statement.executeUpdate(buildUpdate(
                        department.getId().toString(),
                        department.getName(),
                        department.getLocation())
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return getDepartment(FROM_DEPARTMENT_WHERE_ID + department.getId());
    }

    @Override
    public void delete(Department department) {
        try {
            Connection connection = ConnectionSource.instance().createConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(buildDelete(department.getId().toString()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Department getDepartment(String query) {
        Department department = null;
        try {
            Connection connection = ConnectionSource.instance().createConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                department = new Department(
                        new BigInteger(String.valueOf(rs.getString(ID))),
                        rs.getString(NAME),
                        rs.getString(LOCATION)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return department;
    }

    public String buildInsert(String id, String name, String location) {
        return String.format(BUILD_INSERT, id, name, location);
    }

    public String buildUpdate(String id, String name, String location) {
        return String.format(BUILD_UPDATE, name, location, id);
    }

    public String buildDelete(String id) {
        return String.format(BUILD_DELETE, id);
    }
}
