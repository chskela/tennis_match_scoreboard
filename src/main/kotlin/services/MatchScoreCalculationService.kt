package services

import models.entities.CurrentMatch
import models.entities.GameState
import models.entities.Games
import models.entities.PlayerInOrder
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class MatchScoreCalculationService {

    fun calculateMatchScore(currentMatch: CurrentMatch, playerInOrder: PlayerInOrder): CurrentMatch {
        val newCurrentMatch = when (playerInOrder) {
            PlayerInOrder.First -> {
                val candidate = calculateMatchScoreFirstPlayer(currentMatch)
                if (candidate.gameStateFirstPlayer is GameState.Zero && candidate.gameStateSecondPlayer is GameState.Zero) {
                    candidate.copy(currentGames = candidate.currentGames.copy(first = candidate.currentGames.first + 1))
                } else candidate
            }

            PlayerInOrder.Second -> {
                val candidate = calculateMatchScoreSecondPlayer(currentMatch)
                if (candidate.gameStateFirstPlayer is GameState.Zero && candidate.gameStateSecondPlayer is GameState.Zero) {
                    candidate.copy(currentGames = candidate.currentGames.copy(second = candidate.currentGames.second + 1))
                } else candidate
            }
        }

        return updatedMatchIfEndSet(newCurrentMatch)
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

    private fun checkWonGame(currentMatch: CurrentMatch): Boolean {
        val (firstPlayerGame, secondPlayerGame) = currentMatch.currentGames
        return firstPlayerGame != secondPlayerGame
                && ((firstPlayerGame > secondPlayerGame + 1 && firstPlayerGame > 5)
                || (secondPlayerGame > firstPlayerGame + 1 && secondPlayerGame > 5))
    }

    private fun checkEndMatch(currentMatch: CurrentMatch): Boolean =
        totalWinsInSets(currentMatch, PlayerInOrder.First) == 3 || totalWinsInSets(currentMatch, PlayerInOrder.Second) == 3

    private fun updatedMatchIfEndMatch(updatedCurrentMatch: CurrentMatch) = if (checkEndMatch(updatedCurrentMatch)) {
        log.info("updatedMatchIfEndMatch: End match ${updatedCurrentMatch.sets}")
        updatedCurrentMatch.copy(endMatch = true)
    } else updatedCurrentMatch

    private fun updatedMatchIfEndSet(newCurrentMatch: CurrentMatch) = if (checkWonGame(newCurrentMatch)) {
        val updatedCurrentMatch =
            newCurrentMatch.copy(currentGames = Games(0, 0), sets = newCurrentMatch.sets + newCurrentMatch.currentGames)
        log.info("updatedMatchIfEndSet: End set ${newCurrentMatch.currentGames}")
        updatedMatchIfEndMatch(updatedCurrentMatch)
    } else newCurrentMatch

    private fun totalWinsInSets(currentMatch: CurrentMatch, playerInOrder: PlayerInOrder): Int = currentMatch
        .sets.fold(0) { acc, set ->
            acc + when (playerInOrder) {
                PlayerInOrder.First -> if (set.first > set.second) 1 else 0
                PlayerInOrder.Second -> if (set.second > set.first) 1 else 0
            }
        }

    private val log: Logger = LoggerFactory.getLogger(this::class.java)
}
