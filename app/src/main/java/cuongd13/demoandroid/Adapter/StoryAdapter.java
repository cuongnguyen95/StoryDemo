package cuongd13.demoandroid.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import cuongd13.demoandroid.R;
import cuongd13.demoandroid.model.Detail;

/**
 * Created by cuong pc on 6/19/2017.
 */

public class StoryAdapter extends ArrayAdapter<Detail> {

    Context context;
    List<Detail> items;
    int resource;

    public StoryAdapter(Context context, int resource, List<Detail> items) {
        super(context, resource, items);
        this.context = context;
        this.resource = resource;
        this.items = items;
    }

    private class ViewHolder{
        private TextView txtTen  ;
        private Button btnXemThem  ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.detail_row, null);
            viewHolder = new ViewHolder();
            // Anh xa
            viewHolder.txtTen = (TextView) view.findViewById(R.id.ten);

            view.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) view.getTag();
        }


        // Gan gia tri
        viewHolder.txtTen.setText(items.get(position).getTitle());

        return view;
    }

}
