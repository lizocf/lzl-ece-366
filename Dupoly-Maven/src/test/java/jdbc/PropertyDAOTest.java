//package jdbc;
//
//import jdbc.game_util.GameDAO;
//import jdbc.game_util.GameUtil;
//import jdbc.player_util.PlayerDAO;
//import jdbc.player_util.PlayerUtil;
//import jdbc.property_util.OwnedPropertyDAO;
//import jdbc.property_util.OwnedPropertyUtil;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.*;
//import static org.mockito.Mockito.verify;
//
//public class PropertyDAOTest
//{
//    private Connection connection;
//    private PreparedStatement preparedStatement;
//    private PlayerDAO playerDAO;
//    private PlayerUtil playerUtil;
//    private GameDAO gameDAO;
//    private GameUtil gameUtil;
//
//    private OwnedPropertyUtil ownedPropertyUtil;
//    private OwnedPropertyDAO ownedPropertyDAO;
//
//    @BeforeEach
//    void setUp() throws SQLException {
//
//        // Mock the Connection and PreparedStatement objects
//        connection = mock(Connection.class);
//        preparedStatement = mock(PreparedStatement.class);
//
//        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
//
//        playerDAO = new PlayerDAO(connection);
//        playerUtil = new PlayerUtil();
//
//        gameDAO = new GameDAO(connection);
//        gameUtil = new GameUtil();
//
//        playerUtil.setUserId(1);
//        playerUtil.setCash(1000);
//        playerUtil.setCurrentPosition(1);
//        playerUtil.setCurrentDirection("LEFT");
//
//        gameUtil.setGameCode("AS123");
//        gameUtil.setDebtPot(100);
//        gameUtil.setGameId(1);
//        gameUtil.setRecent_card("Bankrupt");
//        gameUtil.setRecentRoll(4);
//
//        ownedPropertyUtil.setGameId(1);
//        ownedPropertyUtil.setUserId(1);
//        ownedPropertyUtil.setPropertyName("New York");
//        ownedPropertyUtil.setNumOfHotels(0);
//
//
//    }
//
//    @Test
//    void testUpdateNumofHotels() throws SQLException{
//        ownedPropertyUtil = ownedPropertyDAO.findById(ownedPropertyUtil);
//        ownedPropertyDAO.updateNumHotel(ownedPropertyUtil,2);
//        assertEquals(2,ownedPropertyUtil.getNumOfHotels());
//    }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//}
