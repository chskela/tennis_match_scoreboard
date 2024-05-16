package models.entities

sealed class PlayerInOrder {
    data object First : PlayerInOrder()
    data object Second : PlayerInOrder()
}