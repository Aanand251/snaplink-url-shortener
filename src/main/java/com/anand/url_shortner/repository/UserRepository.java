package com.anand.url_shortner.repository;

import com.anand.url_shortner.entity.Role;
import com.anand.url_shortner.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    List<User> findByMarkedTrue();

    List<User> findBySuspendedTrue();

    long countByRole(Role role);

    long countByMarkedTrue();

    long countBySuspendedTrue();

    // ---------- Admin Search ----------

    Page<User> findByNameContainingIgnoreCase(
            String name,
            Pageable pageable
    );

    Page<User> findByEmailContainingIgnoreCase(
            String email,
            Pageable pageable
    );

    Page<User> findByRole(
            Role role,
            Pageable pageable
    );

    Page<User> findByMarked(
            boolean marked,
            Pageable pageable
    );

    Page<User> findBySuspended(
            boolean suspended,
            Pageable pageable
    );
}