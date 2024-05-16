package services

import models.entities.CurrentMatch
import models.entities.GameState
import models.entities.PlayerInOrder

class MatchScoreCalculationService {

    fun calculateMatchScore(currentMatch: CurrentMatch, playerInOrder: PlayerInOrder): CurrentMatch {
        return when (playerInOrder) {
            PlayerInOrder.First -> calculateMatchScoreFirstPlayer(currentMatch)
            PlayerInOrder.Second -> calculateMatchScoreSecondPlayer(currentMatch)
        }
    }

    private fun calculateMatchScoreFirstPlayer(currentMatch: CurrentMatch) =
        when (val gameStateFirstPlayer = currentMatch.gameStateFirstPlayer) {
            GameState.Zero -> currentMatch.copy(gameStateFirstPlayer = GameState.Fifteen)
            GameState.Fifteen -> currentMatch.copy(gameStateFirstPlayer = GameState.Thirty)
            GameState.Thirty -> currentMatch.copy(gameStateFirstPlayer = GameState.Forty(false))
            is GameState.Forty -> when (currentMatch.gameStateSecondPlayer) {
                GameState.Zero,
                GameState.Fifteen,
                GameState.Thirty -> currentMatch.copy(
                    gameStateFirstPlayer = GameState.Zero,
                    gameStateSecondPlayer = GameState.Zero
                )
                is GameState.Forty -> if (gameStateFirstPlayer.more) {
                    currentMatch.copy(
                        gameStateFirstPlayer = GameState.Zero,
                        gameStateSecondPlayer = GameState.Zero
                    )
                } else if (currentMatch.gameStateSecondPlayer.more) {
                    currentMatch.copy(gameStateSecondPlayer = GameState.Forty(false))
                } else {
                    currentMatch.copy(gameStateFirstPlayer = GameState.Forty(true))
                }
            }
        }

    private fun calculateMatchScoreSecondPlayer(currentMatch: CurrentMatch) =
        when (val gameStateSecondPlayer = currentMatch.gameStateSecondPlayer) {
            GameState.Zero -> currentMatch.copy(gameStateSecondPlayer = GameState.Fifteen)
            GameState.Fifteen -> currentMatch.copy(gameStateSecondPlayer = GameState.Thirty)
            GameState.Thirty -> currentMatch.copy(gameStateSecondPlayer = GameState.Forty(false))
            is GameState.Forty -> when (currentMatch.gameStateFirstPlayer) {
                GameState.Zero,
                GameState.Fifteen,
                GameState.Thirty -> currentMatch.copy(
                    gameStateFirstPlayer = GameState.Zero,
                    gameStateSecondPlayer = GameState.Zero
                )
                is GameState.Forty -> if (gameStateSecondPlayer.more) {
                    currentMatch.copy(
                        gameStateFirstPlayer = GameState.Zero,
                        gameStateSecondPlayer = GameState.Zero
                    )
                } else if (currentMatch.gameStateFirstPlayer.more) {
                    currentMatch.copy(gameStateFirstPlayer = GameState.Forty(false))
                } else {
                    currentMatch.copy(gameStateSecondPlayer = GameState.Forty(true))
                }
            }
        }
}