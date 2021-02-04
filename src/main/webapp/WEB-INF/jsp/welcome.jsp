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
            var deck = [1, 2, 3, 4, 5, 5, 7, 7, 9, 11, 11, 13, 14, 15, 17, "H1", "H2", "H3", "H4", "H5", "A1", "A2", "A3", "A4", "A5"];

            function newCard(i) {
                alert("New card: "+i+" ("+deck[i]+")");
            }

            function card(i, x, y) {
                var svgCard = '';
                var strokeColor;
                if (i < 15) {
                    strokeColor = "black";
                } else if (i >= 15 && i <= 19) {
                    strokeColor = "red";
                } else if (i >= 20 && i <= 24) {
                    strokeColor = "orange";
                }
                svgCard += '<g fill="white" stroke="'+strokeColor+'" stroke-width="1" onclick="newCard('+i+')">';
                svgCard += '<rect x="'+x+'" y="'+y+'" width="30" height="40" />';
                svgCard += '<text x="'+(x+5)+'" y="'+(y+20)+'">'+deck[i]+'</text>';
                svgCard += '</g>';
                return svgCard;
            }

            function loaded() {
                var allSvgCode = "";
                for (var i = 0; i < deck.length; i++) {
                    allSvgCode += card(i, i*32+5, 25);
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