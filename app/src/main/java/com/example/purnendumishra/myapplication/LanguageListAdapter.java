package com.example.purnendumishra.myapplication;

import android.content.Context;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.purnendumishra.service.request.RestLangList;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Purnendu Mishra on 8/26/2016.
 */
public class LanguageListAdapter extends ArrayAdapter<RestLangList> {
    Context mContext;
    ArrayList<RestLangList> users;
    public LanguageListAdapter(Context context, ArrayList<RestLangList> users) {
        super(context, 0, users);
        mContext=context;
         this.users=users;
    }

    public LanguageListAdapter(Context applicationContext, List<RestLangList> restLangList) {
        super(applicationContext, 0, restLangList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        RestLangList user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.language_item, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.title);
        // Populate the data into the template view using the data object
        tvName.setText(base64Decode(user.getLangReal()));
        // Return the completed view to render on screen
        return convertView;
    }

    public  String base64Decode(String token) {
        String text="";
        byte[] data = Base64.decode(token, Base64.DEFAULT);

        try {
            text = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return text;
    }
}
