package com.study.springboot.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Test {
    @Id
    Long id;
    String name;
    String test;
}
