<%@ page language="java" contentType="application/javascript; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

// jsp
    function renderPlayer(player) {
        var pHtml = '<div class="card mb">';
        pHtml += '<div class="card-header"><h4 class="my-0">'+player+'</h4></div>';
        pHtml += '<div class="card-body"><ul class="list-unstyled"><li>Gems: 0</li><li>Artifacts: 0</li></ul></div>';
        pHtml += '<div class="card-footer"><button class="btn btn-secondary btn-lg block" onclick="removePlayer(\''+player+'\')">Remove</button></div>';
        pHtml += '</div>';
        return pHtml;
    }

    function renderPlayers() {
        var playerHtml = "";

        if (playerNames.length > 0) {
            for (var i = 0; i < playerNames.length; i++) {
                playerHtml += renderPlayer(playerNames[i]);
            }
        }

        var playerEle = document.getElementById("player_deck");
        playerEle.innerHTML = playerHtml;
    }