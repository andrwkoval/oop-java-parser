import lombok.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.xml.bind.Element;
import java.io.IOException;

@ToString
@Getter
@Setter
//TODO Create class that represents coordinates
public class Coordinates {
    private Float lat, lon;

    @SneakyThrows
    public static Coordinates parseCoordinates(String cityUrl) {
        float tempLat, tempLon;
        Coordinates coords = new Coordinates();
        Document doc = Jsoup.connect(cityUrl).get();
        Elements cityCoords = doc.select(".geo");
        if (cityCoords.size() != 0) {
            String strCoords[] = cityCoords.text().replaceAll(";", "").split(" ");
            tempLat = Float.parseFloat(strCoords[0]);
            tempLon = Float.parseFloat(strCoords[1]);
            coords.setLat(tempLat);
            coords.setLon(tempLon);
        } else if (doc.select(".mw-kartographer-maplink").size() != 0) {
            coords.setLat(Float.parseFloat(doc.select(".mw-kartographer-maplink").get(0).attr("data-lat")));
            coords.setLon(Float.parseFloat(doc.select(".mw-kartographer-maplink").get(0).attr("data-lon")));
        } else {
            coords.setLat(null);
            coords.setLon(null);
        }
        return coords;

    }

}
