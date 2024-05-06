<p align="center">
  <img src="https://raw.githubusercontent.com/lizocf/ECE-366-Duopoly/dev/Dupoly-Maven/duopoly/src/images/DUOPOLY_logo_bg_cropped.png">
</p>


# ECE-366: Software Engineering (Spring 2024)
_Developed by Lizelle Ocfemia, Isaiah Rivera, and Nicholas Storniolo. \
Created under the instruction of Professor Christopher Hong at the Cooper Union during the Spring 2024 semester._


## Project Overview

Welcome to Duopoly!
Duopoly takes the timeless excitement of the classic board game Monopoly and adds a thrilling twist, inviting players into a world where strategy meets chance on a uniquely designed hexagonal board.

### Game Rules
There can be 2-8 players in each Duopoly game and follows the same standard rules as Monopoly: the last player who isn't bankrupt wins! However, the players are not allowed to trade with any of the other players and you can't build any hotels. (You get what you get and you don't get upset!) The turn order is based on whoever enters the lobby first and at the beginning of the game, players get to choose which direction to traverse.

The goal is simple yet daunting: outlast your opponents. Drive them into bankruptcy or drain their funds until you’re the last one standing with cash in hand.
Are you ready to take on the challenge and dominate in the high-stakes world of Duopoly? Grab your friends and make your move. The board awaits you!

### New Features
What sets Duopoly apart? It's the exhilarating new features that enhance the gameplay:

- **SKULL** Cards: Draw one and feel the shiver as unpredictable challenges or rewards are thrown your way.
- **SURPRISE** Cards; Draw one and get... a surprise! Who knows what could happen!?
- **DEBT** Space: Every time a player passes DEBT space, they lose a bit of cash. If you land on it, you get all the money collected!
- **EVICTION** Space: A dreaded spot that will strip you of one of your properties.
- **REVERSE** Space: Just when you think you’ve planned it all, find yourself moving in the opposite direction!

<p align="center">
  <img src="https://raw.githubusercontent.com/lizocf/ECE-366-Duopoly/dev/Dupoly-Maven/duopoly/src/images/board_updated.png" width="50%">
</p>

## Requirements
To build and run this project, the following are required:
* [Docker Compose](https://docs.docker.com/compose/install/linux/)
* [React](https://react.dev/)
* [postgreSQL](https://www.postgresql.org/)


## Getting Started

Running Duopoly locally is simple thanks to Docker!

    cd Dupoly-Maven
    docker compose build // wait for containers to build..
    docker compose up // bring up components!
    
Once docker is running in the background, simply type ``localhost:3000`` in your browser and start playing!

## Project structure

Duopoly has three main components: the database, the front end, and the back end. These components are structured below.

    /Dupoly-Maven                 
        --> /database             ## Contains the initialization SQL files for the database as well as util scripts
        --> /duopoly              ## Contains the bulk of UI-related code
              --> /components     # Javascript for functionality
              --> /images         # Asset images for the game
              --> app.js          # Where all components are rendered
              --> server.js       # Is run to maintain sessions across tabs
              --> style.css
              ...
        --> /src/main/java        ## Contains the bulk of backend logic
              --> /game           # Springboot commands
              --> /jdbc           # Backend DAOs and Utils
              ...
        --> docker-compose.yml    ## List of all containers + ports that are brought up upon start

## Code of Ethics

Computers play a crucial and expanding role across various sectors including commerce, industry, government, healthcare, education, entertainment, and broader society. Given their significant influence in shaping these systems, software engineers have substantial potential to do good or cause harm, empower others to do the same, or impact others’ abilities to do so. To maximize the likelihood that their work promotes positive outcomes, we, as fletching software engineers, must dedicate ourselves to making the field a beneficial and respected one. As part of this commitment, we must follow the defined [Code of Ethics and Professional Practice]([url](https://www.computer.org/education/code-of-ethics)). Here are ways we have done so:

_**4.01.** Temper all technical judgments by the need to support and maintain human values._ \
_**4.03.** Maintain professional objectivity with respect to any software or related documents they are asked to evaluate._\
_**5.04.** Assign work only after taking into account appropriate contributions of education and experience tempered with a desire to further that education and experience._\
_**6.08.** Take responsibility for detecting, correcting, and reporting errors in software and associated documents on which they work._ \
_**7.01.** Encourage colleagues to adhere to this Code._ \
_**7.02.** Assist colleagues in professional development._ \
_**7.03.** Credit fully the work of others and refrain from taking undue credit._ \
_**7.04.** Review the work of others in an objective, candid, and properly-documented way._ \
_**7.05.** Give a fair hearing to the opinions, concerns, or complaints of a colleague._ \
_**8.01**. Further their knowledge of developments in the analysis, specification, design, development, maintenance and testing of software and related documents, together with the management of the development process._ \
_**8.02.** Improve their ability to create safe, reliable, and useful quality software at reasonable cost and within a reasonable time._ \
**8.03.** Improve their ability to produce accurate, informative, and well-written documentation._ \
_**8.04.** Improve their understanding of the software and related documents on which they work and of the environment in which they will be used._
