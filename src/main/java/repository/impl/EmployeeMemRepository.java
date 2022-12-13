package repository.impl;

import lombok.extern.slf4j.Slf4j;
import model.Employee;
import repository.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static util.Constants.INCORRECT_INDEX;
@Slf4j
public class EmployeeMemRepository implements Repository<Long, Employee> {

    private final List<Employee> store = new ArrayList<>();

    public void init() {
        log.info("Инициализировано хранилище памяти");
    }

    /**
     * Метод сохранения сотрудника в репозиторий
     * @param employee сотрудник
     * @return employee
     */
    public Employee save(Employee employee) {
        long id = generateId();
        employee.setId(id);
        store.add(employee);
        log.info("Пользователь с id - {} сохранен.", id);
        return employee;
    }

    /**
     * Метод обновления данных сотрудника в репозитории
     * @param id сотрудника
     * @param employee сотрудник
     * @return true or false
     */
    public boolean update(Long id, Employee employee) {
        int index = indexOf(id);
        boolean result = index != INCORRECT_INDEX;
        if (result) {
            employee.setId(id);
            employee.setCreated(store.get(index).getCreated());
            store.set(index, employee);
        }
        return result;
    }

    /**
     * Метод удаления сотрудника из репозитория
     * @param id сотрудника
     * @return false
     */
    public boolean delete(Long id) {
        int index = indexOf(id);
        boolean result = index != INCORRECT_INDEX;
        if (result) {
            store.remove(index);
        }
        return result;
    }

    /**
     * Метод поиска сотрудника по id
     * @param id сотрудника
     * @return
     */
    public Optional<Employee> findById(Long id) {
        int index = indexOf(id);
        return index != INCORRECT_INDEX
                ? Optional.of(store.get(index))
                : Optional.empty();
    }

     /**
     * Метод поиска всех сотрудников
     * @return массив сотрудников
     */
    public List<Employee> findAll() {
        return new ArrayList<>(store);
    }
    /**
     * Возвращает пользователя по имени
     * @param name пользователя
     * @return имя пользователя
     */
    public List<Employee> findByName(String name) {
        return store.stream()
                .filter(employee -> employee.getName().equals(name))
                .collect(Collectors.toList());
    }

    public List<Employee> findByCreateDateInterval(LocalDateTime start, LocalDateTime end) {
        Predicate<Employee> afterDate = employee -> !employee.getCreated().isAfter(end);
        Predicate<Employee> beforeDate = employee -> !employee.getCreated().isBefore(start);
        return store.stream()
                .filter(afterDate.and(beforeDate))
                .collect(Collectors.toList());
    }

    @Override
    public void close() {
        log.info("This is mem repository. Session is not found.");
    }

    /**
     * Возвращает под каким индексом находится пользователь
     * @param id пользователя
     * @return индекс пользователя
     */
    private int indexOf(long id) {
        return IntStream.range(0, store.size())
                .filter(index -> store.get(index).getId() == id)
                .findFirst()
                .orElse(INCORRECT_INDEX);
    }

    private long generateId() {
        Random random = new Random();
        return Math.abs(random.nextLong());
    }
}
