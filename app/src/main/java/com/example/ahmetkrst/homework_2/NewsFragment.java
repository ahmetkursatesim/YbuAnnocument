package com.example.ahmetkrst.homework_2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class NewsFragment extends Fragment implements AdapterView.OnItemClickListener {

    ListView newsListView;
    ProgressDialog NewsDialog;
    ArrayList <String>News_L = new ArrayList<String>();
    ArrayList<String> NewsLinkList =new ArrayList<String>();
    ArrayAdapter<String> adapt;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tabnews, container, false);
       newsListView= view.findViewById(R.id.News_List);


        adapt = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, News_L){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                View view = super.getView(position, convertView, parent);
                TextView News_View = view.findViewById(android.R.id.text1);
                News_View.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);
                News_View.setTextColor(Color.GRAY);
                return view;
            }
        };



        newsListView.setAdapter(adapt);

        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Animation animation1 = new AlphaAnimation(0.3f, 1.0f);
                animation1.setDuration(20);
                view.startAnimation(animation1);

            }
        });
        new News().execute();
       OpenNewsLinks();
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        News_L.clear();
        new News().execute();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    private class News extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            NewsDialog = new ProgressDialog(getActivity());
            NewsDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            NewsDialog.setMessage("Loading......");
            NewsDialog.setIndeterminate(false);
            NewsDialog.setCancelable(true);
            NewsDialog.show();
        }


        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document doc = Jsoup.connect("http://www.ybu.edu.tr/muhendislik/bilgisayar/").get();
                Elements element = doc.select("div.contentNews  div.cncItem");

                Elements NewsHerf= doc.select("div.contentNews div.cncItem a");

                for (int i = 0; i < element.size(); i++) {
                   News_L.add(element.get(i).text());
                   NewsLinkList.add("http://www.ybu.edu.tr/muhendislik/bilgisayar/"+NewsHerf.get(i).attr("href"));

                }


            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            newsListView.setAdapter(adapt);
            NewsDialog.dismiss();
        }

    }


    public void OpenNewsLinks() {
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



                view.setBackgroundColor(Color.BLUE);
                Uri navsweb = Uri.parse(NewsLinkList.get(position));
                Intent Newsintent = new Intent(Intent.ACTION_VIEW, navsweb);
                startActivity(Newsintent);

            }
        });

    }


}