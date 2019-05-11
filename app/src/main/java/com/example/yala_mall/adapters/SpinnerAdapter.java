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

public class SpinnerAdapter<T> extends BaseAdapter {
    private List<T> list;
    private Context context;
    private Typeface font;

    public SpinnerAdapter(List<T> list, Context context) {
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
        if (position == 0) {
            TextView selectView = new TextView(context);
            selectView.setHeight(0);
            selectView.setVisibility(View.GONE);
            view = selectView;
        } else
            view = super.getDropDownView(position, null, parent);

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
        itemView.setTextColor(view.getResources().getColor(R.color.colorPrimary));
        //itemView.setTypeface(font);

        return view;
    }
}
