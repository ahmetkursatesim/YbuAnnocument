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

public class AnnouncementsFragment extends Fragment implements AdapterView.OnItemClickListener  {

    ListView AnnouncementsListView;
    ProgressDialog AnnDialog;
    ArrayList<String> Announcements_List = new ArrayList<String>();
    ArrayList<String> AnnouncementsHrefList =new ArrayList<String>();
    ArrayAdapter<String> AnnouncementAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.tabannocument,container,false);
        AnnouncementsListView=view.findViewById(R.id.Annocument_List);


        AnnouncementAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, Announcements_List){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                View view = super.getView(position, convertView, parent);
                TextView Announcement_View =view.findViewById(android.R.id.text1);
                Announcement_View.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);
                Announcement_View.setTextColor(Color.GRAY);
                return view;
            }
        };


      AnnouncementsListView.setAdapter(AnnouncementAdapter);

        AnnouncementsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Animation animation1 = new AlphaAnimation(0.3f, 1.0f);
                animation1.setDuration(20);
                view.startAnimation(animation1);
            }
        });

        new Announcement().execute();
       AnnouncumentOpenLink();
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
     Announcements_List.clear();
        new Announcement().execute();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    private class Announcement extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            AnnDialog=new ProgressDialog(getActivity());
            AnnDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            AnnDialog.setMessage("Loading......");
            AnnDialog.setIndeterminate(false);
            AnnDialog.setCancelable(true);
            AnnDialog.show();
        }


        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document doc= Jsoup.connect("http://www.ybu.edu.tr/muhendislik/bilgisayar/").get();
                Elements element=doc.select("div.contentAnnouncements  div.cncItem");
                Elements AnnouncementHref = doc.select("div.contentAnnouncements div.cncItem a");
                for(int i= 0;i<element.size();i++){
                  Announcements_List.add(element.get(i).text());
                  AnnouncementsHrefList.add("http://www.ybu.edu.tr/muhendislik/bilgisayar/" +AnnouncementHref.get(i).attr("Href"));
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
          AnnouncementsListView.setAdapter(AnnouncementAdapter);
            AnnDialog.dismiss();
        }

    }



    public void AnnouncumentOpenLink() {
        AnnouncementsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                view.setBackgroundColor(Color.BLUE);
                Uri navWEB = Uri.parse(AnnouncementsHrefList.get(position));
                Intent Announcementintent = new Intent(Intent.ACTION_VIEW, navWEB);
                startActivity(Announcementintent);


            }
        });

    }


}

