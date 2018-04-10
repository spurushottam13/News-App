package app1.com.example.purushottam.e_news;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Purushottam on 04-04-2017.
 */

public class reviewsadapter extends ArrayAdapter<reviews> {
    private ArrayList<reviews> reviewList;
    private LayoutInflater vi;
    private int Resource;
    private ViewHolder holder;
    // Button soww;
    View view;
    //ArrayList<Reviews> cp=0;
    int gen=0;
    reviewsadapter(Context context, int resource, ArrayList<reviews> objects) {
        super(context, resource, objects);
        vi = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource = resource;
        this.reviewList = objects;

    }
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        View v = convertView;

        // if view is null then set below views


        if (v == null) {
            holder = new ViewHolder();
            v = vi.inflate(Resource, null);

            //binding holder variables to xml views

            holder.tvtitle = (TextView) v.findViewById(R.id.title);
            holder.tvdescription = (TextView) v.findViewById(R.id.description);
            holder.tvauthor = (TextView) v.findViewById(R.id.author);


            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        //setting data to the holder vars from "Reviews" getters

        holder.tvtitle.setText(reviewList.get(position).getTitle());
        holder.tvdescription.setText(reviewList.get(position).getDescription());
        holder.tvauthor.setText("Published At:"+reviewList.get(position).getAuthor());

        return v;

    }

    private static class ViewHolder {

        TextView tvtitle;
        TextView tvdescription;
        TextView tvauthor;
    }
}
