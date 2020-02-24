package dag.ord.source.snl

import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.junit.Test

import org.junit.Assert.*

fun <T> er(t : T) = `is`(t)

class SnlSourceTest {

    @Test
    fun toResults() {
        val snl = SnlSource.createWordSources().first { it.sourceId == "SNL" } as SnlSource

        val results = snl.toResults("Ku", json, 100)

        assertThat(results.size, er (3))
        assertThat(results[0].displayUrl, CoreMatchers.containsString("Petter_Dass"))
    }

    private val json = "[\n" +
            "  {\n" +
            "    \"id\": null,\n" +
            "    \"headword\": \"Petter Dass\",\n" +
            "    \"clarification\": null,\n" +
            "    \"article_type_id\": 15,\n" +
            "    \"taxonomy_id\": 3065,\n" +
            "    \"encyclopedia_id\": 4,\n" +
            "    \"permalink\": \"Petter_Dass\",\n" +
            "    \"article_id\": 5384,\n" +
            "    \"rank\": 590.091940879822,\n" +
            "    \"taxonomy_title\": \"Norsk og samisk litteratur\",\n" +
            "    \"snippet\": \"<mark>Petter</mark> Petterssøn <mark>Dass</mark> (også skrevet Peiter Pittersen Don <mark>Dass</mark>) (ca. 1600–ca. 1653/54) og Maren Pedersdatter Falch (1629–1709; hun gift 2) 1655 med sogneprest i Hadsel Mogens Pederssøn Thilemann, 1599–1664, og 3) 1670 med fogd i Helgeland Peder\",\n" +
            "    \"article_url\": \"https://nbl.snl.no/Petter_Dass\",\n" +
            "    \"article_url_json\": \"https://nbl.snl.no/Petter_Dass.json\",\n" +
            "    \"title\": \"Petter Dass\",\n" +
            "    \"license\": \"fri\",\n" +
            "    \"first_image_url\": \"https://storage.googleapis.com/snl-no-media/media/30240/petter-dass.jpg\",\n" +
            "    \"first_image_license\": \"Gjengitt med tillatelse\",\n" +
            "    \"first_two_sentences\": \"Prest, jekteskipper, proprietær og forfatter. Foreldre: Hansdelsmann og bergensborger Petter Petterssøn Dass (også skrevet Peiter Pittersen Don Dass) (ca. 1600–ca. 1653/54) og Maren Pedersdatter Falch (1629–1709; hun gift 2) 1655 med sogneprest i Hadsel Mogens Pederssøn Thilemann, 1599–1664, og 3) 1670 med fogd i Helgeland Peder Christopherssøn Broch, ca.\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": null,\n" +
            "    \"headword\": \"Karl Erik Harr\",\n" +
            "    \"clarification\": null,\n" +
            "    \"article_type_id\": 15,\n" +
            "    \"taxonomy_id\": 608,\n" +
            "    \"encyclopedia_id\": 4,\n" +
            "    \"permalink\": \"Karl_Erik_Harr\",\n" +
            "    \"article_id\": 6381,\n" +
            "    \"rank\": 25.4550305604935,\n" +
            "    \"taxonomy_title\": \"Bildende kunst\",\n" +
            "    \"snippet\": \"<mark>Petter</mark> <mark>Dass</mark>' Nordlands Trompet. Listen vitner om en enestående produktivitet og arbeidsdisiplin.Men Harr byr ikke bare på historieformidling og naturskjønnhet. Humor og fabuleringsevne kommer frem bl.a. i bildene av marmæle (et mytisk havvesen), trollkveite og nisser i hans egen samling\",\n" +
            "    \"article_url\": \"https://nbl.snl.no/Karl_Erik_Harr\",\n" +
            "    \"article_url_json\": \"https://nbl.snl.no/Karl_Erik_Harr.json\",\n" +
            "    \"title\": \"Karl Erik Harr\",\n" +
            "    \"license\": \"begrenset\",\n" +
            "    \"first_image_url\": \"https://storage.googleapis.com/snl-no-media/media/30709/karl-erik-harr.jpg\",\n" +
            "    \"first_image_license\": \"Gjengitt med tillatelse\",\n" +
            "    \"first_two_sentences\": \"Maler, tegner, grafiker og skribent. Foreldre: Kontorsjef Karl Håkon Markus Harr (1914–85) og ekspeditrise Randi Heitmann (1917–). Gift 1) 1963 med billedhugger, tegner og grafiker Kari Emilie Rolfsen (12.10.1938–), datter av maler Rolf Rolfsen og Aase Espetvedt, ekteskapet oppløst 1968; 2) 1972 med flyger Turi Widerøe (23.12.1938–), ekteskapet oppløst 1975; 3) med tekstilkunstner Inger Anne Utvåg (2.2.1951–), datter av direktør Erling Utvåg og direktør Solveig Utvåg, ekteskapet oppløst.\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": null,\n" +
            "    \"headword\": \"Kaare Espolin Johnson\",\n" +
            "    \"clarification\": null,\n" +
            "    \"article_type_id\": 15,\n" +
            "    \"taxonomy_id\": 606,\n" +
            "    \"encyclopedia_id\": 4,\n" +
            "    \"permalink\": \"Kaare_Espolin_Johnson\",\n" +
            "    \"article_id\": 7050,\n" +
            "    \"rank\": 25.25182980299,\n" +
            "    \"taxonomy_title\": \"Kunst og estetikk\",\n" +
            "    \"snippet\": \"<mark>Petter</mark> <mark>Dass</mark>' Nordlands Trompet. Sitatene er felt inn i messing under hvert bilde – 7 i alt, malt med en gummilateksmaling på huntonittplater. (Da skipet høsten 2001 ble tatt ut av trafikk, ble bildene tatt ned, og etter restaurering skal de\",\n" +
            "    \"article_url\": \"https://nbl.snl.no/Kaare_Espolin_Johnson\",\n" +
            "    \"article_url_json\": \"https://nbl.snl.no/Kaare_Espolin_Johnson.json\",\n" +
            "    \"title\": \"Kaare Espolin Johnson\",\n" +
            "    \"license\": \"begrenset\",\n" +
            "    \"first_image_url\": \"https://storage.googleapis.com/snl-no-media/media/31042/kaare-espolin-johnson.jpg\",\n" +
            "    \"first_image_license\": \"Gjengitt med tillatelse\",\n" +
            "    \"first_two_sentences\": \"Grafiker. Foreldre: Overrettssakfører, senere politimester Alexander Lange Johnson (1870–1938) og Bergljot Bertelsen (1872–1955). Gift 8.12.1933 med Margrethe Elisabeth (“Ba”) Angell Sverdrup Qvale (25.4.1906–19.12.1976), datter av væreeier Kristian Qvale (1879–1931) og Gudny Gundersen (1873–1946); fra 1981 samboer med journalist Wenche Thorunn Nilsen (31.5.1942–), datter av ekspeditør Odvar Frank Nilsen (1912–) og hustru Evelyn Synnøve (1914–88).\"\n" +
            "  }\n" +
            "]"
}