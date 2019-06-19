package com.hasanin.hossam.moviefinder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class LodyNet implements MovieScrapers {

    public String imgLink = null;
    public ArrayList<String> movieWatchList = new ArrayList<>();
    public String movieStory = null;
    public String linkHref = null;

    @Override
    public void scrap() throws IOException , NullPointerException {
        Document lodyNet = Jsoup.connect(linkHref).userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21").get();
        imgLink = lodyNet.select(".attachment-post-thumbnail").first().attr("src");
        movieStory = lodyNet.select(".play").first().text();
        Elements players = lodyNet.select(".viewWatch .centerContent input");
        for(Element player : players) {
            movieWatchList.add(player.attr("value"));
        }
    }


    @Override
    public String getImgLink() {
        return imgLink;
    }

    @Override
    public String getMovieStory() {
        return movieStory;
    }

    @Override
    public ArrayList<String> getMovieWatchList() {
        return movieWatchList;
    }

    @Override
    public void setLinkHref(String linkHref) {
        this.linkHref = linkHref;
    }
}
