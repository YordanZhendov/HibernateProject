import model.Address;
import model.Employee;
import model.Project;
import model.Town;

import javax.persistence.EntityManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class Engine implements Runnable{
    private final BufferedReader reader;
    private final EntityManager entityManager;

    public Engine(EntityManager entityManager) {
        this.reader = new BufferedReader(new InputStreamReader(System.in));
        this.entityManager = entityManager;
    }

    @Override
    public void run() {
        //ex2
//        changeCasingEx2();
//        try {
//            containsEmployeeEx3();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        employeeSalaryOverEx4();
//        try {
//            employeesFromDepartmentEx5();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            addingNewAddressAndUpdateEmployeeEx6();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        addressesWithEmployeecountEx7();
//        try {
//            employeeWithProject();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        findlast10ProjectEx8();

//        increaseSalary();
    }

    private void increaseSalary() {
        entityManager.getTransaction().begin();
        int affectedRows = entityManager.createQuery("UPDATE Employee e " +
                "SET e.salary = e.salary*1.12 " +
                "WHERE e.department.id IN (1,2,4,11)")
                .executeUpdate();
        entityManager.getTransaction().commit();
        System.out.printf("Affected Rows: %d%n",affectedRows);

        entityManager.createQuery("SELECT e FROM Employee e " +
                "WHERE e.department.id IN (1,2,4,11)",Employee.class)
                .getResultStream()
                .forEach(a-> {
                    System.out.printf("%s %s ($%s)%n"
                            ,a.getFirstName(),a.getLastName(),a.getSalary());
                });

    }

    private void findlast10ProjectEx8() {

        List<Project> projects=entityManager.createQuery("SELECT p FROM Project p " +
                "ORDER BY p.startDate DESC, p.name ",Project.class)
                .setMaxResults(10)
                .getResultList();
        System.out.println();
        List<String> sortedProjects=new ArrayList<>();
        for (Project project : projects) {
            sortedProjects.add(project.getName());

        }
        Collections.sort(sortedProjects);
        for (String sortedProject : sortedProjects) {
            System.out.printf("%s%n",sortedProject);
        }
    }

    private void employeeWithProject() throws IOException {
        System.out.println("Please enter Your ID:");
        int idInput=Integer.parseInt(reader.readLine());
        Employee employee=entityManager
                .find(Employee.class,idInput);
        List<String> projects=new ArrayList<>();
        for (Project project : employee.getProjects()) {
            projects.add(project.getName());
        }
        Collections.sort(projects);
        System.out.printf("%s %s - %s%n",employee.getFirstName(),employee.getLastName(),employee.getJobTitle());
        for (String project : projects) {
            System.out.printf("\t%s%n",project);
        }
        System.out.println();
    }

    private void addressesWithEmployeecountEx7() {
        List<Address> addresses=entityManager.createQuery("SELECT a FROM Address a " +
                "ORDER BY a.employees.size DESC ",Address.class)
                .setMaxResults(10)
                .getResultList();
        for (Address address : addresses) {
            System.out.printf("%s, %s - %d%n",address.getText(),address.getTown().getName(),address.getEmployees().size());
        }


    }


    private void addingNewAddressAndUpdateEmployeeEx6() throws IOException {
        Address address=createAddress("Vitoshka 15");
        System.out.println("Enter lastname please:");
        String lastNameForUpdate= reader.readLine();

        List<Employee> employee=entityManager.createQuery("SELECT e FROM Employee e " +
                "WHERE e.lastName=:name",Employee.class)
                .setParameter("name",lastNameForUpdate)
                .getResultList();
        entityManager.getTransaction().begin();
        for (Employee employee1 : employee) {
            employee1.setAddress(address);
        }
        entityManager.getTransaction().commit();

    }

    private Address createAddress(String addressText) {

        Address address=new Address();
        address.setText(addressText);

        entityManager.getTransaction().begin();
        entityManager.persist(address);
        entityManager.getTransaction().commit();

        return address;
    }

    private void employeesFromDepartmentEx5() throws IOException {
        System.out.println("Enter a department please: :-)");
        String department= reader.readLine();
        entityManager.createQuery("SELECT e FROM Employee e " +
                "WHERE e.department.name =:name ORDER BY e.salary ASC,e.id ASC ",Employee.class)
                .setParameter("name",department)
                .getResultStream()
                .forEach(employee -> { System.out.printf("%s %s from %s - $%.2f%n"
                        ,employee.getFirstName(),employee.getLastName()
                        ,employee.getDepartment().getName(),employee.getSalary());
                });
//                    employee.getDepartment().getName(),employee.getSalary());
//                    employee.getDepartment().getName(),employee.getSalary()); );
//        System.out.println("Enter a department please: :-)");
//        String department= reader.readLine();
//        List<Employee> employees=entityManager.createQuery("SELECT e FROM Employee e " +
//                "WHERE e.department.name =:name ORDER BY e.salary ASC,e.id ASC ",Employee.class)
//                .setParameter("name",department)
//                .getResultList();
//        for (Employee employee : employees) {
//            System.out.printf("%s %s from %s - $%.2f%n",employee.getFirstName(),employee.getLastName(),
//                    employee.getDepartment().getName(),employee.getSalary());
//        }

    }

    private void employeeSalaryOverEx4() {
        entityManager
                .createQuery("SELECT e FROM Employee e " +
                "WHERE e.salary > 50000",Employee.class)
                .getResultStream()
                .map(Employee::getFirstName)
                .forEach(System.out::println);
//        List<Employee> employees=entityManager.createQuery("SELECT e FROM Employee e " +
//                "WHERE e.salary > 50000",Employee.class)
//                .getResultList();
//        System.out.println("_____________________________________________");
//        for (Employee employee : employees) {
//            System.out.println(employee.getFirstName());
//
//        }
    }

    private void containsEmployeeEx3() throws IOException {
        System.out.println("Enter your full name:");
        String employee=reader.readLine();

        List<Employee> employees=entityManager.createQuery("SELECT e FROM Employee e " +
                "WHERE concat(e.firstName,' ',e.lastName)=:name",Employee.class)
                .setParameter("name",employee)
                .getResultList();
        if(employees.size() !=0){
            System.out.println("Yes");
        }else{
            System.out.println("No");
        }
        System.out.println();

    }

    private void changeCasingEx2() {
        List<Town> towns=entityManager
                .createQuery("SELECT t FROM Town t " +
                        "WHERE length(t.name) <=5 ",Town.class)
                .getResultList();
        entityManager.getTransaction().begin();
            towns.forEach(entityManager::detach);
            for (Town town : towns) {
                town.setName(town.getName().toLowerCase());
            }

            towns.forEach(entityManager::merge);
            entityManager.flush();
        entityManager.getTransaction().commit();
    }
}
