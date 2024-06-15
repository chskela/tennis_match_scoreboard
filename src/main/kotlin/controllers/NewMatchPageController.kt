package controllers

import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import models.dao.PlayerDao
import services.OngoingMatchesService

@WebServlet(name = "NewMatchPageController", urlPatterns = ["/new-match"])
class NewMatchPageController : HttpServlet() {

    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        request.getRequestDispatcher("/templates/new-match.html").forward(request, response)
    }

    override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        val playerName1 = request.getParameter("player1")
        val playerName2 = request.getParameter("player2")

        val playerDao = PlayerDao()

        val player1 = playerDao.save(playerName1).getOrElse {
            playerDao.findByName(playerName1).getOrThrow()
        }
        val player2 = playerDao.save(playerName2).getOrElse {
            playerDao.findByName(playerName2).getOrThrow()
        }
        val matchId = OngoingMatchesService.createNewMatch(player1, player2)
        response.sendRedirect("/match-score?uuid=$matchId")
    }
}