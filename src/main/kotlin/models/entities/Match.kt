package models.entities

import jakarta.persistence.*

@Entity
@Table(name = "match")
class Match(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "player1_id", foreignKey = ForeignKey(name = "fk_match_player1"))
    val player1: Player,

    @ManyToOne
    @JoinColumn(name = "player2_id", foreignKey = ForeignKey(name = "fk_match_player2"))
    val player2: Player,

    @ManyToOne
    @JoinColumn(name = "winner_id", foreignKey = ForeignKey(name = "fk_match_winner"))
    val winner: Player,
)