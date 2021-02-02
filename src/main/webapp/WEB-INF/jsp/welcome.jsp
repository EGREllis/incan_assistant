<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

// Rules: https://boardgamegeek.com/thread/401937/so-youre-wondering-about-new-gryphon-games-edition
// Temple gray: https://boardgamegeek.com/image/222278/diamant
// Temple coloured: https://boardgamegeek.com/image/222277/diamant
// Player cards: https://boardgamegeek.com/image/222678/diamant

<!DOCTYPE html>

<html lang="en">
    <head>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW" crossorigin="anonymous"></script>

        <link rel="stylesheet" href="main.css" />
        <script src="main.js"></script>
    </head>
    <body>
        <table>
            <tr><td></td><td colspan="2">Temple 5</td><td></td><td rowspan="3">Card 1</td><td rowspan="3">Card 2</td></tr>
            <tr><td colspan="2">Temple 3</td><td colspan="2">Temple 4</td></tr>
            <tr><td colspan="2">Temple 1</td><td colspan="2">Temple 2</td></tr>
        </table>
        <nav class="col-md-2 d-none d-md-block bg-light sidebar">
            <div class="sidebar-sticky">
                <ul class="nav flex-column" >
                    <li class="nav-item">
                        <span>New game</span>
                        <span data-feather="plus-circle"></span>
                    </li>
                </ul>
            </div>
        </nav>
        <main role="main">
            <div class="container">
                <form>
                    <div class="card-deck mb-3 text-centre">
                        <div class="card mb-4 box-shadow">
                            <div class="card-header">
                                <h4 class="my-0 font-weight-normal">Your decision</h4>
                            </div>
                            <div class="card-body">
                                <input type="radio" id="my_excavate" name="my_action" value="excavate" />
                                <label for="my_excavate">Excavate</label><br />

                                <input type="radio" id="my_withdraw" name="my_action" value="withdraw" />
                                <label for="my_withdraw">Withdraw</label>
                            </div>
                        </div>
                        <div class="card mb-4 box-shadow">
                            <div class="card-header">
                                <h4 class="my-0 font-weight-normal">Another players decision</h4>
                            </div>
                            <div class="card-body">
                                <input type="radio" id="other_excavate" name="other_action" value="excavate" />
                                <label for="other_exercise">Excavate</label><br />

                                <input type="radio" id="other_withdraw" name="other_action" value="withdraw" />
                                <label for="other_withdraw">Withdraw</label>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </main>

    <!--
	<c:url value="/resources/text.txt" var="url"/>
	<spring:url value="/resources/text.txt" htmlEscape="true" var="springUrl" />
	Spring URL: ${springUrl} at ${time}
	<br>
	JSTL URL: ${url}
	<br>
	Message: ${message}
	Name: ${name}
	-->
    </body>

</html>