<!doctype html>
<html>
    <head>
        <title>Bootstrap cards</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <!--
            <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
        -->

        <script>
            var playerNames = [];

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

            function addPlayer() {
                var newPlayerEle = document.getElementById("new_player_name");
                playerNames[playerNames.length] = newPlayerEle.value;
                renderPlayers();
            }

            function removePlayer(oldPlayerName) {
                var removePlayerIndex = playerNames.indexOf(oldPlayerName);
                if (removePlayerIndex >= 0) {
                    var prePlayers = playerNames.slice(0, removePlayerIndex);
                    var postPlayers = playerNames.slice(removePlayerIndex+1, playerNames.length);
                    var removedPlayerName = prePlayers.concat(postPlayers);
                    playerNames = removedPlayerName;
                }
                renderPlayers();
            }
        </script>
    </head>
    <body>
        <main role="main">
            <form method="GET" onsubmit="return false">
                <div class="container">
                    <section class="jumbotron p-3 p-md-5 text-white rounded bg-dark text-center">
                        <h1 class="jumbotron-heading">Compose players</h1>
                        <label for="new_player_name" class="sr-only">New player name</label>
                        <input type="text" id="new_player_name" class="form-control" placeholder="Player name" autofocus>
                        <button class="btn btn-primary btn-lg block" onclick="addPlayer()" id="addPlayerButton">Add Player</button>
                    </section>

                    <div class="card-deck mb-3 text-center" id="player_deck">
                    </div>
                </div>
            </form>
        </main>
    </body>
</html>