package models.entities

sealed class GameState {
    data object Zero : GameState()
    data object Fifteen : GameState()
    data object Thirty : GameState()
    data class Forty(val more: Boolean = false) : GameState()
}