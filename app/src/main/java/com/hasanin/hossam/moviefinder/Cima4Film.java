package com.hasanin.hossam.moviefinder;

import android.util.Log;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

public class Cima4Film  implements MovieScrapers {

    public String imgLink = null;
    public ArrayList<String> movieWatchList = new ArrayList<>();
    public String movieStory = null;
    public String linkHref = null;

    @Override
    public void scrap() throws IOException , NullPointerException {

        Document cima4filmPage = Jsoup.connect(linkHref).timeout(0).userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21").get();
        Element movieImg = cima4filmPage.select(".poster img").first();
        //wanted
        imgLink = movieImg.attr("src");
        Log.v("cima4film" , imgLink);
        //wanted
        movieStory = cima4filmPage.select(".contentFilm .botContent").first().text();
        Log.v("cima4film" , movieStory);

        Document viewPage = Jsoup.connect(linkHref+"?view=1").userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21").get();
        String movieNum = viewPage.select("link[rel='shortlink']").attr("href").split("p=")[1];
        Log.v("cima4film" , "movie id " + String.valueOf(movieNum));
        Elements serversNum = viewPage.select("div.serversEmbed ul.serversList li");
        Log.v("cima4film" , "movie servers " + String.valueOf(serversNum.size()));

        int i = 1;
        for (Element s : serversNum) {
            String serverLink = linkHref+"?q="+ movieNum +"&i=" +i;
            Document watchPage = Jsoup.connect(serverLink).userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21").get();
            String iframe = watchPage.select("iframe").attr("src");
            movieWatchList.add(iframe);
            Log.v("cima4film" , iframe);
            i += 1;
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
        if (linkHref.contains("https://tv.cima4film.net/")){
            this.linkHref = linkHref.replace("https://tv.cima4film.net/" , "https://hd.cima4film.net/");
        }
        this.linkHref = linkHref;
    }
}
