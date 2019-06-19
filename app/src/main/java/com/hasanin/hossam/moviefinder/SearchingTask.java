package com.hasanin.hossam.moviefinder;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;

import es.dmoral.toasty.Toasty;

public class SearchingTask extends AsyncTask<Void , Void , Void> {

    private Activity activity;
    String movieName;
    Dialog progressBar;
    String linkHref = null;
    HashMap<String , MovieScrapers> movieScrapers = new HashMap<String, MovieScrapers>();
    HashMap<String , String> searchSites = new HashMap<String, String>();
    MovieScrapers scraper = null;

    public SearchingTask(Activity activity , String movieName , Dialog progressBar){
            this.activity = activity;
            this.movieName = movieName.replace(" " , "+");
            this.progressBar = progressBar;
            movieScrapers.put("http://ww.cima4u.tv/" , new Cima4u());
            movieScrapers.put("cima4film.net/" , new Cima4Film());
            movieScrapers.put("https://www.imovie-time.com" , new ImovieTimeSite());
            movieScrapers.put("http://lodynet.com/" , new LodyNet());

            searchSites.put("http://ww.cima4u.tv/" , "cima4u.tv");
            searchSites.put("cima4film.net/" , "hd.cima4film");
            searchSites.put("https://www.imovie-time.com" , "+imovie-time");
            searchSites.put("http://lodynet.com/" , "lodynet");
    }

    @Override
    protected Void doInBackground(Void... voids) {
        searchloop:
        for (String search : searchSites.keySet()) {
            try {
                Document doc = Jsoup.connect("https://www.google.com/search?q=" + movieName + searchSites.get(search)).userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21").get();
                Elements links = doc.select(".r a");
                linksloop:
                for (Element link : links) {
                    linkHref = link.attr("href");
                    Log.v("Srrt" , linkHref);
                    if (linkHref.contains(search)) {
                        Log.v("Srrt" , "opende");
                        scraper = movieScrapers.get(search);
                        scraper.setLinkHref(linkHref);
                        scraper.scrap();
                        if (scraper.getImgLink().isEmpty() || scraper.getMovieStory().isEmpty() || scraper.getMovieWatchList().isEmpty()){
                            scraper = null;
                        }
                        break linksloop;
                    }
                    Log.v("Srrt" , "nouy");
                }
                if (scraper != null){
                    break searchloop;
                }
            } catch (IOException e) {
                e.printStackTrace();

            } catch (NullPointerException e){
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progressBar.dismiss();
        if (scraper != null){
            if (scraper.getMovieWatchList().size() != 0 &&! scraper.getMovieWatchList().isEmpty() && ! scraper.getMovieStory().isEmpty() && ! scraper.getImgLink().isEmpty()) {
                // Go to showMovie activity
                Bundle bundle = new Bundle();
                bundle.putString("movieStory", scraper.getMovieStory());
                bundle.putString("movieName", movieName.replace("+", " "));
                bundle.putStringArrayList("movieLinks", scraper.getMovieWatchList());
                bundle.putString("movieImg", scraper.getImgLink());
                Intent intent = new Intent(activity, ShowMovieActivity.class);
                intent.putExtras(bundle);
                activity.startActivity(intent);
            } else {
                Toasty.error(activity, "I couldn't find what you wrote just make sure of it first.", Toast.LENGTH_LONG, true).show();
            }
        } else {
            Toasty.error(activity, "I couldn't find what you wrote just make sure of it first.", Toast.LENGTH_LONG, true).show();
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }


}
