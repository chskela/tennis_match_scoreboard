package services

import models.entities.CurrentMatch
import models.entities.GameState
import models.entities.PlayerInOrder

class MatchScoreCalculationService {

    fun calculateMatchScore(currentMatch: CurrentMatch, playerInOrder: PlayerInOrder): CurrentMatch {
        val newCurrentMatch =  when (playerInOrder) {
            PlayerInOrder.First -> {
                val candidate = calculateMatchScoreFirstPlayer(currentMatch)
                if (candidate.gameStateFirstPlayer is GameState.Zero && candidate.gameStateSecondPlayer is GameState.Zero){
                    candidate.copy(currentGames = candidate.currentGames.copy(first = candidate.currentGames.first + 1))
                } else candidate
            }
            PlayerInOrder.Second -> {
                val candidate = calculateMatchScoreSecondPlayer(currentMatch)
                if (candidate.gameStateFirstPlayer is GameState.Zero && candidate.gameStateSecondPlayer is GameState.Zero){
                    candidate.copy(currentGames = candidate.currentGames.copy(second = candidate.currentGames.second + 1))
                } else candidate
            }
        }
        return newCurrentMatch
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
            GameState.Thirty -> GameState.Forty to losePlayerGameState
            GameState.Forty -> when (losePlayerGameState) {
                GameState.Zero,
                GameState.Fifteen,
                GameState.Thirty -> GameState.Zero to GameState.Zero
                GameState.Forty -> GameState.Advantage to losePlayerGameState
                GameState.Advantage -> GameState.Forty to GameState.Forty
            }
            GameState.Advantage -> GameState.Zero to GameState.Zero
        }
    }
}
