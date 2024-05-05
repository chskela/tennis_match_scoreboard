package com.chskela.entities

import jakarta.persistence.*

@Entity
@Table(name = "matches")
data class Matches(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    val id: Long? = null,
    @ManyToOne
    val player1: Players = Players(),
    @ManyToOne
    val player2: Players = Players(),
    @ManyToOne
    val winner: Players = Players(),
)