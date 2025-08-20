package com.rookies4.myspringboot.repository;

import com.rookies4.myspringboot.entity.Customer;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class CustomerRepositoryTest {
    @Autowired
    CustomerRepository customerRepository;

    @Test
    @Disabled
    void testNotFoundCustomer() {
        Customer notFoundCustomer = customerRepository.findByCustomerId("AC003").
                orElseThrow(()-> new RuntimeException("Customer NOT Found"));
    }
    @Test
    @Rollback(value = false)
    void testUpdateCustomer() {
        Customer customer = customerRepository.findByCustomerId("AC002").
                orElseThrow(()-> new RuntimeException("Customer NOT Found"));
        customer.setCustomerName("마이 둘리2");
        customerRepository.save(customer);
    }
    @Test
    @Disabled
     void testFindCustomer(){
        Optional<Customer> customerById = customerRepository.findById(1L);
        if(customerById.isPresent()){
            Customer existCustomer = customerById.get();
            assertThat(existCustomer.getId()).isEqualTo(1L);
        }
        //  고객번호가 존재하는 경우 AC001
        Optional<Customer> customerbyCustomerId = customerRepository.findByCustomerId(("AC001"));
        Customer ac001Customer = customerbyCustomerId.orElseGet(()-> new Customer());
        assertThat(ac001Customer.getCustomerName()).isNotEqualTo("스프링fw");
        // 고객번호가 존재하지 않는 경우 AC003
        Customer notFoundCustomer = customerRepository.findByCustomerId("AC003").orElseGet(()-> new Customer());
        assertThat(notFoundCustomer.getCustomerName()).isNull();
    }
    @Disabled
    @Test
    @Rollback(value = false)
    public  void testSaveCustomer() {
        //Given
        Customer customer = new Customer();
        customer.setCustomerId("AC005");
        customer.setCustomerName("스프링Fw5");
        //WHen
        Customer savedCustomer = customerRepository.save(customer);
        //Then
        assertThat(savedCustomer).isNotNull();
        assertThat(savedCustomer.getCustomerName()).isEqualTo("스프링Fw5");
    }
}