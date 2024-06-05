package models.entities

sealed class GameState(val score: String) {
    data object Zero : GameState("0")

    data object Fifteen : GameState("15")

    data object Thirty : GameState("30")

    data object Forty : GameState("40")

    data object Advantage : GameState("AD")
}