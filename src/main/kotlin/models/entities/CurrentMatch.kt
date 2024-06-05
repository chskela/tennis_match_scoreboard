package models.entities

data class CurrentMatch(
    val player1: Player,
    val player2: Player,

    val gameStateFirstPlayer: GameState = GameState.Zero,
    val gameStateSecondPlayer: GameState = GameState.Zero,

    val currentGames: Games= Games(0, 0),
    val sets: List<Games> = emptyList(),
)
