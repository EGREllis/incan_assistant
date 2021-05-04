<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!doctype html>
<html>
    <head>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <!-- <link rel="stylesheet" href="main.css" /> -->
        <script src="main.js"></script>

        <script>
            var serverData = '<c:out value="${players}" />';

            var playerNames = "";
            var round = 1;

            var cards =     [1, 2, 3, 4, 5, 7, 9, 11, 13, 14, 15, 17, "H1", "H2", "H3", "H4", "H5", "A1", "A2", "A3", "A4", "A5"];
            var deck =      [0, 1, 2, 3, 4, 5, 6, 7,  8,  9,  10, 11, 12,   13,   14,   15,   16,   17,   18,   19,   20,   21];
            var quantity =  [1, 1, 1, 1, 2, 2, 1, 2,  1,  1,  1,  1,  3,    3,    3,    3,    3,    1,    1,    1,    1,    1];
            var cardType =  [1, 1, 1, 1, 1, 1, 1, 1,  1,  1,  1,  1,  2,    2,    2,    2,    2,    3,    3,    3,    3,    3];
            var tableau =   [];
            var gems =      [];
            var playerGems= [];
            var playerMode= [];
            var playerArtifacts = [];
            var playerSavedGems = [];
            var playerSavedArtifacts = [];

            function isGemCard(i) {
                return cardType[i] == 1;
            }

            function isHazardCard(i) {
                return cardType[i] == 2;
            }

            function isArtifactCard(i) {
                return cardType[i] == 3;
            }

            function renderRound() {
                var roundEle = document.getElementById("roundTitle");
                var roundHtml = "<h2>Round "+round+"</h2>";
                roundEle.innerHTML = roundHtml;
            }

            function addCardToTableau(cardIndex) {
                var excavating = getPlayersThatAreExcavating();
                var withdrawing = getPlayersThatAreWithdrawn();

                var card = cards[i];
                var gemRemaining = 0;
                if (isGemCard(i)) {
                    var gemSplit = Math.floor(card / excavating.length);
                    gemRemaining = card - (gemSplit * excavating.length);
                    for (var j = 0; j < excavating.length; j++) {
                        playerGems[excavating[j]] += gemSplit;
                    }
                } else if (isArtifactCard(i)) {
                    // Do nothing special
                } else if (isHazardCard(i)) {
                    if (tableau.indexOf(i) >= 0) {
                        // This is the second hazard - the round is over
                        for (var j = 0; j < excavating.length; j++) {
                            playerGems[excavating[j]] = 0;
                            playerGems
                        }
                    }
                }

                quantity[cardIndex] -= 1;
                tableau[tableau.length] = cardIndex;
                gems[gems.length] = gemRemaining;

                render();
            }

            function getPlayersThatAreWithdrawn() {
                var withdrawn = [];
                for (var i = 0; i < playerMode.length; i++) {
                    if (playerMode[i] == "W") {
                        withdrawn[withdrawn.length] = i;
                    }
                }
                return withdrawn;
            }

            function processSuccessfulWithdraw(withdrawn) {
                if (withdrawn.length == 0) {
                    // Do nothing
                } else if (withdrawn.length == 1) {
                    // Award artifacts

                } else {
                    // No artifacts
                }
            }

            function getPlayersThatAreExcavating() {
                var excavate = [];
                for (var i = 0; i < playerMode.length; i++) {
                    if (playerMode[i] == "E") {
                        excavate[excavate.length] = i;
                    }
                }
                return excavate;
            }

            function processSuccessfulExcavate(excavate) {
            }

            function processFailedExcavate(excavate) {
            }

            function togglePlayer(playerIndex) {
                if (playerMode[playerIndex] == "E") {
                    playerMode[playerIndex] = "W";
                } else {
                    playerMode[playerIndex] = "E";
                }
                renderPlayerInfo();
            }

            function renderPlayerInfo() {
                var playerHtml = "";
                var imageSvg = "";

                for (var i = 0; i < playerNames.length; i++) {
                    if (playerMode[i] == "E") {
                        imageSvg = "<svg viewBox='0 0 100 100' onclick='togglePlayer("+i+")'><circle cx='50' cy='30' r='15' stroke='black' fill='white' /><line x1='50' y1='45' x2='50' y2='75' stroke='black'/><line x1='65' y1='50' x2='35' y2='50' stroke='black' /><line x1='50' y1='75' x2='35' y2='90' stroke='black' /><line x1='50' y1='75' x2='65' y2='90' stroke='black' /><line x1='90' y1='50' x2='70' y2='50' stroke='black'/><line x1='90' y1='50' x2='80' y2='40' stroke='black' /><line x1='90' y1='50' x2='80' y2='60' stroke='black' /></svg>";
                    } else {
                        imageSvg = "<svg viewBox='0 0 100 100' onclick='togglePlayer("+i+")'><circle cx='50' cy='30' r='15' stroke='black' fill='white' /><line x1='50' y1='45' x2='50' y2='75' stroke='black'/><line x1='65' y1='50' x2='35' y2='50' stroke='black' /><line x1='50' y1='75' x2='35' y2='90' stroke='black' /><line x1='50' y1='75' x2='65' y2='90' stroke='black' /><line x1='10' y1='50' x2='30' y2='50' stroke='black'/><line x1='10' y1='50' x2='20' y2='40' stroke='black' /><line x1='10' y1='50' x2='20' y2='60' stroke='black' /></svg>";
                    }
                    playerHtml += '<div class="card"><div class="card-header"><p class="card-text">'+playerNames[i]+'</p></div><div class="card-body">'+imageSvg+'</div><div class="card-footer"><p>Gems: '+playerGems[i]+'</p></div></div>';
                }

                var playerInfoEle = document.getElementById("player-info");
                playerInfoEle.innerHTML = playerHtml;
            }

            function renderDeck() {
                var deckHtml = "<table class='table text-center'><thead class='thead-dark'><tr><th scope='col' colspan='"+(cards.length+1)+"'>Deck</th></tr></thead><tbody><tr><th scope='row'>Card</th>";
                var quant = "<tr><th scope='row'>Quantity</th>";
                for (var i = 0; i < cards.length; i++) {
                    var disabledHtml = "";
                    if (quantity[i] <= 0) {
                        disabledHtml = "disabled";
                    }
                    deckHtml += "<td><button class='btn btn-sm btn-secondary block' onclick='addCardToTableau("+i+")' "+disabledHtml+">"+cards[i]+"</button></td>"
                    quant += "<td>"+quantity[i]+"</td>";
                }
                quant += "</tr>";
                deckHtml += "</tr>" + quant + "</tbody></table>";

                var deckEle = document.getElementById("deck");
                deckEle.innerHTML = deckHtml;
            }

            function renderTableauSvg() {
                var tableauSvg = "<svg viewBox='0 0 1000 100'>";
                for (var i = 0; i < tableau.length; i++) {
                    var x1 = 5 + i * 35;
                    var textX = x1 + 5;
                    tableauSvg += "<rect x='"+x1+"' y='5' width='30' height='90' stroke='black' fill='white' stroke-width='1' />";
                    tableauSvg += "<text x='"+textX+"' y='30'>"+cards[tableau[i]]+"</text>";
                }
                tableauSvg += "</svg>";

                tableauSvgEle = document.getElementById("tableauSvg");
                tableauSvgEle.innerHTML = tableauSvg;
            }

            function render() {
                renderRound();
                renderDeck();
                renderTableauSvg();
                renderPlayerInfo();
            }

            function unpack() {
                var decoded = serverData.replaceAll("&#034;", '"');
                eval("serverData="+decoded);

                playerNames = serverData;
                for (var i = 0; i < playerNames.length; i++) {
                    playerGems[i] = 0;
                    playerMode[i] = "E";
                }
                render();
            }
        </script>
    </head>
    <body onload="unpack()">
        <main role="main">
            <div class="container-fluid">
                <div class="lead text-center" id="roundTitle"><h2>Round 1</h2></div>
                <div class="row">
                    <div class="col">
                        <div id="deck"></div>
                    </div>
                </div>
                <div class="row">
                    <div class="col">
                        <div id="tableauSvg"></div>
                    </div>
                </div>
                <div class="card-deck" id="player-info">
                </div>
            </div>
        </main>
    </body>
</html>
