package com.example.demo.entity;

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
@Table(name = "play_time_table")
public class PlayTimeTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "play_time_table_seq")
    private int playTimeTableSeq;

    @ManyToOne
    @JoinColumn(name = "play_seq", nullable = false)
    private Play play;

    @ManyToOne
    @JoinColumn(name = "theater_seq", nullable = false)
    private Theater theater;

    @Column(name = "start_time", nullable = false)
    private String startTime;

    @Column(name = "end_time", nullable = false)
    private String endTime;

    @Column(name = "start_dis_time", nullable = true)
    private String startDisTime;

    @Column(name = "end_dis_time", nullable = true)
    private String endDisTime;

    @Column(name = "min_rate", nullable = false)
    private Integer minRate;

    @Column(name = "max_rate", nullable = false)
    private Integer maxRate;

    @Column(name = "target_date", nullable = false)
    private LocalDateTime targetDate;

    @Column(name = "discount_rate", nullable = true)
    private Integer discountRate;

    @Column(name = "discounted_price", nullable = true)
    private Integer discountedPrice;
}
	