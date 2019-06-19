package com.hasanin.hossam.moviefinder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class ImovieTimeSite implements MovieScrapers {

    public String imgLink = null;
    public ArrayList<String> movieWatchList = new ArrayList<>();
    public String movieStory = null;
    public String linkHref = null;

    @Override
    public void setLinkHref(String linkHref) {
        this.linkHref = linkHref;
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
    public void scrap() throws IOException , NullPointerException {
        if (linkHref.contains("tag")){
            Document tagPage = Jsoup.connect(linkHref).userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21").get();
            linkHref = tagPage.select(".title-2 a").first().attr("href");
        }
        Document imovieTime = Jsoup.connect(linkHref).userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21").get();
        Element movieImg = imovieTime.select(".post__thumb").first();
        imgLink = movieImg.attr("src");
        movieStory = imovieTime.select("div > span.cntt").first().text();
        Elements movieLinks = imovieTime.select(".single_tab iframe");
        for (Element thelink : movieLinks) {
            String l = thelink.attr("data-src");
            movieWatchList.add(l);
        }
    }
}
