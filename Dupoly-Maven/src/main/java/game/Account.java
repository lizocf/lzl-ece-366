package game;
import com.fasterxml.jackson.core.JsonProcessingException;
import jdbc.DatabaseConnectionManager;
import jdbc.account_util.AccountDAO;
import jdbc.account_util.AccountUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;

// For player we might have to call the util functions intself or something

//@SpringBootApplication
//@RestController
@CrossOrigin
public class Account{
    public int user_id, num_wins, num_losses, elo_rating, duo_points;
    public int user_name;
//    public Account(int user_id, int num_wins, int num_losses, int elo_rating, int duo_points, String[] user_name, String[] password) {
//        this.user_id = user_id;
//        this.num_wins = num_wins;
//        this.num_losses = num_losses;
//        this.elo_rating = elo_rating;
//        this.duo_points = duo_points;
//        this.user_name = user_name;
//        this.password = password;
//    }


    @GetMapping("/accountTest")
    public String accountTest()
    {
        System.out.println("ACCOUNT TESTING 123");
        return "account springboot!";

    }

    @GetMapping("/getUserName/{userName}")
    public AccountUtil getUserName(@PathVariable("userName") String userName) {
        System.out.println(userName);
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("db",
                "duopoly", "postgres", "password");
        AccountUtil account = new AccountUtil();
        account.setUserName(userName);
        try {
            Connection connection = dcm.getConnection();
            AccountDAO accountDAO = new AccountDAO(connection);

            account = accountDAO.findById(account);
            System.out.println(account);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return account;
    }


    @PostMapping("/createNewAccount")
    public AccountUtil createNewAccount(@RequestBody String json) throws JsonProcessingException
    {
        System.out.println(json);
        ObjectMapper objectMapper = new ObjectMapper();
        Map <String, String> inputMap = objectMapper.readValue(json, Map.class);
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("db",
                "duopoly", "postgres", "password");
        AccountUtil newAccount = new AccountUtil();

        try {
            Connection connection = dcm.getConnection();
            AccountDAO accountDAO = new AccountDAO(connection);
            newAccount.setUserName(inputMap.get("user_name"));
            newAccount.setUserPW(inputMap.get("user_pw"));
            System.out.println(newAccount);
            newAccount = accountDAO.createInstance(newAccount);
            System.out.println(newAccount);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return newAccount;
    }

    @PostMapping("/createToken")
    public AccountUtil createToken(@RequestBody String json) throws JsonProcessingException
    {
        System.out.println(json);
        ObjectMapper objectMapper = new ObjectMapper();
        Map <String, String> inputMap = objectMapper.readValue(json, Map.class);
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("db",
                "duopoly", "postgres", "password");
        AccountUtil newAccount = new AccountUtil();

        try {
            Connection connection = dcm.getConnection();
            AccountDAO accountDAO = new AccountDAO(connection);
            newAccount.setUserName(inputMap.get("user_name"));
            System.out.println(newAccount);
            newAccount = accountDAO.findById(newAccount);
            newAccount.setToken(inputMap.get("token"));
            accountDAO.createToken(newAccount);
            System.out.println(newAccount);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return newAccount;
    }

    @PostMapping("/updateWins")
    public AccountUtil updateWins(@RequestBody String json) throws JsonProcessingException
    {
        System.out.println(json);
        ObjectMapper objectMapper = new ObjectMapper();
        Map <String, String> inputMap = objectMapper.readValue(json, Map.class);
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("db",
                "duopoly", "postgres", "password");
        AccountUtil account = new AccountUtil();

        try {
            Connection connection = dcm.getConnection();
            AccountDAO accountDAO = new AccountDAO(connection);
            account.setUserName(inputMap.get("user_name"));
            account = accountDAO.findById(account);
            accountDAO.update(account);
            System.out.println(account);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return account;
    }

    @PostMapping("/updateLoss")
    public AccountUtil updateLoss(@RequestBody String json) throws JsonProcessingException
    {
        System.out.println(json);
        ObjectMapper objectMapper = new ObjectMapper();
        Map <String, String> inputMap = objectMapper.readValue(json, Map.class);
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("db",
                "duopoly", "postgres", "password");
        AccountUtil account = new AccountUtil();

        try {
            Connection connection = dcm.getConnection();
            AccountDAO accountDAO = new AccountDAO(connection);
            account.setUserName(inputMap.get("user_name"));
            account = accountDAO.findById(account);
            accountDAO.update_loss(account);
            System.out.println(account);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return account;
    }

    @PostMapping("/updateDP")
    public AccountUtil updateDP(@RequestBody String json) throws JsonProcessingException
    {
        System.out.println(json);
        ObjectMapper objectMapper = new ObjectMapper();
        Map <String, String> inputMap = objectMapper.readValue(json, Map.class);
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("db",
                "duopoly", "postgres", "password");
        AccountUtil account = new AccountUtil();

        try {
            Connection connection = dcm.getConnection();
            AccountDAO accountDAO = new AccountDAO(connection);
            account.setUserName(inputMap.get("user_name"));
            account = accountDAO.findById(account);
            accountDAO.update_dp(account, Integer.valueOf(inputMap.get("duo_points")));
            System.out.println(account);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return account;
    }

    @PostMapping("/updateElo")
    public AccountUtil updateElo(@RequestBody String json) throws JsonProcessingException
    {
        System.out.println(json);
        ObjectMapper objectMapper = new ObjectMapper();
        Map <String, String> inputMap = objectMapper.readValue(json, Map.class);
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("db",
                "duopoly", "postgres", "password");
        AccountUtil account = new AccountUtil();

        try {
            Connection connection = dcm.getConnection();
            AccountDAO accountDAO = new AccountDAO(connection);
            account.setUserName(inputMap.get("user_name"));
            account = accountDAO.findById(account);
            accountDAO.update_elo(account, inputMap.get("elo"));
            System.out.println(account);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return account;
    }

    @PostMapping("/deleteAccount")
    public AccountUtil deleteAcc(@RequestBody String json) throws JsonProcessingException
    {
        System.out.println(json);
        ObjectMapper objectMapper = new ObjectMapper();
        Map <String, String> inputMap = objectMapper.readValue(json, Map.class);
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("db",
                "duopoly", "postgres", "password");
        AccountUtil account = new AccountUtil();

        try {
            Connection connection = dcm.getConnection();
            AccountDAO accountDAO = new AccountDAO(connection);
            account.setUserName(inputMap.get("user_name"));
            account = accountDAO.findById(account);
            accountDAO.delete(account);
            System.out.println(account);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return account;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getNum_wins() {
        return num_wins;
    }

    public void setNum_wins(int num_wins) {
        this.num_wins = num_wins;
    }

    public int getNum_losses() {
        return num_losses;
    }

    public void setNum_losses(int num_losses) {
        this.num_losses = num_losses;
    }

    public int getElo_rating() {
        return elo_rating;
    }

    public void setElo_rating(int elo_rating) {
        this.elo_rating = elo_rating;
    }

    public int getDuo_points() {
        return duo_points;
    }

    public void setDuo_points(int duo_points) {
        this.duo_points = duo_points;
    }

    public int getUser_name() {
        return user_name;
    }

    public void setUser_name(int user_name) {
        this.user_name = user_name;
    }
}