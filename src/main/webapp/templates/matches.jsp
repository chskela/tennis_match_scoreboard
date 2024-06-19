<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ru">

<head>
    <meta charset="UTF-8">
    <title>Recent matches</title>
    <link href="/css/styles.css" rel="stylesheet">
</head>

<body>

    <c:set var="matches" value="${requestScope.matches}" scope="request" />
    <c:set var="page" value="${requestScope.page}" scope="request" />
    <c:set var="lastPageNumber" value="${requestScope.lastPageNumber}" scope="request" />
    <c:set var="filterByPlayerName" value="${requestScope.filterByPlayerName}" scope="request" />

    <div class="scoreboard matches">
        <div class="header">
            <div class="cell">
                <h1>Табло учета теннисного матча</h1>
            </div>
        </div>
        <div class="buttons">
            <div class="button"><a href="/new-match">Новый матч</a></div>
            <div class="button"><a href="/">Домой</a></div>
        </div>

        <form class="search-container" action="matches" method="post" name="playerfilter">
            <input class="player button" type="text" placeholder="Имя игрока..." name="filterByPlayerName">
            <button class="button" type="submit">Поиск...</button>
        </form>

        <div class="content">
            <h3 class="h3 cell">Завершенные матчи</h3>
            <c:if test="${!matches.isEmpty()}">

                <div class="cell">Игрок 1</div>
                <div class="cell">Игрок 2</div>
                <div class="cell">Победитель</div>

                <c:forEach var="match" items="${matches}">

                    <div class="cell">${match.player1.name}</div>
                    <div class="cell">${match.player2.name}</div>
                    <div class="cell">${match.winner.name}</div>

                </c:forEach>

                <div class="paginator">

                    <c:if test="${page > 1 }">
                        <p class="paginator-item">
                            <a href="matches?filter_by_player_name=${filterByPlayerName}&page=${page - 1}">Пред.</a>
                        </p>
                    </c:if>
                    <c:forEach begin="1" end="${lastPageNumber}" var="i">
                        <c:choose>
                            <c:when test="${page == i}">
                                <p class="paginator-item"><b>${i}</b></p>
                            </c:when>
                            <c:otherwise>
                                <p class="paginator-item">
                                    <a href="matches?filter_by_player_name=${filterByPlayerName}&page=${i}">${i}</a>
                                </p>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                    <c:if test="${page < lastPageNumber}">
                        <p class="paginator-item">
                            <a href="matches?filter_by_player_name=${filterByPlayerName}&page=${page + 1}">След.</a>
                        </p>
                    </c:if>
                </div>
            </c:if>

            <c:if test="${matches.isEmpty()}">
                <h4 class="h3 cell">Нет завершенных матчей</h4>
            </c:if>

        </div>
    </div>
</body>
</html>