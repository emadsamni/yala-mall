package com.example.yala_mall.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yala_mall.R;


import java.util.List;

public class SpinnerAdapter2<T> extends BaseAdapter {
    private List<T> list;
    private Context context;
    private Typeface font;

    public SpinnerAdapter2(List<T> list, Context context) {
        this.list = list;
        this.context = context;
        //font = Typeface.createFromAsset(context.getAssets(), Constants.FONT_STYLE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view;

            TextView selectView = new TextView(context);
            selectView.setHeight(60);
            selectView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            selectView.setText(list.get(position).toString());
            selectView.setVisibility(View.VISIBLE);
            view = selectView;


        return view;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        TextView itemView;
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_text_view_spinner,parent,false);
        }else
            view = convertView;

        itemView = view.findViewById(R.id.item_view);
        itemView.setText(list.get(position).toString());
        itemView.setTextColor(view.getResources().getColor(R.color.white));
        //itemView.setTypeface(font);

        return view;
    }
}
