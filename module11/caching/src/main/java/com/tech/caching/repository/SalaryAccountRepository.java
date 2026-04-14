package com.tech.caching.repository;

import com.tech.caching.entity.SalaryAccount;
import org.springframework.data.repository.CrudRepository;

public interface SalaryAccountRepository extends CrudRepository<SalaryAccount,Long> {
}
