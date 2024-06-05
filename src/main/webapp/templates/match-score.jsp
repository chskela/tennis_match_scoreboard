<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tennis Scoreboard</title>
    <style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f0f0f0;
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
        margin: 0;
    }

    .scoreboard {
        background-color: #fff;
        border: 2px solid #000;
        border-radius: 10px;
        padding: 20px;
        width: 600px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    }

    .header, .player {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 10px;
    }

    .header {
        font-weight: bold;
        font-size: 18px;
    }

    .cell {
        flex: 1;
        text-align: center;
        border: 1px solid #000;
        padding: 10px;
        margin: 0 5px;
        border-radius: 5px;
        background-color: #eaeaea;
    }

    .player-name {
        background-color: #d4edda;
        font-size: 20px;
        font-weight: bold;
    }

    .points, .games {
        font-size: 18px;
        background-color: #fff3cd;
    }

    .sets {
        display: flex;
        justify-content: center;
    }

    .set-score {
        margin: 0 5px;
        font-size: 18px;
        font-weight: bold;
        border: 1px solid #000;
        padding: 5px;
        border-radius: 5px;
        background-color: #d1ecf1;
    }

    </style>
</head>
<body>
    <div class="scoreboard">
        <c:set var="match" value="${requestScope.match}" scope="request"/>
        <div class="header">
            <div class="cell">
                <a href="/new-match">Новый матч</a>
            </div>
            <div class="cell">Points</div>
            <div class="cell">Games</div>
            <div class="cell">Sets</div>
        </div>
        <div class="player">
            <div class="player-name cell"><c:out value="${match.player1.name}"/></div>
            <div class="points cell"><c:out value="${match.gameStateFirstPlayer.score}"/></div>
            <div class="games cell">${match.currentGames.first}</div>
            <div class="sets cell">
                <c:forEach var="set" items="${match.sets}">
                    <span class="set-score">${set.first}</span>
                </c:forEach>
            </div>
        </div>
        <div class="player">
            <div class="player-name cell"><c:out value="${match.player2.name}"/></div>
            <div class="points cell"><c:out value="${match.gameStateSecondPlayer.score}"/></div>
            <div class="games cell">${match.currentGames.second}</div>
            <div class="sets cell">
                <c:forEach var="set" items="${match.sets}">
                    <span class="set-score">${set.second}</span>
                </c:forEach>
            </div>
        </div>
        <div class="player">
            <form  method="POST" action="/match-score?uuid=${uuid}">
                <input type="hidden" name="winner" value="First">
                <button class="points cell" type="submit">Игрок ${match.player1.name} выиграл очко</button>
            </form>
            <form  method="POST" action="/match-score?uuid=${uuid}">
                <input type="hidden" name="winner" value="Second">
                <button class="points cell" type="submit">Игрок ${match.player2.name} выиграл очко</button>
            </form>
        </div>
    </div>
</body>
</html>
