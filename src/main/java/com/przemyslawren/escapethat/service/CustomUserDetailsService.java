package com.przemyslawren.escapethat.service;

import com.przemyslawren.escapethat.model.Customer;
import com.przemyslawren.escapethat.model.Employee;
import com.przemyslawren.escapethat.repository.CustomerRepository;
import com.przemyslawren.escapethat.repository.EmployeeRepository;
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Customer> customer = customerRepository.findByEmail(username);
        if (customer.isPresent()) {
            Customer user = customer.get();
            return new User(
                    user.getEmail(),
                    user.getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_CUSTOMER")));
        }

        Optional<Employee> employee = employeeRepository.findByEmail(username);
        if (employee.isPresent()) {
            Employee user = employee.get();
            return new User(
                    user.getEmail(),
                    user.getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_EMPLOYEE")));
        }

        throw new UsernameNotFoundException("User not found with email: " + username);
    }
}
