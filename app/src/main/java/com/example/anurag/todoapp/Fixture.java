package com.example.anurag.todoapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Fixture extends AppCompatActivity {
    DataBaseHelper mydb;
    Bitmap camera1,camera2,gallery1,gallery2;

    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixture);
        Utility.showToast(this,"Click the plus to create icon");
        mydb =new DataBaseHelper(this);

        final Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        final Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
        final TextView edittextteam1 =findViewById(R.id.editTextTeam1);
        final TextView edittextteam2 =findViewById(R.id.editTextTeam2);
        final ImageView team2_image =findViewById(R.id.team2_image);
        final ImageView team1_image =findViewById(R.id.team1_image);
        Button addfixture =findViewById(R.id.ADDFIXTURE);
        Button updatefixture =findViewById(R.id.UPDATEFIXTURE);




        // Spinner click listener
        //spinner1.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList <String>();
        categories.add("GroupA");
        categories.add("GroupB");
        categories.add("GroupC");
        categories.add("GroupD");
        categories.add("GroupE");
        categories.add("GroupF");
        categories.add("GroupG");
        categories.add("GroupH");
        categories.add("Quarter-final");
        categories.add("Semi-final");
        categories.add("Final");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter <String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner1.setAdapter(dataAdapter);




        // Spinner click listener
        //  spinner1.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories1 = new ArrayList <String>();
        for(int i = 1 ; i <= 50 ; ++i){
            categories1.add("" + i);
        }
        // Creating adapter for spinner
        ArrayAdapter <String> dataAdapter1 = new ArrayAdapter <String>(this, android.R.layout.simple_spinner_item, categories1);

        // Drop down layout style - list view with radio button
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner2.setAdapter(dataAdapter1);


        if(getIntent() != null && getIntent().hasExtra("mId")){

            intent = getIntent();

            final String iId = intent.getStringExtra("mId");
            final String igroup =intent.getStringExtra("group");
            final String imatchno=intent.getStringExtra("matchno");
            final String iteam1name =intent.getStringExtra("team1name");
            final String iteam1image=intent.getStringExtra("team1image");
            final String iteam2name =intent.getStringExtra("team2name");
            final String iteam2image=intent.getStringExtra("team2image");
            edittextteam1.setText(iteam1name);
            edittextteam2.setText(iteam2name);
            String[] check ={"GroupA","GroupB","GroupC","GroupD","groupE","GroupF","GroupG","GroupH","Quarter-final","Semi-final","Final"};
            for(int i=0;i<=10;i++){

                if(igroup==check[i]){
                    Log.d("ivalue","value of i");
                    spinner1.setSelection(i);
                }
            }



            for(int i=1;i<=50;i++){

                if( Integer.parseInt(imatchno) == i){
                    spinner2.setSelection((i-1));
                }
            }

            byte[] decodedString1 = Base64.decode(iteam1image, Base64.DEFAULT);
            Bitmap decodedByte1 = BitmapFactory.decodeByteArray(decodedString1, 0, decodedString1.length);
            byte[] decodedString2 = Base64.decode(iteam2image, Base64.DEFAULT);
            Bitmap decodedByte2 = BitmapFactory.decodeByteArray(decodedString2, 0, decodedString2.length);
            team1_image.setImageBitmap(decodedByte1);
            team2_image.setImageBitmap(decodedByte2);


            addfixture.setVisibility(View.GONE);
            updatefixture.setVisibility(View.VISIBLE);



            updatefixture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    updatedata(iId);
                }
            });


        }else{

            updatefixture.setVisibility(View.GONE);
            addfixture.setVisibility(View.VISIBLE);


        }


        imageicon();
        adddata();




    }


    public void updatedata(final String id){


        final Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        final Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
        final TextView edittextteam1 =findViewById(R.id.editTextTeam1);
        final TextView edittextteam2 =findViewById(R.id.editTextTeam2);
        final ImageView team2_image =findViewById(R.id.team2_image);
        final ImageView team1_image =findViewById(R.id.team1_image);
        Bitmap team1bit,team2bit;

        if(edittextteam1==null){
            Utility.showToast(Fixture.this, "Please enter Team_1 Name");
            return;
        }
        if(edittextteam2==null){
            Utility.showToast(Fixture.this,"Please enter Team_2 Name");
            return;
        }

        if ( camera1!=null ){
            team1bit=camera1;
        }
        else if(gallery1!=null){
            team1bit=gallery1;
        }
        else if(team1_image!=null){
            byte[] decodedString1 = Base64.decode(intent.getStringExtra("team1image"), Base64.DEFAULT);
            team1bit=BitmapFactory.decodeByteArray(decodedString1, 0, decodedString1.length);
        }
        else {
            Utility.showToast(Fixture.this, "Please enter icon for Team_1");
            return;
        }

        ByteArrayOutputStream team1baos =new ByteArrayOutputStream();
        team1bit.compress(Bitmap.CompressFormat.JPEG,40,team1baos);
        byte[] b1 =team1baos.toByteArray();
        String encodeImage1 = Base64.encodeToString(b1,Base64.DEFAULT);

        if ( camera2!= null ){
            team2bit=camera2;
        }
        else if(gallery2!=null){
            team2bit=gallery2;
        }
        else if(team2_image!=null){
            byte[] decodedString1 = Base64.decode(intent.getStringExtra("team2image"), Base64.DEFAULT);
            team2bit=BitmapFactory.decodeByteArray(decodedString1, 0, decodedString1.length);                    }
        else{

            Utility.showToast(Fixture.this,"Please enter icon for Team_2");
            return;
        }


        ByteArrayOutputStream team2baos =new ByteArrayOutputStream();
        team2bit.compress(Bitmap.CompressFormat.JPEG,40,team2baos);
        byte[] b2 =team2baos.toByteArray();
        String encodeImage2 = Base64.encodeToString(b2,Base64.DEFAULT);


        String strgroup =(String)spinner1.getSelectedItem();
        String strmatchno =(String) spinner2.getSelectedItem();


        boolean isinserted = mydb.updatedata(id,strgroup,Integer.parseInt(strmatchno),edittextteam1.getText().toString(),encodeImage1,edittextteam2.getText().toString(),encodeImage2);

        if(isinserted==true){
            Utility.showToast(Fixture.this,"Data updated");
        }
        else {
            Utility.showToast(Fixture.this,"Data not updated");
        }

        finish();



    }



    public void adddata(){
        final Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        final Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
        final TextView edittextteam1 =findViewById(R.id.editTextTeam1);
        final TextView edittextteam2 =findViewById(R.id.editTextTeam2);
        final ImageView team2_image =findViewById(R.id.team2_image);
        final ImageView team1_image =findViewById(R.id.team1_image);
        Button ADDFIXTURE =findViewById(R.id.ADDFIXTURE);
        ADDFIXTURE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(edittextteam1==null){
                    Utility.showToast(Fixture.this, "Please enter Team_1 Name");
                    return;
                }
                if(edittextteam2==null){
                    Utility.showToast(Fixture.this,"Please enter Team_2 Name");
                    return;
                }

                Bitmap team1bit = null;
                if ( camera1!=null ){
                    team1bit=camera1;
                }
                else if(gallery1!=null){
                    team1bit=gallery1;
                }
                else {

                    Utility.showToast(Fixture.this, "Please enter icon for Team_1");
                    return;
                }
                ByteArrayOutputStream team1baos =new ByteArrayOutputStream();
                team1bit.compress(Bitmap.CompressFormat.JPEG,40,team1baos);
                byte[] b1 =team1baos.toByteArray();
                String encodeImage1 = Base64.encodeToString(b1,Base64.DEFAULT);

                Bitmap team2bit = null ;
                if ( camera2!= null ){
                    team2bit=camera2;
                }
                else if(gallery2!=null){
                    team2bit=gallery2;
                }
                else{

                    Utility.showToast(Fixture.this,"Please enter icon for Team_2");
                    return;
                }

                ByteArrayOutputStream team2baos =new ByteArrayOutputStream();
                team2bit.compress(Bitmap.CompressFormat.JPEG,40,team2baos);
                byte[] b2 =team2baos.toByteArray();
                String encodeImage2 = Base64.encodeToString(b2,Base64.DEFAULT);


                String strgroup =(String)spinner1.getSelectedItem();
                String strmatchno =(String) spinner2.getSelectedItem();


                boolean isinserted = mydb.insertData(strgroup,Integer.parseInt(strmatchno),edittextteam1.getText().toString(),encodeImage1,edittextteam2.getText().toString(),encodeImage2);
                if(isinserted==true){
                    Utility.showToast(Fixture.this,"Data inserted");
                }
                else {
                    Utility.showToast(Fixture.this,"Data not inserted");
                }

                finish();


            }
        });


    }






    public final void imageicon(){
        ImageView icon1 =findViewById(R.id.icon1);
        ImageView icon2 =findViewById(R.id.icon2);
        icon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] items = { "Take Photo", "Choose from Library",
                        "Cancel" };
                AlertDialog.Builder builder = new AlertDialog.Builder(Fixture.this);
                builder.setTitle("Add Photo!");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item].equals("Take Photo")) {
                            if(Utility.checkpermission(Fixture.this)){
                                cameraIntent(100);
                            }
                        }

                        else if (items[item].equals("Choose from Library")) {
                            if(Utility.checkpermission(Fixture.this)){
                                galleryIntent(101);

                            }
                        }
                    }
                });
                builder.show();
            }
        });

        icon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] items = { "Take Photo", "Choose from Library",
                        "Cancel" };
                AlertDialog.Builder builder = new AlertDialog.Builder(Fixture.this);
                builder.setTitle("Add Photo!");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item].equals("Take Photo")) {
                            if(Utility.checkpermission(Fixture.this)){
                                cameraIntent(200);
                            }
                        }

                        else if (items[item].equals("Choose from Library")) {
                            if(Utility.checkpermission(Fixture.this)){
                                galleryIntent(201);
                            }

                        }
                    }
                });
                builder.show();
            }
        });

    }




    private void cameraIntent(int requestCode) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, requestCode);
    }

    private void galleryIntent(int requestCode) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE){
            if(grantResults.length == 2){
                //Permission Granted....
                Utility.showToast(Fixture.this, "GRANTED");
            }else{
                //Permission Denied...


            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == 100) {
                Utility.showToast(Fixture.this, "IMAGE CAPTURED FOR TEAM1 ICON");
                camera1 =(Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes =new ByteArrayOutputStream();
                camera1.compress(Bitmap.CompressFormat.JPEG,90,bytes);
                File destination =new File(Environment.getExternalStorageDirectory(),System.currentTimeMillis()+".jpg");
                FileOutputStream fo;
                try{
                    destination.createNewFile();
                    fo= new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                }catch(FileNotFoundException e){
                    e.printStackTrace();
                }
                catch( IOException e){
                    e.printStackTrace();
                }
                ImageView icon1 =findViewById(R.id.team1_image);
                icon1.setImageBitmap(camera1);


            }
        }

        if(resultCode == RESULT_OK){
            if(requestCode == 101){
                Utility.showToast(Fixture.this, "GALLEY IMAGE SELECTED FOR TEAM1 ICON");

                gallery1=null;
                if(data!=null){
                    try{
                        gallery1=MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(),data.getData());
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    ImageView icon1 =findViewById(R.id.team1_image);
                    icon1.setImageBitmap(gallery1);
                }

            }
        }
        if(resultCode == RESULT_OK){
            if(requestCode == 200){
                Utility.showToast(Fixture.this, "IMAGE CAPTURED FOR TEAM2 ICON");
                camera2 =(Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes =new ByteArrayOutputStream();
                camera2.compress(Bitmap.CompressFormat.JPEG,90,bytes);
                File destination =new File(Environment.getExternalStorageDirectory(),System.currentTimeMillis()+".jpg");
                FileOutputStream fo;
                try{
                    destination.createNewFile();
                    fo= new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                }catch(FileNotFoundException e){
                    e.printStackTrace();
                }
                catch( IOException e){
                    e.printStackTrace();
                }
                ImageView icon1 =findViewById(R.id.team2_image);
                icon1.setImageBitmap(camera2);



            }
        }
        if(resultCode == RESULT_OK){
            if(requestCode == 201){
                Utility.showToast(Fixture.this, "GALLEY IMAGE SELECTED FOR TEAM2 ICON");
                gallery2 =null;
                if(data!=null){
                    try{
                        gallery2 = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(),data.getData());
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    ImageView icon2 =findViewById(R.id.team2_image);
                    icon2.setImageBitmap(gallery2);
                }

            }
        }




    }
}