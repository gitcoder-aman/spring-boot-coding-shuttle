package com.tech.caching.service.impl;

import com.tech.caching.entity.Employee;
import com.tech.caching.entity.SalaryAccount;
import com.tech.caching.repository.SalaryAccountRepository;
import com.tech.caching.service.SalaryAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRED)
public class SalaryAccountServiceImpl implements SalaryAccountService {

    private final SalaryAccountRepository salaryAccountRepository;

    @Override
    public void createAccount(Employee employee) {

//        if(employee.getName().equals("Raushan Kumar Gupta")) throw new RuntimeException("Raushan Kumar Gupta is not allowed");
        SalaryAccount salaryAccount = SalaryAccount.builder()
                .employee(employee)
                .balance(BigDecimal.ZERO)
                .build();
        salaryAccountRepository.save(salaryAccount);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public SalaryAccount incrementBalance(Long accountId) {

        SalaryAccount salaryAccount = salaryAccountRepository.findById(accountId).orElseThrow(() -> new RuntimeException("Account not found"));

        BigDecimal prevBalance = salaryAccount.getBalance();
        BigDecimal newBalance = prevBalance.add(BigDecimal.valueOf(1L));
        salaryAccount.setBalance(newBalance);
        return salaryAccountRepository.save(salaryAccount);
    }
}
