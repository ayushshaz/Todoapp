package com.example.anurag.todoapp;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DataBaseHelper mydb = new DataBaseHelper(this);
    final ArrayList<MatchDetail> matchDetails = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Utility.showToast(this, "Click the plus Button to create Fixture");
        createfixturef();


    }

    public  final void createfixturef(){
        Button createfixtureb = findViewById(R.id.createfixtureb);
        createfixtureb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Fixture.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        matchDetails.clear();
        Cursor cursor = mydb.getalldata();
        ListView listview =(ListView) findViewById(R.id.list);

        if(cursor == null){
            return;
        }

        while(cursor.moveToNext()){

            MatchDetail matchDetail = new MatchDetail();

            String image1_base64,image2_base64;
            image1_base64=cursor.getString(4);

            //Log.d("IMAGE", image1_base64);


            byte[] decodedString = Base64.decode(image1_base64, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);


            image2_base64=cursor.getString(6);
            byte[] decodedString1 = Base64.decode(image2_base64, Base64.DEFAULT);
            Bitmap decodedByte1 = BitmapFactory.decodeByteArray(decodedString1, 0, decodedString1.length);

            //Log.d("IMAGE1", image2_base64);
            matchDetail.setMatchId(cursor.getInt(0) + "");
            matchDetail.setTeamOneName(cursor.getString(3));
            matchDetail.setTeamOneImage(decodedByte);
            matchDetail.setGroup(cursor.getString(1));
            matchDetail.setTeamTwoImage(decodedByte1);
            matchDetail.setMatch(cursor.getInt(2) + "");
            matchDetail.setTeamOneImageBase64(image1_base64);
            matchDetail.setTeamTwoImageBase64(image2_base64);
            matchDetail.setTeamTwoName(cursor.getString(5));
            Log.d("DETAILS", matchDetail.toString());
            matchDetails.add(matchDetail);
        }
        listview.setAdapter(new MyBaseAdapter(MainActivity.this,matchDetails));

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                MatchDetail userdata = matchDetails.get(position);
                Intent intent = new Intent(MainActivity.this,Fixture.class);

                intent.putExtra("mId", userdata.getMatchId());
                intent.putExtra("group", userdata.getGroup());
                intent.putExtra("matchno",userdata.getMatch());
                intent.putExtra("team1name",userdata.getTeamOneName());
                intent.putExtra("team1image",userdata.getTeamOneImageBase64());
                intent.putExtra("team2name",userdata.getTeamTwoName());
                intent.putExtra("team2image",userdata.getTeamTwoImageBase64());

                startActivity(intent);
            }
        });

    }


    public void delete(View view){

        if(matchDetails.size()!=0){

            Utility.showToast(MainActivity.this,"Data Deleted");

        }else{
            Utility.showToast(MainActivity.this,"Database is Empty");
        }

        for(int i = 0 ; i < matchDetails.size() ; ++i){
            mydb.deletedata(matchDetails.get(i).getMatchId());
        }
        ListView listview =(ListView) findViewById(R.id.list);
        matchDetails.clear();

        listview.setAdapter(new MyBaseAdapter(MainActivity.this,matchDetails));
    }





}
