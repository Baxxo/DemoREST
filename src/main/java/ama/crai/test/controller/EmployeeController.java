package ama.crai.test.controller;

import ama.crai.test.assembler.EmployeeModelAssembler;
import ama.crai.test.entity.Employee;
import ama.crai.test.exception.EmployeeNotFoundException;
import ama.crai.test.repository.EmployeeRepository;
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
public class EmployeeController {

    private final EmployeeRepository repository;
    private final EmployeeModelAssembler assembler;

    public EmployeeController(EmployeeRepository employeeRepository, EmployeeModelAssembler assembler) {
        this.repository = employeeRepository;
        this.assembler = assembler;
    }

    @RequestMapping("/")
    public @ResponseBody String greeting() {
        return "Hello, World";
    }

    @GetMapping("/employees")
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
            value = "/employees",
            params = "id",
            method = RequestMethod.GET
    )
    public EntityModel<Employee> one(@RequestParam("id") Long id) {
        Employee employee = repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        return assembler.toModel(employee);
    }

    //    @PostMapping("/employees")
    @RequestMapping(
            value = "/employees",
            method = RequestMethod.POST
    )
    public ResponseEntity<EntityModel<Employee>> newEmployee(@RequestBody Employee employee) {
        EntityModel<Employee> entityModel = assembler.toModel(repository.save(employee));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    //    @PutMapping("/employees/{id}")
    @RequestMapping(
            value = "/employees",
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

    //    @DeleteMapping("/employees/{id}")
    @RequestMapping(
            value = "/employees",
            params = "id",
            method = RequestMethod.DELETE
    )
    public ResponseEntity<?> deleteEmployee(@RequestParam("id") Long id) {
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
