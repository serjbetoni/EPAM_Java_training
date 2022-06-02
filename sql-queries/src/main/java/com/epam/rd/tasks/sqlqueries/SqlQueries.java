package com.epam.rd.tasks.sqlqueries;

/**
 * Implement sql queries like described
 */
public class SqlQueries {
    //Select all employees sorted by last name in ascending order
    //language=HSQLDB
    String select01 = "SELECT id, lastname, salary FROM employee ORDER BY lastname;";

    //Select employees having no more than 5 characters in last name sorted by last name in ascending order
    //language=HSQLDB
    String select02 = "SELECT id, lastname, salary FROM employee WHERE LENGTH(lastname) <=5 ORDER BY lastname;";

    //Select employees having salary no less than 2000 and no more than 3000
    //language=HSQLDB
    String select03 = "SELECT id, lastname, salary FROM employee WHERE salary <= 3000 AND salary >= 2000;";

    //Select employees having salary no more than 2000 or no less than 3000
    //language=HSQLDB
    String select04 = "SELECT id, lastname, salary FROM employee WHERE salary >= 3000 OR salary <= 2000;";

    //Select all employees assigned to departments and corresponding department
    //language=HSQLDB
    String select05 = "SELECT employee.id, employee.lastname, employee.salary, department.name " +
            "FROM employee INNER JOIN department ON employee.department = department.id;";

    //Select all employees and corresponding department name if there is one.
    //Name column containing name of the department "depname".
    //language=HSQLDB
    String select06 = "SELECT employee.id, employee.lastname, employee.salary, department.name AS depname " +
            "FROM employee LEFT JOIN department ON employee.department = department.id;";

    //Select total salary pf all employees. Name it "total".
    //language=HSQLDB
    String select07 = "SELECT SUM(salary) AS total FROM employee;";

    //Select all departments and amount of employees assigned per department
    //Name column containing name of the department "depname".
    //Name column containing employee amount "staff_size".
    //language=HSQLDB
    String select08 = "SELECT department.name AS depname, COUNT(employee.lastname) AS staff_size " +
            "FROM department JOIN employee ON department.id = employee.department GROUP BY depname;";

    //Select all departments and values of total and average salary per department
    //Name column containing name of the department "depname".
    //language=HSQLDB
    String select09 = "SELECT department.name AS depname, SUM(employee.salary) AS total, AVG(employee.salary) " +
            "AS average FROM department JOIN employee ON department.id = employee.department GROUP BY depname;";
    //Select lastnames of all employees and lastnames of their managers if an employee has a manager.
    //Name column containing employee's lastname "employee".
    //Name column containing manager's lastname "manager".
    //language=HSQLDB
    String select10 = "SELECT e.lastname AS employee, m.lastname AS manager " +
            "FROM employee e LEFT JOIN employee m ON e.manager=m.id;";

}
