import scrapper.CacheScrapper;
import scrapper.Home;
import scrapper.Scrapper;

public class Main {
    public static void main(String[] args) {
        String url = "";
        Scrapper scrapper = new CacheScrapper();
        Home home = scrapper.parse(url);

        System.out.println(home);
    }
}
