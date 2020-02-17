package md.felicia;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class PostgreSQL {

    private static String DB_USER;
    private static String DB_PASSWORD;
    private static String DB_NAME;
    private static String DB_SERVER;

    private static PostgreSQL instance = null;
    public String status;
    private Connection connection;

    private PostgreSQL() {
        Properties properties = new Properties();
        try {
            properties.load(getClass().getResourceAsStream("config.ini"));
            DB_USER = properties.getProperty("DB_USER");
            DB_NAME = properties.getProperty("DB_NAME");
            DB_PASSWORD = properties.getProperty("DB_PASSWORD");
            DB_SERVER = properties.getProperty("DB_SERVER");


        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection("jdbc:postgresql://" + DB_SERVER + ":5432/" + DB_NAME, DB_USER, DB_PASSWORD);
            this.status = "OK";
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            this.status = "Fail";

        }
    }

    static synchronized PostgreSQL getInstance() {
        if (instance == null) {
            instance = new PostgreSQL();
        }
        return instance;
    }

    public void insertPrice(CatalogItem catalogItem) {

        String cmd = "INSERT INTO public.\"prices\" (" +
                "\"codulmedicamentului\", \"denumireacomerciala\", \"formafarmaceutica\", doza, volum, divizarea," +
                "country, detinatorul, \"numardeinregistrare\", \"registerdate\", \"codatc\", \"denumireainternationala\", \"termendevalabilitate\", barcode, \"pricemdl\", " +
                "\"priceval\", currency, \"dataaprobarii\", modificari, modificari2, \"codulvamal\" )" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(cmd);
            preparedStatement.setString(1, catalogItem.getCodulMedicamentului());
            preparedStatement.setString(2, catalogItem.getDenumireaComerciala());
            preparedStatement.setString(3, catalogItem.getFormaFarmaceutica());
            preparedStatement.setString(4, catalogItem.getDoza());
            preparedStatement.setString(5, catalogItem.getVolum());
            preparedStatement.setString(6, catalogItem.getDivizarea());
            preparedStatement.setString(7, catalogItem.getCountry());
            preparedStatement.setString(8, catalogItem.getDetinatorul());
            preparedStatement.setInt(9, catalogItem.getNumarDeInregistrare());
            preparedStatement.setDate(10, catalogItem.getRegisterDate());
            preparedStatement.setString(11, catalogItem.getCodATC());
            preparedStatement.setString(12, catalogItem.getDenumireaInternationala());
            preparedStatement.setInt(13, catalogItem.getTermenDeValabilitate());
            preparedStatement.setLong(14, catalogItem.getBarcode());
            preparedStatement.setDouble(15, catalogItem.getPriceMDL());
            preparedStatement.setDouble(16, catalogItem.getPriceVal());
            preparedStatement.setString(17, catalogItem.getCurrency());
            preparedStatement.setString(18, catalogItem.getDataAprobarii());
            preparedStatement.setString(19, catalogItem.getModificari());
            preparedStatement.setString(20, catalogItem.getModificari2());
            preparedStatement.setInt(21, catalogItem.getCodulVamal());

            int rows = preparedStatement.executeUpdate();
            System.out.println(rows);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Inserted");

    }

    public void executeSQL(String sql) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);
            int rows = preparedStatement.executeUpdate();
            System.out.println(rows);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}