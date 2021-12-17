package scrapper;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class CacheScrapper implements Scrapper{
    @Override @SneakyThrows
    public Home parse(String url) {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:db.sqlite");
        Statement statement = connection.createStatement();

        String query = String.format("select count(*) as count from homes where url='%s'", url);
        ResultSet rs = statement.executeQuery(query);
        System.out.println("Trying to find url in cache...");

        int count = rs.getInt("count");
        if(count == 0) {
            DefaultScrapper defaultScrapper = new DefaultScrapper();
            Home home = defaultScrapper.parse(url);
            System.out.println("Retrieving data from the website...");

            String homeInsertQuery = String.format("insert into homes(url, price, beds, baths, garage) values('%s', '%d', '%f', '%f', '%f')",
                    url,
                    home.getPrice(),
                    home.getBeds(),
                    home.getBaths(),
                    home.getGarages());
            statement.executeUpdate(homeInsertQuery);
            return home;
        } else {
            System.out.println("Found in cache...");
            String selectQuery = String.format("select * from homes where url='%s'",url);
            ResultSet resHome = statement.executeQuery(selectQuery);

            return new Home(resHome.getInt("price"),
                            resHome.getDouble("beds"),
                            resHome.getDouble("baths"),
                            resHome.getDouble("garages"));
        }
    }
}
