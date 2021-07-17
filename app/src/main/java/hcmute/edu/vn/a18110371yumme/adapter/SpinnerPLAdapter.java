package hcmute.edu.vn.a18110371yumme.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import hcmute.edu.vn.a18110371yumme.R;
import hcmute.edu.vn.a18110371yumme.models.Type;

public class SpinnerPLAdapter extends BaseAdapter {
    Context context;
    List<? extends Type> list;

    public SpinnerPLAdapter(Context context, List<? extends Type> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }
    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View v=inflater.inflate(R.layout.spinner_layout,parent,false);
        TextView tv_id=v.findViewById(R.id.sp_id);
        TextView tv_Loai=v.findViewById(R.id.sp_name);
        tv_id.setText(String.valueOf(position+1));
        tv_Loai.setText(list.get(position).getTypeName());
        return v;
    }
}
