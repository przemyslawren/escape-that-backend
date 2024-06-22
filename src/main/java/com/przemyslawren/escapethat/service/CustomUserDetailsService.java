package com.przemyslawren.escapethat.service;

import com.przemyslawren.escapethat.model.CustomUserDetails;
import com.przemyslawren.escapethat.model.Customer;
import com.przemyslawren.escapethat.model.Employee;
import com.przemyslawren.escapethat.repository.CustomerRepository;
import com.przemyslawren.escapethat.repository.EmployeeRepository;
import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;

    private List<Customer> customerExtent;
    private List<Employee> employeeExtent;

    @PostConstruct
    public void loadCache() {
        customerExtent = customerRepository.findAll();
        employeeExtent = employeeRepository.findAll();
    }

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Customer> customer = customerExtent.stream()
                .filter(c -> c.getEmail().equals(username))
                .findFirst();

        if (customer.isPresent()) {
            Customer user = customer.get();
            return new CustomUserDetails(
                    user.getEmail(),
                    user.getPassword(),
                    user.getId(),
                    List.of(new SimpleGrantedAuthority("ROLE_CUSTOMER"))
            );
        }

        Optional<Employee> employee = employeeExtent.stream()
                .filter(e -> e.getEmail().equals(username))
                .findFirst();
        
        if (employee.isPresent()) {
            Employee user = employee.get();
            return new CustomUserDetails(
                    user.getEmail(),
                    user.getPassword(),
                    user.getId(),
                    List.of(new SimpleGrantedAuthority("ROLE_EMPLOYEE")));
        }

        throw new UsernameNotFoundException("User not found with email: " + username);
    }
}
