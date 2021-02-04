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
            var deck =      [1, 2, 3, 4, 5, 7, 9, 11, 13, 14, 15, 17, "H1", "H2", "H3", "H4", "H5", "A1", "A2", "A3", "A4", "A5"];
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
                    tableau[tableau.size] = i;
                }
                loaded();
            }

            function removeCardFromTableau(i) {

            }

            function svgBackground() {
                var svgCode = '<g fill="white" stroke="black" stroke-width="1">';
                svgCode += '<rect x="5" y="5" width="990" height="70" />';
                svgCode += '<text x="450" y="25">Card selector</text>';
                svgCode += '</g>';
                svgCode += '<g fill="brown" stroke="black" stroke-width="1">';
                svgCode += '<rect x="5" y="80" width="990" height="70" />';
                svgCode += '<text x="450" y="100">Tableau</text>';
                svgCode += '</g>';
                return svgCode;
            }

            function card(i, x, y) {
                var svgCard = '';
                var strokeColor;
                if (cardType[i] == 1) {
                    strokeColor = "black";
                } else if (cardType[i] == 2) {
                    strokeColor = "red";
                } else if (cardType[i] == 3) {
                    strokeColor = "orange";
                }
                svgCard += '<g fill="white" stroke="'+strokeColor+'" stroke-width="1" onclick="addCardToTableau('+i+')">';
                svgCard += '<rect x="'+x+'" y="'+y+'" width="30" height="40" />';
                svgCard += '<text x="'+(x+5)+'" y="'+(y+20)+'">'+deck[i]+'</text>';
                svgCard += '</g>';
                return svgCard;
            }

            function loaded() {
                var allSvgCode = svgBackground();
                var nCards = nDistinctCards();
                var cardWidth = 990 - 2 * nCards;
                var unitCardWidth = Math.floor(cardWidth / nCards);
                var cardOffset = Math.floor(((unitCardWidth * nCards) - cardWidth) / 2);

                alert("X: "+x+" UnitCardWidth: "+unitCardWidth+" CardOffset: "+cardOffset+ " (cardWidth "+cardWidth+" nCards "+nCards+" unitCardWidth "+unitCardWidth+")" );

                var drawn = 0;
                for (var i = 0; i < deck.length; i++) {
                    if (quantity[i] > 0) {
                        var x = (drawn*unitCardWidth)+(10+cardOffset);
                        allSvgCode += card(i, x, 30);
                        drawn++;
                    }
                }

                var ele = document.getElementById("svgBox");
                ele.innerHTML = allSvgCode;
            }
        </script>
    </head>
    <body onload="loaded()">
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1000 1000" style="width:100%; height:100%" id="svgBox">
        </svg>
    </body>
</html>