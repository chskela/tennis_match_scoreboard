<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Recent matches</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>

<c:set var="matches" value="${requestScope.matches}" scope="request"/>
<c:set var="page" value="${requestScope.page}" scope="request"/>
<c:set var="lastPageNumber" value="${requestScope.lastPageNumber}" scope="request"/>
<c:set var="filterByPlayerName" value="${requestScope.filterByPlayerName}" scope="request"/>

<div class="nav">
    <a href="/">Tennis scoreboard</a>
    <a href="new-match">New match</a>
    <a class="current" href="matches">Recent matches</a>
    <div class="search-container">
        <form action="matches" method="post" name="playerfilter">
            <input type="text" placeholder="Player name..." name="filterByPlayerName">
            <button type="submit">Find...</button>
        </form>

    </div>
</div>

<div class="content">
    <h1>Recent matches</h1>
            <c:if test="${!matches.isEmpty()}">
            <table class="info">
                <tr>
                    <th>Player1</th>
                    <th>Player2</th>
                    <th>Winner</th>
                </tr>
                <c:forEach var="match" items="${matches}">
                    <tr>
                        <td>${match.player1.name}</td>
                        <td>${match.player2.name}</td>
                        <td>${match.winner.name}</td>
                    </tr>
                </c:forEach>
            </table>




                <table class="paginator">
                    <tr>
                        <c:if test="${page > 1 }">
                            <td><a href="matches?filter_by_player_name=${filterByPlayerName}&page=${page - 1}">Previous</a></td>
                        </c:if>
                        <c:forEach begin="1" end="${lastPageNumber}" var="i">
                            <c:choose>
                                <c:when test="${page == i}">
                                    <td>${i}</td>
                                </c:when>
                                <c:otherwise>
                                    <td><a href="matches?filter_by_player_name=${filterByPlayerName}&page=${i}">${i}</a></td>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                        <c:if test="${page < lastPageNumber}">
                            <td><a href="matches?filter_by_player_name=${filterByPlayerName}&page=${page + 1}">Next</a></td>
                        </c:if>
                        </c:if>
                    </tr>
                </table>



            <c:if test="${uiState.matches.isEmpty()}">
                <h1>No matches!</h1>
            </c:if>
    </div>
<div class="footer">
    <p>footer</p>
</div>



</body>
</html>
