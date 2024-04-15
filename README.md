
![Image](https://raw.githubusercontent.com/lizocf/ECE-366-Duopoly/dev/Dupoly-Maven/duopoly/src/images/DUOPOLY_logo_bg.png)
# Duopoly

## ECE-366: Software Eng. Project

Created under the instruction of Professor Christopher Hong at the Cooper Union during the Spring 2024 semester.

Developed by Lizelle Ocfemia, Isaiah Rivera, and Nicholas Storniolo
    

# Project Overview

### Game Rules
Duopoly, a spinoff from the classic board game Monopoly. 
In Duopoly, players start off with $2000 and get to decide whether to move left or right of the START space around the hexagonal board at the beginning of the game. 
New features such as SKULL cards, DEBT space, EVICTION space, and the REVERSE space are the core features of Duopoly. 
A player wins once everyone else runs out of money or goes bankrupt. 

### The board

 
## Running Duopoly
In order to build and run this project the following __dependencies__ are required:
* Docker Compose
  * [Installation information on Docker Compose](https://docs.docker.com/compose/install/linux/)
* npm
* psql

### Post-setup run instructions:
*The database must be initialized with players/general game information first!!* 
See 'First Run Instructions:' below

    cd Dupoly-Maven
    docker compose up app
    
A

    cd Dupoly-Maven/duopoly
    npm start

### First run instructions:

A local version of the project database must be initialized for local testing.
For these purposes running /database/create.sh within the database container will
initialize it. The following outlines basic command line interaction with these utilities:


    To run/setup psql:
    docker compose build
    docker compose up -d
    docker exec -it <container-name> bash
    psql -h localhost -U postgres
    // CREATE DATABASE <database_name>; GRANT ALL PRIVILEGES ON DATABASE <database_name> TO postgres;
    how to run SQL files:
    psql -h localhost -U postgres -d <database_name> -f <file.sql>
    
    populating initial data:
    ./database/create.sh

In the event of damaging the database during testing the following instructions aim to help reset the local database:

    // While outside of the container
    rm -rf ~/srv/postgres
    docker pull postgres
    mkdir -p ~/srv/postgres
    docker run --rm --name lil-postgres -e POSTGRES_PASSWORD=password -d -v HOME/srv/postgres:/var/lib/postgresql/data -p 5432:5432 postgres
    
    // Docker exec to go inside lil-postgres container
    docker exec -it lil-postgres bash
    psql -h localhost -U postgres
    CREATE DATABASE duopoly;
    GRANT ALL PRIVILEGES ON DATABASE duopoly TO postgres;
    
    // Now exit and stop lil-postgres container. docker compose up should work now.

  


## Project structure

### Database Structure


### File Structure


### 