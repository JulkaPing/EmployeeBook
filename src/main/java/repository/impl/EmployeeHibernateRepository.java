package repository.impl;

import lombok.extern.slf4j.Slf4j;
import model.Employee;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import repository.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@Slf4j
public class EmployeeHibernateRepository implements Repository<Long, Employee> {
    private StandardServiceRegistry registry;
    private SessionFactory sessionFactory;

    @Override
    public void init() {
        this.registry = new StandardServiceRegistryBuilder()
                .configure().build();
        this.sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    @Override
    public Employee save(Employee employee) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(employee);
        session.getTransaction().commit();
        return employee;
    }

    @Override
    public boolean update(Long id, Employee employee) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Employee result = session.get(Employee.class, id);
            result.setCity(employee.getCity());
            result.setName(employee.getName());
            session.getTransaction().commit();
        } catch (HibernateException hibernateException) {
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Employee result = session.get(Employee.class, id);
            session.delete(result);
            session.getTransaction().commit();
        } catch (HibernateException hibernateException) {
            return false;
        }
        return true;
    }

    @Override
    public Optional<Employee> findById(Long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Employee employee = session.get(Employee.class, id);
        session.getTransaction().commit();
        return Optional.ofNullable(employee);
    }

    @Override
    public List<Employee> findAll() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Employee> employees = session.createQuery("from Employee", Employee.class).getResultList();
        session.getTransaction().commit();
        return employees;
    }

    @Override
    public List<Employee> findByName(String name) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query<Employee> query = session.createQuery("from Employee where name = :name", Employee.class);
        query.setParameter("name", name);
        session.getTransaction().commit();
        return query.getResultList();
    }

    @Override
    public List<Employee> findByCreateDateInterval(LocalDateTime start, LocalDateTime end) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query<Employee> query = session.createQuery("from Employee where between data = :data", Employee.class);
        query.setParameter("data", new Date());
        session.getTransaction().commit();
        return query.getResultList();
    }

    @Override
    public void close() {
        try {
            StandardServiceRegistryBuilder.destroy(registry);
            log.info("Session is closed.");
        } catch (Exception exception) {
            log.error("Incorrect close session. Message - {}, CannonicalException - {}", exception.getMessage(),
                    exception.getClass().getCanonicalName());
        }
    }
}
