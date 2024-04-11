package jdbc;

import game.*;
import jdbc.*;
import jdbc.account_util.*;
import jdbc.game_util.GameDAO;
import jdbc.game_util.GameUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCExecutor
{
    public static void main(String... args) {
        // DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost",
                // "duopoly", "postgres", "password");
        // need to change localhost to db to work with docker. also, make "duopoly" database beforehand (see issue 1)
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("db",
                "duopoly", "postgres", "password"); 


        try {
            Connection connection = dcm.getConnection();
            System.out.println("YIPEEEE");
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM player_in_game");
//            while(resultSet.next()){
//                System.out.println(resultSet.getInt(1));
//            }
        
        // robbed from MainToyGame.java
        AccountDAO accountDAO = new AccountDAO(connection);
//        AccountUtil account = accountDAO.findById(1);
//        System.out.println(account.getUserId() + " " + account.getUserName());


        // Intailize Player account information
        AccountUtil account1 = new AccountUtil();
        AccountUtil account2 = new AccountUtil();

        //// createInstance for accounts works
        account1.setUserName("PERSON1");
        account1 = accountDAO.createInstance(account1);
        account2.setUserName("PERSON2");
        account2 = accountDAO.createInstance(account2);
        accountDAO.update_dp(account1, 300);
        accountDAO.update_dp(account2, 700);

        AccountUtil account2update = accountDAO.findById(account1);
        accountDAO.update_dp(account2update,6000);
        AccountUtil account2update2 = accountDAO.findById(account2);
        accountDAO.update_dp(account2update2,6100);

        System.out.println(account2update.getUserName() + " " + account2update.getUserId());
        System.out.println(account2update2.getUserName() + " " + account2update2.getUserId());

         GameDAO gameDAO = new GameDAO(connection);
         GameUtil game1 = new GameUtil();
         Board gb = new Board();


        gameDAO.createInstance(game1);

        System.out.println(game1.getGameId());
        // gb.lookup.put("New York City",1);




        gb.gameBoard[4].isOccupied(true, account1.getUserId(), game1.getGameId(), game1.getGameCode(), connection);
        gb.gameBoard[9].isOccupied(true, account2.getUserId(), game1.getGameId(), game1.getGameCode(), connection);















//
//        // set up which account we want to update
//        account1 = accountDAO.findById(4);
//
//        // update dp of account1 to 300 (NOT ADD 300)
//
//
//        // update wins = wins + 1, we might not want to keep it this way
//        accountDAO.update(account1);
//
//        // update loss = loss + 1
//        accountDAO.update_loss(account1);
//
//        // update elo from iron to bronze
//        accountDAO.update_elo(account1, "Bronze");
//
          // System.out.println(account1.getUserId() + " " + account1.getUserName() + " " );
//
//        // delete account1
//        accountDAO.delete(account1);

        // Board gb = new Board()
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
