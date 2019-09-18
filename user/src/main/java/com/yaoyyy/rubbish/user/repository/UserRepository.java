package com.yaoyyy.rubbish.user.repository;

import com.yaoyyy.rubbish.common.model.user.Customer;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@EntityScan("com.yaoyyy.rubbish.common.model.user.Customer")
public interface UserRepository extends JpaRepository<Customer, String> {
}
