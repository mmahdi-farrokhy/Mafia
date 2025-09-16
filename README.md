# MAFIA GAME
## Minimum number of players: 5

|Players Count|Citizens Count|Mafias Count |
|:-------------:|:-------------:|:-------------:|
|5|3 (Doctor + Detective + Simple Citizen)|2 (Don + Simple Mafia)|
|6|4 (Doctor + Detective + 2 Simple Citizen)|2 (Don + Simple Mafia)|
|7|5 (Doctor + Detective + 3 Simple Citizen)|2 (Don + Simple Mafia)|
|8|5 (Doctor + Detective + 3 Simple Citizen)|3 (Don + 2 Simple Mafia)|
|9|6 (Doctor + Detective + 4 Simple Citizen)|3 (Don + 2 Simple Mafia)|
|10|7 (Doctor + Detective + 5 Simple Citizen)|3 (Don + 2 Simple Mafia)|
|11|8 (Doctor + Detective + 8 Simple Citizen)|3 (Don + 2 Simple Mafia)|
|12|8 (Doctor + Detective + 6 Simple Citizen)|4 (Don + 3 Simple Mafia)|

## Game Play
- Set number of players
- Enter players names
- Start the game
- Assign the roles
- All players sleep
- Mafias wake up
- Don shows his like
- Mafias consult 60 seconds
- Mafias sleep
- All players wake up
- Day: The player that was killed in night will be introduced. If the number of remaining mafias is equal to or more than remaining citizens, mafia wins the game. If there is 2 citizens and 1 mafia left in the deck, the chaos phase begins. The remaining 3 players talk for 120 seconds, then each one will choose a citizen; if the chosen player is the mafia, mafia wins the game. Otherwise, the chosen citizen will choose a player to deal with. If he deals with the citizen, city wins the game. Otherwise mafia wins the game.
    - Each player talks 90 seconds and has a 60 seconds challenge.
    - Vote will take place at the end of the day
    - Each player with vote more than half of remaining players goes to defense
        - If there is only one player in defense, he exits the game with the majority vote.
        - If there are multiple players in defense, the one with the more votes exits the game
    - Each player can vote only one person in defense
    - Players in defense can not vote each other

- Night: If there is no mafia left in the game at the beginning of the night, city wins the game.
    - Mafias wake up and shoots one player. They have one minute to shoot and consult.
    - Mafias sleep.
    - Doctor wakes up and saves one player.
    - Doctor sleeps.
    - Detective wakes up and investigates a player, if the player is citizen or Don the detective gets No, otherwise gets a yes and identifies a simple mafia.
    - Detective sleeps.
    - End of night, new day starts and all players wake up.