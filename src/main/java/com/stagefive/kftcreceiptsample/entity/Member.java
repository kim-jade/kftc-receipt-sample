package com.stagefive.kftcreceiptsample.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Member {

  @Id
  Long id;

  @Column
  String name;

}
