package org.aibles.jwtdemo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "refresh_token")
public class RefreshToken {
  @Id
  private String tokenId;
  @Column(nullable = false)
  private String content;

  public RefreshToken(String tokenId, String content) {
    this.tokenId = tokenId;
    this.content = content;
  }

  public RefreshToken() {

  }
}
