package ama.crai.demo.controller;

import ama.crai.demo.assembler.EmployeeModelAssembler;
import ama.crai.demo.entity.Employee;
import ama.crai.demo.entity.Order;
import ama.crai.demo.exception.EmployeeNotFoundException;
import ama.crai.demo.repository.EmployeeRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeRepository repository;
    private final EmployeeModelAssembler assembler;

    public EmployeeController(EmployeeRepository employeeRepository, EmployeeModelAssembler assembler) {
        this.repository = employeeRepository;
        this.assembler = assembler;
    }

    @GetMapping()
    public CollectionModel<EntityModel<Employee>> all() {
        List<EntityModel<Employee>> employees = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(employees,
                linkTo(methodOn(EmployeeController.class).all()).withSelfRel());
    }

    /**
     * Return the employee with the given id.
     *
     * @param id id of the employee
     * @return the employee with links to the other resources
     */
    @ResponseBody
    @RequestMapping(
            params = "id",
            method = RequestMethod.GET
    )
    public EntityModel<Employee> one(@RequestParam("id") Long id) {
        Employee employee = repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        return assembler.toModel(employee);
    }

    @PostMapping
    public ResponseEntity<EntityModel<Employee>> newEmployee(@RequestBody Employee employee) {
        EntityModel<Employee> entityModel = assembler.toModel(repository.save(employee));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @RequestMapping(
            params = "id",
            method = RequestMethod.PUT
    )
    public ResponseEntity<?> replaceEmployee(@RequestBody Employee newEmployee, @RequestParam("id") Long id) {

        Employee updatedEmployee = repository.findById(id)
                .map(employee -> {
                    employee.setFirstName(newEmployee.getFirstName());
                    employee.setLastName(newEmployee.getLastName());
                    employee.setRole(newEmployee.getRole());
                    return repository.save(employee);
                })
                .orElseGet(() -> {
                    newEmployee.setId(id);
                    return repository.save(newEmployee);
                });

        EntityModel<Employee> entityModel = assembler.toModel(updatedEmployee);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @RequestMapping(
            params = "id",
            method = RequestMethod.DELETE
    )
    public ResponseEntity<?> deleteEmployee(@RequestParam("id") Long id) {
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @RequestMapping(
            value = "/getOrder",
            params = "id",
            method = RequestMethod.GET
    )
    public @ResponseBody List<Order> getOrderByUserId(@RequestParam("id") Long id) {
        return repository.findOrdersByUserId(id);
    }

}
