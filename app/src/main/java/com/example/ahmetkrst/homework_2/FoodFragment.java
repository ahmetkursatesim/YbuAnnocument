package com.example.ahmetkrst.homework_2;

import android.app.ProgressDialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.transition.Transition;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;


public class FoodFragment extends Fragment {


    ListView FoodListView;
    ProgressDialog FoodDialog;
    ArrayList <String> FoodList = new ArrayList<>();
    ArrayAdapter<String> FoodAdapter;




    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.tabfoods,container,false);
        FoodListView=view.findViewById(R.id.Food_List);


        FoodAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, FoodList){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                View view = super.getView(position, convertView, parent);
                TextView Food_View =  view.findViewById(android.R.id.text1);
               Food_View.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);
                Food_View.setTextColor(Color.GRAY);
                return view;
            }
        };



       FoodListView.setAdapter(FoodAdapter);

      FoodListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



                Animation animation1 = new AlphaAnimation(0.3f, 1.0f);
                animation1.setDuration(2000);
                view.startAnimation(animation1);


            }

        });


        new Foods().execute();

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        FoodList.clear();
        new Foods().execute();
    }

    private class Foods extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            FoodDialog=new ProgressDialog(getActivity());
            FoodDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            FoodDialog.setMessage("Loading......");
            FoodDialog.setIndeterminate(false);
            FoodDialog.setCancelable(true);
            FoodDialog.show();
        }


        @Override
        protected Void doInBackground(Void... params) {
            try {

                Document doc= Jsoup.connect("http://ybu.edu.tr/sks/").get();
                Elements date=doc.select("h3");
                Elements element=doc.select("table p");
                FoodList.add(date.text());
                for(int i= 0;i<element.size();i++){
                   FoodList.add(element.get(i).text());
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            FoodListView.setAdapter(FoodAdapter);
            FoodDialog.dismiss();
        }

    }
}