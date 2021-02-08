<!doctype html>
<html>
    <head>
        <title>Compose players</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

        <script>
            var playerNames = [];

            function playerNamesToJson() {
                var json = "[";
                for (var i = 0; i < playerNames.length; i++) {
                    if (i > 0) {
                        json += ",";
                    }
                    json += '"'+playerNames[i]+'"';
                }
                json += "]";
                return json;
            }

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

                var startGameButtonEle = document.getElementById("startGameButton");
                if (playerNames.length >= 3 && playerNames.length <= 5) {
                    startGameButton.disabled = false;
                } else {
                    startGameButton.disabled = true;
                }

                var addPlayerButtonEle = document.getElementById("addPlayerButton");
                if (playerNames.length < 5) {
                    addPlayerButtonEle.disabled = false;
                } else {
                    addPlayerButtonEle.disabled = true;
                }
            }

            function addPlayer() {
                var newPlayerEle = document.getElementById("new_player_name");
                if (playerNames.indexOf(newPlayerEle.value) >= 0) {
                    newPlayerEle.value = "";
                    newPlayerEle.focus();
                } else if (newPlayerEle.value.length > 0) {
                    playerNames[playerNames.length] = newPlayerEle.value;
                    newPlayerEle.value = "";
                    newPlayerEle.focus();
                    playerNames.sort();
                    renderPlayers();
                }
            }

            function removePlayer(oldPlayerName) {
                var removePlayerIndex = playerNames.indexOf(oldPlayerName);
                if (removePlayerIndex >= 0) {
                    var prePlayers = playerNames.slice(0, removePlayerIndex);
                    var postPlayers = playerNames.slice(removePlayerIndex+1, playerNames.length);
                    var removedPlayerName = prePlayers.concat(postPlayers);
                    playerNames = removedPlayerName;
                    var newPlayerEle = document.getElementById("new_player_name");
                    newPlayerEle.focus();
                }
                renderPlayers();
            }

            function assistGame() {
                var playerJson = playerNamesToJson();
                var hiddenEle = document.getElementById("players-json");
                hiddenEle.value = playerJson;

                // Make the form submittable, then submit it.
                var formEle = document.getElementById("players_form");
                formEle.onsubmit = function () { return true; }
                formEle.submit();
            }
        </script>
    </head>
    <body>
        <main role="main">
            <form method="POST" action="new_assist" id="players_form" onsubmit="return false">
                <div class="container">
                    <section class="jumbotron p-3 p-md-5 text-white rounded bg-dark text-center">
                        <h1 class="jumbotron-heading">Compose players</h1>
                        <p class="lead">Incan gold is a three to five player game.</p>
                        <label for="new_player_name" class="sr-only">New player name</label>
                        <input type="text" id="new_player_name" class="form-control mb-3" placeholder="Player name" autofocus>
                        <button class="btn btn-primary btn-lg block" onclick="addPlayer()" id="addPlayerButton">Add player</button>
                        <button class="btn btn-primary btn-lg block" onclick="assistGame()" id="startGameButton" disabled>Assist game</button>
                        <input type="hidden" name="players" id="players-json" value="" />
                    </section>

                    <div class="card-deck mb-3 text-center" id="player_deck">
                    </div>
                </div>
            </form>
        </main>
    </body>
</html>