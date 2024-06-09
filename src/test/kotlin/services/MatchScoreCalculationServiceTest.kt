package services

import models.entities.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
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
        currentMatch = step(currentMatch, PlayerInOrder.First, 1)

        //then
        assertEquals(GameState.Fifteen, currentMatch.gameStateFirstPlayer)
        assertEquals(GameState.Zero, currentMatch.gameStateSecondPlayer)
    }

    @Test
    fun `the first player wins two points`() {
        //when
        currentMatch = step(currentMatch, PlayerInOrder.First, 2)

        //then
        assertEquals(GameState.Thirty, currentMatch.gameStateFirstPlayer)
        assertEquals(GameState.Zero, currentMatch.gameStateSecondPlayer)
    }

    @Test
    fun `the first player wins two points and second player wins one`() {
        //when
        currentMatch = step(currentMatch, PlayerInOrder.First, 2)
        currentMatch = step(currentMatch, PlayerInOrder.Second, 1)

        //then
        assertEquals(GameState.Thirty, currentMatch.gameStateFirstPlayer)
        assertEquals(GameState.Fifteen, currentMatch.gameStateSecondPlayer)
    }

    @Test
    fun `the first player wins game`() {
        //when
        currentMatch = step(currentMatch, PlayerInOrder.First, 4)

        //then
        assertEquals(GameState.Zero, currentMatch.gameStateFirstPlayer)
        assertEquals(GameState.Zero, currentMatch.gameStateSecondPlayer)
    }

    @Test
    fun `the first player wins one point in a tiebreaker`() {
        //when
        currentMatch = step(currentMatch, PlayerInOrder.First, 3)
        currentMatch = step(currentMatch, PlayerInOrder.Second, 3)
        currentMatch = step(currentMatch, PlayerInOrder.First, 1)

        //then
        assertEquals(GameState.Advantage, currentMatch.gameStateFirstPlayer)
        assertEquals(GameState.Forty, currentMatch.gameStateSecondPlayer)
    }

    @Test
    fun `the first player wins two points in a tiebreaker`() {
        //when
        currentMatch = step(currentMatch, PlayerInOrder.First, 3)
        currentMatch = step(currentMatch, PlayerInOrder.Second, 3)
        currentMatch = step(currentMatch, PlayerInOrder.First, 2)
        //then
        assertEquals(GameState.Zero, currentMatch.gameStateFirstPlayer)
        assertEquals(GameState.Zero, currentMatch.gameStateSecondPlayer)
    }


    @Test
    fun `the first player wins one set`() {
        //when
        currentMatch = step(currentMatch, PlayerInOrder.First, 24)

        //then
        assertEquals(Games(6, 0), currentMatch.sets[0])
        assertEquals(Games(0, 0), currentMatch.currentGames)
    }

    private fun step(currentMatch: CurrentMatch, playerInOrder: PlayerInOrder, step: Int): CurrentMatch {
        return (1..step).fold(currentMatch) { acc, _ -> service.calculateMatchScore(acc, playerInOrder) }
    }
}