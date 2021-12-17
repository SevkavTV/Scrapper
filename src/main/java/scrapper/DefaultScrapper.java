package scrapper;

import lombok.SneakyThrows;
import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class DefaultScrapper implements Scrapper{
    private static final String PRICE_SELECTOR = ".detail__info-xlrg";
    private static final String BED_SELECTOR = ".nhs_BedsInfo";
    private static final String BATH_SELECTOR = ".nhs_BathsInfo";
    private static final String GARAGE_SELECTOR = ".nhs_GarageInfo";

    @Override @SneakyThrows
    public Home parse(String url) {
        Document doc = Jsoup.connect(url).get();
        int price = getPrice(doc);
        double beds = getBed(doc);
        double baths = getBath(doc);
        double garages = getGarage(doc);
        return new Home(price, beds, baths, garages);
    }

    private static int getPrice(Document doc) {
        Element priceTag = doc.selectFirst(PRICE_SELECTOR);
        String price = priceTag.text().replaceAll("[^0-9]", "");
        return Integer.parseInt(price);
    }

    private static double getBed(Document doc) {
        Element bedsTag = doc.selectFirst(BED_SELECTOR);
        String beds = bedsTag.text().replaceAll("[^0-9.,]", "");
        return Double.parseDouble(beds);
    }

    private static double getBath(Document doc) {
        Element bathTag = doc.selectFirst(BATH_SELECTOR);
        String bath = bathTag.text().replaceAll("[^0-9.,]", "");
        return Double.parseDouble(bath);
    }

    private static double getGarage(Document doc) {
        Element garageTag = doc.selectFirst(GARAGE_SELECTOR);
        String garage = garageTag.text().replaceAll("[^0-9.,]", "");
        return Double.parseDouble(garage);
    }
}
