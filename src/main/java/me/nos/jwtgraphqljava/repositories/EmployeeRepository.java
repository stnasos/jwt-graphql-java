package me.nos.jwtgraphqljava.repositories;

import me.nos.jwtgraphqljava.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
}