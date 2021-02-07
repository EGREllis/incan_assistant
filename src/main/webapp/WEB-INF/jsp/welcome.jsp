<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!--
// Rules: https://boardgamegeek.com/thread/401937/so-youre-wondering-about-new-gryphon-games-edition
// Temple gray: https://boardgamegeek.com/image/222278/diamant
// Temple coloured: https://boardgamegeek.com/image/222277/diamant
// Player cards: https://boardgamegeek.com/image/222678/diamant
-->

<!DOCTYPE html>

<html lang="en">
    <head>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW" crossorigin="anonymous"></script>

        <link rel="stylesheet" href="main.css" />
        <script src="main.js"></script>
        <script>
            var cards =     [1, 2, 3, 4, 5, 7, 9, 11, 13, 14, 15, 17, "H1", "H2", "H3", "H4", "H5", "A1", "A2", "A3", "A4", "A5"];
            var deck =      [0, 1, 2, 3, 4, 5, 6, 7,  8,  9,  10, 11, 12,   13,   14,   15,   16,   17,   18,   19,   20,   21];
            var quantity =  [1, 1, 1, 1, 2, 2, 1, 2,  1,  1,  1,  1,  3,    3,    3,    3,    3,    1,    1,    1,    1,    1];
            var cardType =  [1, 1, 1, 1, 1, 1, 1, 1,  1,  1,  1,  1,  2,    2,    2,    2,    2,    3,    3,    3,    3,    3];
            var tableau =   [];

            function nDistinctCards() {
                var sum = 0;
                for (var i = 0; i < quantity.length; i++) {
                    if (quantity[i] > 0) {
                        sum++;
                    }
                }
                return sum;
            }

            function addCardToTableau(i) {
                if (quantity[i] >= 0) {
                    quantity[i] -= 1;
                    tableau[tableau.length] = deck[i];
                }
                loaded();
            }

            function removeCardFromTableau(i) {
                var tableauCard = tableau[i];
                for (var deckIndex = 0; deckIndex < deck.length; deckIndex++) {
                    if (deck[deckIndex] == tableauCard) {
                        quantity[deckIndex] += 1;
                        if (tableau.length == 1) {
                            tableau = [];
                        } else {
                            tableau = tableau.slice(0, i).concat(tableau.slice(i+1, tableau.length));
                        }
                        break;
                    }
                }
                loaded();
            }

            function svgBackground() {
                var svgCode = '<g fill="white" stroke="black" stroke-width="1">';
                svgCode += '<rect x="5" y="5" width="990" height="90" />';
                svgCode += '<text x="450" y="25">Card selector</text>';
                svgCode += '</g>';
                svgCode += '<g fill="brown" stroke="black" stroke-width="1">';
                svgCode += '<rect x="5" y="100" width="990" height="70" />';
                svgCode += '<text x="450" y="120">Tableau</text>';
                svgCode += '</g>';
                return svgCode;
            }

            function getCardTypeStrokeColor(cardIndex) {
                var strokeColor;
                if (cardType[cardIndex] == 1) {
                    strokeColor = "black";
                } else if (cardType[cardIndex] == 2) {
                    strokeColor = "red";
                } else if (cardType[cardIndex] == 3) {
                    strokeColor = "orange";
                }
                return strokeColor;
            }

            function calcCardGeometry() {
                var nCards = nDistinctCards();
                var cardWidth = 990 - 2 * nCards;
                var unitCardWidth = Math.floor(cardWidth / nCards);
                var cardOffset = Math.floor(((unitCardWidth * nCards) - cardWidth) / 2);
                return [unitCardWidth, cardOffset];
            }

            function card(cardIndex, i, x, y, quant, func) {
                var svgCard = '';
                var strokeColor = getCardTypeStrokeColor(cardIndex);

                for (var q = quant; q > 0; q--) {
                    var cardX = x;
                    var cardY = y + (q-1) * 10;
                    svgCard += '<g fill="white" stroke="'+strokeColor+'" stroke-width="1" onclick="'+func+'('+i+')">';
                    svgCard += '<rect x="'+cardX+'" y="'+cardY+'" width="36" height="40" />';
                    svgCard += '<text x="'+(cardX+5)+'" y="'+(cardY+20)+'">'+cards[cardIndex]+'</text>';
                    svgCard += '</g>';
                }
                return svgCard;
            }

            function drawCardSelectorCards() {
                var selectorSvg = "";

                for (var i = 0; i < deck.length; i++) {
                    if (quantity[i] > 0) {
                        var x = (i*44)+17;
                        selectorSvg += card(deck[i], i, x, 30, quantity[deck[i]], "addCardToTableau");
                    }
                }
                return selectorSvg;
            }

            function drawTableauCards() {
                var tableauSvg = "";

                for (var i = 0; i < tableau.length; i++) {
                    var x = (i * 40) + 10;
                    tableauSvg += card(tableau[i], i, x, 125, 1, "removeCardFromTableau");
                }
                return tableauSvg;
            }

            function loaded() {
                var allSvgCode = svgBackground();
                allSvgCode += drawCardSelectorCards();
                allSvgCode += drawTableauCards();

                var ele = document.getElementById("svgBox");
                ele.innerHTML = allSvgCode;
            }
        </script>
    </head>
    <body onload="loaded()">
        <div class="incan-header px-3 py-3 pt-md-5 pd-md-4 mx-auto text-center">
            <h1 class="display-4">Incan Gold Assistant</h1>
            <p class="lead">Get an edge on your opponents!</p>
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1000 200" style="width:100%; height:100%" id="svgBox">
            </svg>
        </div>
        <form method="POST" id="">
            <div class="container">
                <div class="card-deck text-center">
                    <div class="card">
                        <div class="card-header">
                            <p class="card-text">Player name 1</p>
                        </div>
                        <div class="card-body">
                            <ul>
                                <li>Excavate</li>
                                <li>Withdraw</li>
                            </ul>
                        </div>
                    </div>
                    <div class="card">
                        <div class="card-header">
                            <p class="card-text">Player name 2</p>
                        </div>
                        <div class="card-body">
                            <ul>
                                <li>Excavate</li>
                                <li>Withdraw</li>
                            </ul>
                        </div>
                    </div>
                    <div class="card">
                        <div class="card-header">
                            <p class="card-text">Player name 3</p>
                        </div>
                        <div class="card-body">
                            <ul>
                                <li>Excavate</li>
                                <li>Withdraw</li>
                            </ul>
                        </div>
                    </div>
                    <div class="card">
                        <div class="card-header">
                            <p class="card-text">Player name 4</p>
                        </div>
                        <div class="card-body">
                            <ul>
                                <li>Excavate</li>
                                <li>Withdraw</li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </body>
</html>