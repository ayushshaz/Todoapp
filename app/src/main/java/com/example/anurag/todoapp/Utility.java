package com.example.anurag.todoapp;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;



public class Utility {

    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE=123;
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)

    public static boolean checkpermission(final Context context){

        int currentAPIversion=Build.VERSION.SDK_INT;
        if(currentAPIversion>= Build.VERSION_CODES.M){

            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){

                if(ActivityCompat.shouldShowRequestPermissionRationale((Activity)context,Manifest.permission.READ_EXTERNAL_STORAGE) ){

                    AlertDialog.Builder alertbuilder =new AlertDialog.Builder(context);
                    alertbuilder.setCancelable(true);
                    alertbuilder.setTitle("Permission Neccessary");
                    alertbuilder.setMessage("External storage permission is neccessary");
                    alertbuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions((Activity) context , new String[]{Manifest.permission.READ_EXTERNAL_STORAGE ,
                                    Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

                        }
                    });
                    AlertDialog alert =  alertbuilder.create();
                    alert.show();
                }
                else{
                    ActivityCompat.requestPermissions((Activity) context ,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }

                return false;
            }else{
                return true;
            }


        }else{

            return true;
        }
    }

    public static void showToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
