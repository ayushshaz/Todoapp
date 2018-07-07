package com.example.anurag.todoapp;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class MyBaseAdapter extends BaseAdapter {

    ArrayList<MatchDetail> myList = new ArrayList();
    LayoutInflater inflater;
    Context context;

    public MyBaseAdapter(Context context, ArrayList<MatchDetail> myList) {
        this.myList = myList;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return myList.size();
    }

    @Override
    public Object getItem(int position) {
        return myList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder mViewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_list_item, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        MatchDetail listvisible = (MatchDetail) getItem(position);

        mViewHolder.listmatchno.setText(listvisible.getMatch());
        mViewHolder.listicon1.setImageBitmap(listvisible.getTeamOneImage());
        mViewHolder.listteam1.setText(listvisible.getTeamOneName());
        mViewHolder.listteam2.setText(listvisible.getTeamTwoName());
        mViewHolder.listicon2.setImageBitmap(listvisible.getTeamTwoImage());


        return convertView;
    }

    private class MyViewHolder {
        TextView listmatchno,listteam1,listteam2;
        ImageView listicon1,listicon2;

        public MyViewHolder(View item) {
            listmatchno = (TextView) item.findViewById(R.id.listmatchno);
            listteam1 = (TextView) item.findViewById(R.id.listteam1);
            listteam2 = (TextView) item.findViewById(R.id.listteam2);
            listicon1 = (ImageView) item.findViewById(R.id.listicon1);
            listicon2 = (ImageView) item.findViewById(R.id.listicon2);
        }
    }
}
