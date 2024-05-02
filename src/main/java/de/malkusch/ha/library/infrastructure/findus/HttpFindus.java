package de.malkusch.ha.library.infrastructure.findus;

import static de.malkusch.ha.library.infrastructure.findus.IdParser.parseId;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.time.LocalDate;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import de.malkusch.ha.library.infrastructure.http.HttpClient;
import de.malkusch.ha.library.infrastructure.http.HttpClient.Header;
import de.malkusch.ha.library.model.Findus;
import de.malkusch.ha.library.model.Medium;
import de.malkusch.ha.library.model.RentedMedia;
import de.malkusch.ha.library.model.RentedMedium;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
final class HttpFindus implements Findus {

    private final String url;
    private final HttpClient http;
    private final Header authentication;

    @Override
    public RentedMedia rentedMedia() throws IOException, InterruptedException {
        try (var response = http.get(url, authentication)) {
            var parsed = Jsoup.parse(response.body, UTF_8.name(), url);
            var rows = parsed.select("#findus_leserkonto_ausgeliehen_tabelle > tbody tr");
            var media = rows.stream().map(HttpFindus::medium).toList();
            return new RentedMedia(media);
        }
    }

    private static RentedMedium medium(Element row) {
        var id = parseId(row.select("td.spaltetitel .details_hier a").attr("href"));
        var author = row.select("td.spalteautor > .sortierhinweis").text();
        var title = row.select("td.spaltetitel > .sortierhinweis").text();
        var type = row.select("td.spaltemedienart > .sortierhinweis").text();
        var until = LocalDate.parse(row.select("td.spalteausleihfrist > .sortierhinweis").text());

        var medium = new Medium(id, title);
        return new RentedMedium(medium, until);
    }
}
