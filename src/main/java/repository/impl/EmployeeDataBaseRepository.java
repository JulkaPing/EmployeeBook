package repository.impl;

import lombok.extern.slf4j.Slf4j;
import model.Employee;
import repository.Repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class EmployeeDataBaseRepository implements Repository<Long, Employee> {

    private Connection connection;

    @Override
    public void init() {
        try {
            InputStream inputStream = ClassLoader.getSystemResourceAsStream("database.properties");
            Properties properties = new Properties();
            properties.load(inputStream);
            connection = DriverManager.getConnection(
                    properties.getProperty("url"),
                    properties.getProperty("username"),
                    properties.getProperty("password")
            ); {
                DatabaseMetaData metaData = connection.getMetaData();
                log.info("Connection to database is successful - {}", !metaData.getConnection().isClosed());
                log.info("Connection to database by url - {}", metaData.getURL());
                String script = new BufferedReader(new FileReader("src/main/resources/scripts/employees_table.sql"))
                        .lines()
                        .collect(Collectors.joining());
                try (Statement statement = connection.createStatement()) {
                    int result = statement.executeUpdate(script);
                    if (result == 0) {
                        log.info("Table employees created successful");
                    }
                }
            }

        } catch (Exception e) {
            log.error("Ошибка подключения к базе данных.{}, {}", e.getClass().getCanonicalName(), e.getMessage());
        }
    }

    @Override
    public Employee save(Employee employee) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO employees(name, city, created) VALUES (?, ?, ?)",
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, employee.getName());
            preparedStatement.setString(2, employee.getCity());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(employee.getCreated()));
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs != null && rs.next()) {
                long generatedId = rs.getLong("id");
                log.info("Employee with id - {} save successful", generatedId);
                employee.setId(generatedId);
            }
        } catch (SQLException exception) {
            log.error("Exception in save method. {}, {}", exception, exception.getMessage());
        }
        return employee;
    }

    @Override
    public boolean update(Long id, Employee employee) {
        boolean rsl = false;
        try (PreparedStatement statement = connection.prepareStatement(
                "UPDATE employees SET name = ?, city = ? WHERE id = ?"
        )) {
            statement.setString(1, employee.getName());
            statement.setString(2, employee.getCity());
            statement.setString(3, String.valueOf(id));
            if (statement.executeUpdate() > 0) {
                rsl = true;
            }
        } catch (SQLException sqlexception) {
            log.error("Exception in update method. {}, {}", sqlexception, sqlexception.getMessage());
        }
        return rsl;
    }

    @Override
    public boolean delete(Long id) {
        boolean rsl = false;
        try (PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM employees WHERE id = ?")) {
            statement.setLong(1, id);
            if (statement.executeUpdate() > 0) {
                rsl = true;
            }
        } catch (SQLException sqlexception) {
            log.error("Exception in delete method. {}, {}", sqlexception, sqlexception.getMessage());
        }
        return rsl;
    }

    @Override
    public Optional<Employee> findById(Long id) {
        Optional<Employee> rsl = Optional.empty();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM employees WHERE id = ?")) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Employee employee = Employee.builder()
                        .id(rs.getLong("id"))
                        .name(rs.getString("name"))
                        .city(rs.getString("city"))
                        .created(rs.getTimestamp("created").toLocalDateTime())
                        .build();
                rsl = Optional.ofNullable(employee);
            }
        } catch (SQLException sqlexception) {
            sqlexception.printStackTrace();
            log.error("Exception in findById method. {}, {}", sqlexception, sqlexception.getMessage());
        }
        return rsl;
    }

    @Override
    public List<Employee> findAll() {
        List<Employee> result = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM employees");
            while (resultSet.next()) {
                Employee employee = Employee.builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .city(resultSet.getString("city"))
                        .created(resultSet.getTimestamp("created").toLocalDateTime())
                        .build();
                result.add(employee);
            }
        } catch (SQLException sqlexception) {
            sqlexception.printStackTrace();
            log.error("Exception findByAll method. {}, {}", sqlexception, sqlexception.getMessage());
        }
        return result;
    }

    @Override
    public List<Employee> findByName(String name) {
        List<Employee> rsl = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM employees WHERE name = ?")) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Employee employee = Employee.builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .city(resultSet.getString("city"))
                        .created(resultSet.getTimestamp("created").toLocalDateTime())
                        .build();
                rsl.add(employee) ;
            }
        } catch (SQLException sqlexception) {
            sqlexception.printStackTrace();
            log.error("Exception in findByName method. {}, {}", sqlexception, sqlexception.getMessage());
        }
        return rsl;
    }

    @Override
    public List<Employee> findByCreateDateInterval(LocalDateTime start, LocalDateTime end) {
        List<Employee> rsl = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM employees WHERE created BETWEEN ? AND ?")) {
            preparedStatement.setTimestamp(1, Timestamp.valueOf(start));
            preparedStatement.setTimestamp(2, Timestamp.valueOf(end));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Employee employee = Employee.builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .city(resultSet.getString("city"))
                        .created(resultSet.getTimestamp("created").toLocalDateTime())
                        .build();
                rsl.add(employee);
            }
        } catch (SQLException sqlexception) {
            sqlexception.printStackTrace();
            log.error("Exception in findByCreateDateInterval method. {}, {}", sqlexception, sqlexception.getMessage());
        }
        return rsl;
    }
}
