package services

import models.entities.CurrentMatch
import models.entities.GameState
import models.entities.Player
import models.entities.PlayerInOrder
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

class MatchScoreCalculationServiceTest {
    private val service = MatchScoreCalculationService()
    private lateinit var currentMatch: CurrentMatch

    @BeforeEach
    fun setUp() {
        currentMatch = CurrentMatch(
            player1 = Player(id = 1, name = "Player 1"),
            player2 = Player(id = 2, name = "Player 2"),
        )
    }

    @Test
    fun `the first player wins one point`() {
        //when
        currentMatch = service.calculateMatchScore(currentMatch, PlayerInOrder.First)

        //then
        assertEquals(GameState.Fifteen, currentMatch.gameStateFirstPlayer)
        assertEquals(GameState.Zero, currentMatch.gameStateSecondPlayer)
    }

    @Test
    fun `the first player wins two points`() {
        //when
        currentMatch = service.calculateMatchScore(currentMatch, PlayerInOrder.First)
        currentMatch = service.calculateMatchScore(currentMatch, PlayerInOrder.First)

        //then
        assertEquals(GameState.Thirty, currentMatch.gameStateFirstPlayer)
        assertEquals(GameState.Zero, currentMatch.gameStateSecondPlayer)
    }

    @Test
    fun `the first player wins two points and second player wins one`() {
        //when
        currentMatch = service.calculateMatchScore(currentMatch, PlayerInOrder.First)
        currentMatch = service.calculateMatchScore(currentMatch, PlayerInOrder.First)
        currentMatch = service.calculateMatchScore(currentMatch, PlayerInOrder.Second)

        //then
        assertEquals(GameState.Thirty, currentMatch.gameStateFirstPlayer)
        assertEquals(GameState.Fifteen, currentMatch.gameStateSecondPlayer)
    }

    @Test
    fun `the first player wins game`() {
        //when
        currentMatch = service.calculateMatchScore(currentMatch, PlayerInOrder.First)
        currentMatch = service.calculateMatchScore(currentMatch, PlayerInOrder.First)
        currentMatch = service.calculateMatchScore(currentMatch, PlayerInOrder.First)
        currentMatch = service.calculateMatchScore(currentMatch, PlayerInOrder.First)

        //then
        assertEquals(GameState.Zero, currentMatch.gameStateFirstPlayer)
        assertEquals(GameState.Zero, currentMatch.gameStateSecondPlayer)
    }

    @Test
    fun `the first player wins one point in a tiebreaker`() {
        //when
        currentMatch = service.calculateMatchScore(currentMatch, PlayerInOrder.First)
        currentMatch = service.calculateMatchScore(currentMatch, PlayerInOrder.First)
        currentMatch = service.calculateMatchScore(currentMatch, PlayerInOrder.First)
        currentMatch = service.calculateMatchScore(currentMatch, PlayerInOrder.Second)
        currentMatch = service.calculateMatchScore(currentMatch, PlayerInOrder.Second)
        currentMatch = service.calculateMatchScore(currentMatch, PlayerInOrder.Second)
        currentMatch = service.calculateMatchScore(currentMatch, PlayerInOrder.First)

        //then
        assertEquals(GameState.Forty(true), currentMatch.gameStateFirstPlayer)
        assertEquals(GameState.Forty(false), currentMatch.gameStateSecondPlayer)
    }

    @Test
    fun `the first player wins two points in a tiebreaker`() {
        //when
        currentMatch = service.calculateMatchScore(currentMatch, PlayerInOrder.First)
        currentMatch = service.calculateMatchScore(currentMatch, PlayerInOrder.First)
        currentMatch = service.calculateMatchScore(currentMatch, PlayerInOrder.First)
        currentMatch = service.calculateMatchScore(currentMatch, PlayerInOrder.Second)
        currentMatch = service.calculateMatchScore(currentMatch, PlayerInOrder.Second)
        currentMatch = service.calculateMatchScore(currentMatch, PlayerInOrder.Second)
        currentMatch = service.calculateMatchScore(currentMatch, PlayerInOrder.First)
        currentMatch = service.calculateMatchScore(currentMatch, PlayerInOrder.First)

        //then
        assertEquals(GameState.Zero, currentMatch.gameStateFirstPlayer)
        assertEquals(GameState.Zero, currentMatch.gameStateSecondPlayer)
    }
}