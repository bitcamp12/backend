package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "theater")
public class Theater {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "theater_seq")
    private int theaterSeq;

    @Column(name = "seat_x", nullable = false)
    private int seatX;

    @Column(name = "seat_y", nullable = false)
    private int seatY;

    @Column(name = "name", nullable = false, length = 150)
    private String name;
}
