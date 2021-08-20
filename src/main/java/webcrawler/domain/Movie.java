package webcrawler.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Movie implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String name;
	private Float rating;
	private List<String> directors = new ArrayList<>();
	private List<String> cast = new ArrayList<>();
	private String positive_comment;

	public Movie() {}
	
	public Movie(String name, Float rating, String positive_comment) {
		super();
		this.name = name;
		this.rating = rating;
		this.positive_comment = positive_comment;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getRating() {
		return rating;
	}

	public void setRating(Float rating) {
		this.rating = rating;
	}

	public List<String> getDirectors() {
		return directors;
	}

	public void setDirectors(List<String> directors) {
		this.directors = directors;
	}
	
	public void addDirectors(String director) {
		this.directors.add(director);
	}

	public List<String> getCast() {
		return cast;
	}

	public void setCast(List<String> cast) {
		this.cast = cast;
	}
	
	public void addCast(String cast) {
		this.cast.add(cast);
	}

	public String getPositive_comment() {
		return positive_comment;
	}

	public void setPositive_comment(String positive_comment) {
		this.positive_comment = positive_comment;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("*-*-*-*-*-*-*-*-*-*-*-*-*-*\n");
		sb.append("Movie: ")
			.append(this.getName()).append('\n');
		
		sb.append("Rating: ")
			.append(this.rating).append('\n');
		
		for (String director : this.getDirectors()) {
			sb.append("Director Name: ")
				.append(director).append('\n');
		}
		
		for (String cast : this.getCast()) {
			sb.append("Cast Name: ")
				.append(cast).append('\n');
		}
		if (this.positive_comment == null) {
			sb.append("Positive comment: "+"#NONE#").append('\n');
		}
		else {
			sb.append("Positive comment: "+this.positive_comment).append('\n');
		}
		sb.append("*-*-*-*-*-*-*-*-*-*-*-*-*-*\n");
		return sb.toString();
	}

	
}
