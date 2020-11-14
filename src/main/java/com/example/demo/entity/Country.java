package com.example.demo.entity;

import lombok.Data;
import javax.persistence.*;

@Entity
@Table(name="country")
@Data
public class Country {
   @Id
   @Column(name="id")
   private int id;

   @Column(name="countryname")
   private String countryname;

   @Column(name="capital")
   private String capital;
}
