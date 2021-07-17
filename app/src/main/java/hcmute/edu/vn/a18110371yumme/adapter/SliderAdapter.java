package hcmute.edu.vn.a18110371yumme.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;

import hcmute.edu.vn.a18110371yumme.R;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderAdapterVH> {

    private Context context;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.paper_item, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, int position) {

        switch (position) {
            case 0:
                Glide.with(viewHolder.itemView)
                        .load(R.drawable.slider6)
                        .into(viewHolder.imageViewBackground);
                break;
            case 1:
                Glide.with(viewHolder.itemView)
                        .load(R.drawable.slider7)
                        .into(viewHolder.imageViewBackground);
                break;
            case 2:
                Glide.with(viewHolder.itemView)
                        .load(R.drawable.slider3)
                        .into(viewHolder.imageViewBackground);
                break;
            default:
                Glide.with(viewHolder.itemView)
                        .load(R.drawable.slider5)
                        .into(viewHolder.imageViewBackground);
                break;
        }
    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return 4;
    }

    public class SliderAdapterVH extends SliderViewAdapter.ViewHolder{
        View itemView;
        ImageView imageViewBackground;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.image);
            this.itemView = itemView;
        }
    }
}