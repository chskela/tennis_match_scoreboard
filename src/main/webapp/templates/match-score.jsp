<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
        <!DOCTYPE html>
        <html lang="ru">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Tennis Scoreboard</title>
            <link href="/css/styles.css" rel="stylesheet">
        </head>

        <body>
            <div class="scoreboard match">
                <c:set var="match" value="${requestScope.match}" scope="request" />
                <div class="header">
                    <div class="cell">
                        <h1>Табло учета теннисного матча</h1>
                    </div>
                </div>
                <div class="header-table">
                    <div class="cell">Игроки</div>
                    <div class="cell">Очки</div>
                    <div class="cell">Геймы</div>
                    <div class="cell">Сеты</div>
                </div>

                <div class="player-1">
                    <div class="player-name cell">
                        <c:out value="${match.player1.name}" />
                    </div>
                    <div class="points cell">
                        <c:out value="${match.gameStateFirstPlayer.score}" />
                    </div>
                    <div class="games cell">${match.currentGames.first}</div>
                    <div class="sets cell">
                        <c:forEach var="set" items="${match.sets}">
                            <span class="set-score">${set.first}</span>
                        </c:forEach>
                    </div>
                </div>
                <div class="player-2">
                    <div class="player-name cell">
                        <c:out value="${match.player2.name}" />
                    </div>
                    <div class="points cell">
                        <c:out value="${match.gameStateSecondPlayer.score}" />
                    </div>
                    <div class="games cell">${match.currentGames.second}</div>
                    <div class="sets cell">
                        <c:forEach var="set" items="${match.sets}">
                            <span class="set-score">${set.second}</span>
                        </c:forEach>
                    </div>
                </div>


                <c:choose>
                    <c:when test="${!match.endMatch}">
                        <div class="footer">
                            <form class="winner-1" method="POST" action="/match-score?uuid=${uuid}">
                                <input type="hidden" name="winner" value="First">
                                <button class="button points cell" type="submit">Игрок <b>${match.player1.name}</b> выиграл
                                    очко</button>
                            </form>
                            <form class="winner-2" method="POST" action="/match-score?uuid=${uuid}">
                                <input type="hidden" name="winner" value="Second">
                                <button class="button points cell" type="submit">Игрок <b>${match.player2.name}</b> выиграл
                                    очко</button>
                            </form>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="footer">
                            <div class="cell">Победил игрок <b>${match.winner.name}</b>!!!</div>
                            <div class="button"><a href="/new-match">Новый матч</a></div>
                            <div class="button"><a href="/matches">Завершенные матчи</a></div>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </body>

        </html>