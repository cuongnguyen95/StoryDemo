package cuongd13.demoandroid.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import cuongd13.demoandroid.R;
import cuongd13.demoandroid.model.Update;

/**
 * Created by cuong pc on 6/19/2017.
 */

public class UpdateAdapter extends ArrayAdapter<Update> {

    Context context;
    List<Update> items;
    int resource;

    public UpdateAdapter(Context context, int resource, List<Update> items) {
        super(context, resource, items);
        this.context = context;
        this.resource = resource;
        this.items = items;
    }

    private class ViewHolder{
        private ImageView imgHinh  ;
        private TextView txtTen  ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.home_row, null);
            viewHolder = new ViewHolder();
            // Anh xa
            viewHolder.imgHinh = (ImageView) view.findViewById(R.id.image);
            viewHolder.txtTen = (TextView) view.findViewById(R.id.title);

            view.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) view.getTag();
        }


        // Gan gia tri
        Picasso.with(context).load(items.get(position).getThumb()).into(viewHolder.imgHinh);
        viewHolder.txtTen.setText(items.get(position).getTitle());




        return view;
    }

}
