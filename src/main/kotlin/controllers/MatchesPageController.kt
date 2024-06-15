package controllers

import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import models.dao.MatchDao
import models.entities.MatchesState
import kotlin.math.ceil

@WebServlet(name = "MatchesPageController", urlPatterns = ["/matches"])
class MatchesPageController : HttpServlet(){
    private val recordsPerPage: Int = 5

    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        val pageNumber = request.getParameter("page")?.toInt() ?: 1
        val filterByPlayerName  = request.getParameter("filter_by_player_name")

        val matchDao = MatchDao()

        val matchesState = if (filterByPlayerName.isNullOrEmpty()) {
            val matches  = matchDao.getAllMatches(pageNumber, recordsPerPage).getOrElse {
                emptyList()
            }
            val count = matchDao.countAllMatches().getOrElse { 0 }
            val lastPageNumber = ceil(count.toDouble() / recordsPerPage).toLong()
            MatchesState(matches = matches, lastPageNumber = lastPageNumber)
        } else  {
            val matches  = matchDao.getMatchesByPlayerName(filterByPlayerName, pageNumber, recordsPerPage).getOrElse {
                emptyList()
            }
            val count = matchDao.countMatchesByPlayerName(filterByPlayerName).getOrElse { 0 }
            val lastPageNumber = ceil(count.toDouble() / recordsPerPage).toLong()
            MatchesState(matches = matches, lastPageNumber = lastPageNumber)
        }

        request.setAttribute("matches", matchesState.matches)
        request.setAttribute("lastPageNumber", matchesState.lastPageNumber)
        request.setAttribute("filterByPlayerName",filterByPlayerName)
        request.setAttribute("page", pageNumber)
        request.getRequestDispatcher("templates/matches.jsp").forward(request, response)
    }

    override fun doPost(request: HttpServletRequest, response: HttpServletResponse)  {
        val filterByPlayerName = request.getParameter("filterByPlayerName")
        val pageNumber = request.getParameter("page")?.toInt() ?: 1
        response.sendRedirect("/matches?page=$pageNumber&filter_by_player_name=$filterByPlayerName")
    }
}