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

    private fun calculateMatchScoreFirstPlayer(currentMatch: CurrentMatch): CurrentMatch {
        val (win, lose) = calculateBall(currentMatch.gameStateFirstPlayer, currentMatch.gameStateSecondPlayer)
        return currentMatch.copy(gameStateFirstPlayer = win, gameStateSecondPlayer = lose)
    }

    private fun calculateMatchScoreSecondPlayer(currentMatch: CurrentMatch): CurrentMatch {
        val (win, lose) = calculateBall(currentMatch.gameStateSecondPlayer, currentMatch.gameStateFirstPlayer)
        return currentMatch.copy(gameStateFirstPlayer = lose, gameStateSecondPlayer = win)
    }

    private fun calculateBall(
        winPlayerGameState: GameState,
        losePlayerGameState: GameState
    ): Pair<GameState, GameState> {
        return when (winPlayerGameState) {
            GameState.Zero -> GameState.Fifteen to losePlayerGameState
            GameState.Fifteen -> GameState.Thirty to losePlayerGameState
            GameState.Thirty -> GameState.Forty(false) to losePlayerGameState
            is GameState.Forty -> when (losePlayerGameState) {
                GameState.Zero,
                GameState.Fifteen,
                GameState.Thirty -> GameState.Zero to GameState.Zero

                is GameState.Forty -> if (winPlayerGameState.more) {
                    GameState.Zero to GameState.Zero
                } else if (losePlayerGameState.more) {
                    winPlayerGameState to GameState.Forty(false)
                } else {
                    GameState.Forty(true) to losePlayerGameState
                }
            }
        }
    }
}