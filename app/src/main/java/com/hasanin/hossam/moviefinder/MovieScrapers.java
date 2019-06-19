package com.hasanin.hossam.moviefinder;

import java.io.IOException;
import java.util.ArrayList;

public interface MovieScrapers {
    public String imgLink = null;
    public ArrayList<String> movieWatchList = new ArrayList<>();
    public String movieStory = null;
    public String linkHref = null;

    public void scrap() throws IOException , NullPointerException;
    public String getMovieStory();
    public String getImgLink();
    public ArrayList<String> getMovieWatchList();
    public void setLinkHref(String linkHref);
}
