package com.example.demo.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "play")
public class Play {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "play_seq")
    private int playSeq;

    @Column(name = "name", nullable = false, length = 200)
    private String name;
    
    @ManyToOne
    @JoinColumn(name = "member_seq", nullable = false)
    private Member member;

    @Column(name = "start_time")
    private LocalDateTime startTime; 

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "image_file_name", length = 300)
    private String imageFileName;

    @Column(name = "image_original_file_name", length = 300)
    private String imageOriginalFileName;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "address", length = 300)
    private String address;

    @Column(name = "total_actor", length = 300)
    private String totalActor;
    
    @Column(name = "price")
    private int price;
    
    @Column(name = "age_limit", length = 100)
    private String ageLimit;
    
    @Column(name = "running_time")
    private int runningTime;
}
