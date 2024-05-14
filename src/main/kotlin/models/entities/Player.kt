package models.entities

import jakarta.persistence.*

@Entity
@Table(indexes = [Index(name = "idx_name", columnList = "name", unique = true)])
class Player(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long? = null,
    @Column(nullable = false, unique = true)
    val name: String,
) {
    override fun toString(): String {
        return "Player(id=$id, name='$name')"
    }
}