package controllers

import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import models.entities.PlayerInOrder
import services.FinishedMatchesPersistenceService
import services.MatchScoreCalculationService
import services.OngoingMatchesService
import java.util.*

@WebServlet(name = "MatchScoreController", urlPatterns = ["/match-score"])
class MatchScoreController : HttpServlet() {

    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        val uuid = UUID.fromString(request.getParameter("uuid"))
        val currentMatch = OngoingMatchesService.getCurrentMatch(uuid).getOrThrow()
        request.setAttribute("match", currentMatch)
        request.setAttribute("uuid", uuid)
        request.getRequestDispatcher("/templates/match-score.jsp").forward(request, response)
    }

    override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        val matchScoreCalculationService = MatchScoreCalculationService()
        val uuid = UUID.fromString(request.getParameter("uuid"))
        val currentMatch = OngoingMatchesService.getCurrentMatch(uuid).getOrThrow()
        val winnerPoint = request.getParameter("winner")
        val updatedMatch = matchScoreCalculationService.calculateMatchScore(currentMatch, handleInput(winnerPoint))
        OngoingMatchesService.updateMatch(uuid, updatedMatch)
        if (updatedMatch.endMatch) {
            FinishedMatchesPersistenceService().saveFinishedMatch(updatedMatch)
            OngoingMatchesService.deleteMatch(uuid)
            request.setAttribute("match", updatedMatch)
            request.setAttribute("uuid", uuid)
            request.getRequestDispatcher("/templates/match-score.jsp").forward(request, response)
        } else  {
            response.sendRedirect("/match-score?uuid=$uuid")
        }
    }

    private fun handleInput(winnerPoint: String): PlayerInOrder = when (winnerPoint) {
        "First" -> PlayerInOrder.First
        else -> PlayerInOrder.Second
    }
}