package com.twentiecker.moviecatalogue.data.entity.tv;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class TvResults implements Parcelable {

	@SerializedName("first_air_date")
	private final String firstAirDate;

	@SerializedName("overview")
	private final String overview;

	@SerializedName("original_language")
	private final String originalLanguage;

	@SerializedName("genre_ids")
	private List<Integer> genreIds;

	@SerializedName("poster_path")
	private final String posterPath;

	@SerializedName("origin_country")
	private final List<String> originCountry;

	@SerializedName("backdrop_path")
	private final String backdropPath;

	@SerializedName("original_name")
	private final String originalName;

	@SerializedName("popularity")
	private final double popularity;

	@SerializedName("vote_average")
	private final double voteAverage;

	@SerializedName("name")
	private final String name;

	@SerializedName("id")
	private final int id;

	@SerializedName("vote_count")
	private final int voteCount;

	protected TvResults(Parcel in) {
		firstAirDate = in.readString();
		overview = in.readString();
		originalLanguage = in.readString();
		posterPath = in.readString();
		originCountry = in.createStringArrayList();
		backdropPath = in.readString();
		originalName = in.readString();
		popularity = in.readDouble();
		voteAverage = in.readDouble();
		name = in.readString();
		id = in.readInt();
		voteCount = in.readInt();
	}

	public static final Creator<TvResults> CREATOR = new Creator<TvResults>() {
		@Override
		public TvResults createFromParcel(Parcel in) {
			return new TvResults(in);
		}

		@Override
		public TvResults[] newArray(int size) {
			return new TvResults[size];
		}
	};

	public String getFirstAirDate(){
		return firstAirDate;
	}

	public String getOverview(){
		return overview;
	}

	public String getOriginalLanguage(){
		return originalLanguage;
	}

	public List<Integer> getGenreIds(){
		return genreIds;
	}

	public String getPosterPath(){
		return posterPath;
	}

	public List<String> getOriginCountry(){
		return originCountry;
	}

	public String getBackdropPath(){
		return backdropPath;
	}

	public String getOriginalName(){
		return originalName;
	}

	public double getPopularity(){
		return popularity;
	}

	public double getVoteAverage(){
		return voteAverage;
	}

	public String getName(){
		return name;
	}

	public int getId(){
		return id;
	}

	public int getVoteCount(){
		return voteCount;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(firstAirDate);
		parcel.writeString(overview);
		parcel.writeString(originalLanguage);
		parcel.writeString(posterPath);
		parcel.writeStringList(originCountry);
		parcel.writeString(backdropPath);
		parcel.writeString(originalName);
		parcel.writeDouble(popularity);
		parcel.writeDouble(voteAverage);
		parcel.writeString(name);
		parcel.writeInt(id);
		parcel.writeInt(voteCount);
	}

	public TvResults(String firstAirDate, String overview, String originalLanguage, List<Integer> genreIds, String posterPath, List<String> originCountry, String backdropPath, String originalName, double popularity, double voteAverage, String name, int id, int voteCount) {
		this.firstAirDate = firstAirDate;
		this.overview = overview;
		this.originalLanguage = originalLanguage;
		this.genreIds = genreIds;
		this.posterPath = posterPath;
		this.originCountry = originCountry;
		this.backdropPath = backdropPath;
		this.originalName = originalName;
		this.popularity = popularity;
		this.voteAverage = voteAverage;
		this.name = name;
		this.id = id;
		this.voteCount = voteCount;
	}
}