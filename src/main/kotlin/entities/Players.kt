package com.chskela.entities

import jakarta.persistence.*

@Entity
@Table(name = "players")
class Players(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    val id: Long? = null,
    val name: String = "",
)