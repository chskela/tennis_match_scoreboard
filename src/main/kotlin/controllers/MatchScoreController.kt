package controllers

import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import models.entities.PlayerInOrder
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import services.FinishedMatchesPersistenceService
import services.MatchScoreCalculationService
import services.OngoingMatchesService
import java.util.*

@WebServlet(name = "MatchScoreController", urlPatterns = ["/match-score"])
class MatchScoreController : HttpServlet() {

    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        val uuid = UUID.fromString(request.getParameter("uuid"))
        if (uuid == null) {
            log.info("UUID is null")
            response.status = HttpServletResponse.SC_BAD_REQUEST
        }
        OngoingMatchesService.getCurrentMatch(uuid)
            .onSuccess {
                request.setAttribute("match", it)
                request.setAttribute("uuid", uuid)
                request.getRequestDispatcher("/templates/match-score.jsp").forward(request, response)
            }
            .onFailure {
                log.info("Match not found $uuid")
                response.status = HttpServletResponse.SC_NOT_FOUND
            }
    }

    override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        val matchScoreCalculationService = MatchScoreCalculationService()
        val uuid = UUID.fromString(request.getParameter("uuid"))
        if (uuid == null) {
            log.info("UUID is null")
            response.status = HttpServletResponse.SC_BAD_REQUEST
        }
        OngoingMatchesService.getCurrentMatch(uuid)
            .onFailure {
                log.info("Match not found $uuid")
                response.status = HttpServletResponse.SC_NOT_FOUND
            }.onSuccess {
                val winnerPoint = request.getParameter("winner")
                val updatedMatch = matchScoreCalculationService.calculateMatchScore(it, handleInput(winnerPoint))
                OngoingMatchesService.updateMatch(uuid, updatedMatch)
                if (updatedMatch.endMatch) {
                    FinishedMatchesPersistenceService().saveFinishedMatch(updatedMatch)
                    OngoingMatchesService.deleteMatch(uuid)
                    request.setAttribute("match", updatedMatch)
                    request.setAttribute("uuid", uuid)
                    request.getRequestDispatcher("/templates/match-score.jsp").forward(request, response)
                } else {
                    response.sendRedirect("/match-score?uuid=$uuid")
                }
            }
    }

    private fun handleInput(winnerPoint: String): PlayerInOrder = when (winnerPoint) {
        "First" -> PlayerInOrder.First
        else -> PlayerInOrder.Second
    }

    private val log: Logger = LoggerFactory.getLogger(this::class.java)
}