package models.entities

sealed class GameState(val score: Int) {
    data object Zero : GameState(0)

    data object Fifteen : GameState(15)

    data object Thirty : GameState(30)

    data class Forty(val more: Boolean = false) : GameState(40)
}