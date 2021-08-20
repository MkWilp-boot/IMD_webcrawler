package webcrawler.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

import webcrawler.domain.Movie;

public class Main {

	private static final String BASE_URL = "https://www.imdb.com";
	private static final String ARGUMENT_FOR_ORDER = "&sort=userRating&dir=desc&ratingFilter=0";
	private static final String ROOT_DIR = System.getProperty("user.dir");
	
	public static void main(String[] args) throws IOException {
		
		List<String> link_to_follow = new ArrayList<>();
		
		System.setProperty("webdriver.edge.driver", ROOT_DIR + "\\res\\msedgedriver.exe");
		
		WebDriver driver = new EdgeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.imdb.com/chart/bottom");
		
		Document doc = Jsoup.parse(driver.getPageSource());
		// Getting all trs in the table
		Elements tbody = doc.getElementsByTag("tr");
		
		// skipng the first one and limiting to 10 max
		tbody.stream()
			.skip(1)
			.limit(10)
			.forEach(e -> {
				// first td content
				Element titleColumn = e.getElementsByClass("titleColumn").get(0);
				// getting all links and adding them to the list
				Elements links = titleColumn.getElementsByTag("a");
				links.stream().forEach(link -> {
					String linkHref = link.attr("href");
					link_to_follow.add(BASE_URL + linkHref);
				});
			});
		
		String url = link_to_follow.get(0); // remover
		
		/**/
		driver.get(url);
		// doing this because jsoup does not parse the entire html by itself (dynamic content)
		doc = Jsoup.parse(driver.getPageSource());
		
		Movie movie = new Movie();
		// get movie title
		String title = doc.getElementsByClass("TitleHeader__TitleText-sc-1wu6n3d-0 cLNRlG")
				.get(0)
				.text();
		movie.setName(title);
		
		// getting the rating
		Float rating = Float.parseFloat(
					doc.getElementsByClass("AggregateRatingButton__RatingScore-sc-1ll29m0-1 iTLWoV")
						.get(0)
						.text()
				);
		movie.setRating(rating);
		
		// get directors
		Element ul = doc.getElementsByClass("ipc-metadata-list-item__content-container").get(0);
		ul.children().stream()
			.forEach(li -> {
				Elements links = li.getElementsByTag("a");
				links.stream().forEach(link -> movie.addDirectors(link.text()));
			});

		// getting top cast
		Element div_all_casts = doc.getElementsByClass("ipc-sub-grid ipc-sub-grid--page-span-2 ipc-sub-grid--wraps-at-above-l ipc-shoveler__grid").get(0);
		
		for (Element title_cast_item : div_all_casts.children()) {
			String name = title_cast_item.getElementsByTag("a").get(0).attr("aria-label");
			movie.addCast(name);
		}
		
		// going to the review list
		String user_review_link = BASE_URL + doc.getElementsByClass("UserReviewsHeader__Header-k61aee-0 egCnbs")
				.get(0)
				.child(0)
				.attr("href")
				.concat(ARGUMENT_FOR_ORDER);
		
		driver.get(user_review_link);
		doc = Jsoup.parse(driver.getPageSource());
		
		// getting the list of reviews
		// continue... lister-list
		
		driver.close();
		System.exit(0);
		
		/**/
		/*
		for (String url : link_to_follow) {
			driver.get(url);
			doc = Jsoup.parse(driver.getPageSource());
			
			Movie movie = new Movie();
			String title = doc.getElementsByClass("TitleHeader__TitleText-sc-1wu6n3d-0 cLNRlG")
					.get(0)
					.text();
			movie.setName(title);
		
			driver.close();
			System.exit(0);
		}
		*/
		
	}
}
