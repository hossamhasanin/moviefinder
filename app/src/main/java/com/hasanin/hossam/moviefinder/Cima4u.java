package com.hasanin.hossam.moviefinder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Cima4u implements MovieScrapers {

    public String imgLink = null;
    public ArrayList<String> movieWatchList = new ArrayList<>();
    public String movieStory = null;
    public String linkHref = null;


    @Override
    public void scrap() throws IOException , NullPointerException {
        Document cima4u = Jsoup.connect(linkHref).userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21").get();
        imgLink = cima4u.select(".poster img").first().attr("data-src");
        movieStory = cima4u.select(".storyContent").first().text();
        String watchLink = cima4u.select(".leftDetails a").first().attr("href");
        Document playPage = Jsoup.connect(watchLink).userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21").get();
        Elements players = playPage.select(".serversList .sever_link");
        for(Element player : players) {
            //http://live.cima4u.tv/structure/server.php?id=1142603
            String l = player.attr("data-link");
            Document iframe = Jsoup.connect("http://live.cima4u.tv/structure/server.php?id="+l).userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21").get();
            String finalWatchLink = iframe.select("iframe").first().attr("src");
            movieWatchList.add(finalWatchLink);
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
