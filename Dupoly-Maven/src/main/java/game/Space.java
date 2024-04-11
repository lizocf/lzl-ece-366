package game;

import java.sql.Connection;

public interface Space {

    void isOccupied(boolean occupied, int userId, int gameId, String gamecode, Connection connection);
    //    Alot of spaces in Duoploy require different actions
    //    depending on whether a user is on that space or not
    //    for example: if someone is on a property that has been
    //    brought then they should pay rent. However a different
    //    property like debt may have a different effect.

    // abstract void manipDatabase() <-- takes in the data base name you want to manipulate.
}
