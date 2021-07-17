package hcmute.edu.vn.a18110371yumme.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

import hcmute.edu.vn.a18110371yumme.DAO.TypeDAO;
import hcmute.edu.vn.a18110371yumme.R;
import hcmute.edu.vn.a18110371yumme.models.Type;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {

    List<Type> list;
    Context context;
    TypeDAO typeDAO;
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();

    public CategoriesAdapter(List<Type> list, Context context){
        this.list = list;
        this.context = context;
        typeDAO = new TypeDAO(context);
    }

    public CategoriesAdapter(FragmentActivity activity) {

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.category_one_item,parent,false);
        typeDAO= new TypeDAO(context);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.with(context).load(list.get(position).getImage()).into(holder.CategoriesImage);
        holder.CategoriesName.setText(list.get(position).getTypeName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView CategoriesName;
        public ImageView CategoriesImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            CategoriesName = itemView.findViewById(R.id.category_name);
            CategoriesImage = itemView.findViewById(R.id.category_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }


}



//    private List<Category> categories = new ArrayList<>();
//    private Context context;
//    private OnItemClickListener mListener;
//    public void setOnItemClickListener(OnItemClickListener listener){
//        mListener = listener;
//    }
//    public CategoriesAdapter(Context context){
//        this.context = context;
//        String[] categoryNames = {"Cơm Phần", "Trà Sữa",
//                "Gà Rán", "Bún/Phở","Ăn Vặt", "Món Hàn"};
//
//        int images_array[] = {
//                R.drawable.rice,
//                R.drawable.milk,
//                R.drawable.ic_fried_chicken,
//                R.drawable.ic_noodles,
//                R.drawable.ic_snack,
//                R.drawable.ic_koreanfood,
//        };
//
//        for (int i = 0; i < 6; i++){
//            Category category = new Category(categoryNames[i], images_array[i]);
//            categories.add(category);
//        }
//    }
//
//    @NonNull
//    @Override
//    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        View view = LayoutInflater.from(context).inflate(R.layout.theloai_one_item, viewGroup, false);
//        ItemHolder holder = new ItemHolder(view, mListener);
//        return holder;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ItemHolder holder, final int position) {
//        final Category category =  categories.get(position);
//        holder.mCategoryName.setText(category.getCategoryName());
//        holder.mCategoryImage.setImageResource(category.getCategoryDrawable());
//    }
//
//    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
//        public TextView mCategoryName;
//        public ImageView mCategoryImage;
//        public View mView;
//
//
//        public ItemHolder(@NonNull View itemView, final OnItemClickListener listener) {
//            super(itemView);
//            mView = itemView;
//            mCategoryName = itemView.findViewById(R.id.category_name);
//            mCategoryImage = itemView.findViewById(R.id.category_photo);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (listener != null) {
//                        int position = getAdapterPosition();
//                        if (position != RecyclerView.NO_POSITION){
//                            listener.onItemClick(position);
//                        }
//                    }
//                }
//            });
//        }
//
//        @Override
//        public void onClick(View v) {}
//    }
//
//    public interface OnItemClickListener {
//        void onItemClick(int position);
//    }
//
//    @Override
//    public int getItemCount() {
//        return categories.size();
//    }
