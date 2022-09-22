package org.aibles.jwtdemo.repository;

import org.aibles.jwtdemo.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
}
