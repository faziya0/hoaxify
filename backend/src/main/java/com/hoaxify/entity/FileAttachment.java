package com.hoaxify.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FileAttachment {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;
private String name;
String fileType;
@Temporal(TemporalType.TIMESTAMP)
private Date timestamp;
@OneToOne(cascade = {CascadeType.REMOVE})
private Hoax hoax;
}
