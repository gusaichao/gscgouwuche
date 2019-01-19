package com.bawei.gsc.gouwuche.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawei.gsc.gouwuche.R;
import com.bawei.gsc.gouwuche.bean.CartBean;
import com.bawei.gsc.gouwuche.callback.CartCallback;
import com.bawei.gsc.gouwuche.widget.AddMinus;
import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

public class ProductAdapter extends XRecyclerView.Adapter<ProductAdapter.MyVh> {

    private List<CartBean.Cart.Product> list;
    private Context context;
    private CartCallback cartCallback;

    public void setCartCallback(CartCallback cartCallback) {
        this.cartCallback = cartCallback;
    }

    public ProductAdapter(List<CartBean.Cart.Product> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyVh onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_layout_item,viewGroup,false);
        return new MyVh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyVh myVh, final int i) {
        final CartBean.Cart.Product product = list.get(i);
        myVh.tv_title.setText(product.title);
        myVh.tv_price.setText(product.price+"");
        String[] im = product.images.split("\\|");
        Glide.with(context).load(im[0]).into(myVh.iv);
        myVh.checkBox.setChecked(product.isProductChecked);

        myVh.addMinus.setAddminusCallback(new AddMinus.AddminusCallback() {
            @Override
            public void addCallback(int num) {
                product.productNum=num;
                if (cartCallback!=null){
                    cartCallback.notynums();
                }
            }
        });
        myVh.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.get(i).isProductChecked=myVh.checkBox.isChecked();
                if (cartCallback!=null){
                    for (CartBean.Cart.Product product1 : list) {
                        if (product1.isProductChecked==false){
                            cartCallback.CartCallback(false,product1.pos);
                            return;
                        }
                        cartCallback.CartCallback(true,product1.pos);
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    public class MyVh extends RecyclerView.ViewHolder {

        private final CheckBox checkBox;
        private final TextView tv_title;
        private final TextView tv_price;
        private final ImageView iv;
        private final AddMinus addMinus;

        public MyVh(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkbox);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_price = itemView.findViewById(R.id.tv_price);
            iv = itemView.findViewById(R.id.iv_product);
            addMinus = itemView.findViewById(R.id.addminus);
        }
    }
}
