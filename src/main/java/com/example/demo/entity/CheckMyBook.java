package com.example.demo.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.Immutable;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Immutable
@Table(name= "booking_status_view")
public class CheckMyBook {

	@Id
	@Column(name = "member_id")
	private String memberId;
	
	@Column(name ="bookSeq")
	private int bookSeq;
	@Column(name ="payDate")
	private LocalDateTime payDate;
	@Column(name ="payment")
	private String payment;
	@Column(name ="paymentStatus")
	private String paymentStatus;
	
	@Column(name ="playName")
	private String playName;
	
	@Column(name ="targetDate")
	private LocalDateTime targetDate;
}
