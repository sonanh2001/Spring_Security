package org.aibles.jwtdemo.repository;

import java.util.Optional;
import org.aibles.jwtdemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Boolean existsByUsername(String username);
  Optional<User> findByEmail(String email);
  Boolean existsByEmail(String email);
}
