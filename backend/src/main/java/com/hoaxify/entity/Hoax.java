package com.hoaxify.entity;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Hoax  {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Size(message="{hoaxify.validation.constraints.Size.hoaxify.message}", min=1,max=1000)
	private String content;

	@Temporal(TemporalType.TIMESTAMP)
	private Date timestap;
	
	@ManyToOne
	private User user;
	
	@OneToOne(mappedBy="hoax",cascade=CascadeType.REMOVE)
	FileAttachment fileAttachment;
	
}
