package com.tech.caching.service;

import com.tech.caching.entity.Employee;
import com.tech.caching.entity.SalaryAccount;


public interface SalaryAccountService {
    void createAccount(Employee employee);

    SalaryAccount incrementBalance(Long accountId);
}
