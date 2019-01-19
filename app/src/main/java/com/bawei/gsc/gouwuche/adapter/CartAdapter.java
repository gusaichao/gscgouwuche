package com.bawei.gsc.gouwuche.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bawei.gsc.gouwuche.R;
import com.bawei.gsc.gouwuche.bean.CartBean;
import com.bawei.gsc.gouwuche.callback.CartCallback;
import com.bawei.gsc.gouwuche.callback.CartUICallback;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

public class CartAdapter extends XRecyclerView.Adapter<CartAdapter.MyVh> implements CartCallback {

    private List<CartBean.Cart> list;
    private Context context;
    private ProductAdapter productAdapter;

    public CartAdapter(List<CartBean.Cart> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void AddList(List<CartBean.Cart> list) {
        if (list!=null){
            this.list.addAll(list);
            notifyDataSetChanged();
        }
    }

    private CartUICallback cartUICallback;

    public void setCartUICallback(CartUICallback cartUICallback) {
        this.cartUICallback = cartUICallback;
    }

    @NonNull
    @Override
    public MyVh onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_layout_item,viewGroup,false);
        return new MyVh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyVh myVh, int i) {
        final CartBean.Cart cart = list.get(i);
        myVh.tv_title.setText(cart.sellerName);
        myVh.xRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        productAdapter = new ProductAdapter(cart.list, context);
        productAdapter.setCartCallback(this);
        myVh.xRecyclerView.setAdapter(productAdapter);
        myVh.checkBox.setChecked(cart.isChecked);
        myVh.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cart.isChecked=myVh.checkBox.isChecked();
                for (CartBean.Cart.Product product : cart.list) {
                    product.isProductChecked=cart.isChecked;

                }
                notifyDataSetChanged();
                if (cartUICallback!=null){
                    cartUICallback.notyprice();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    @Override
    public void CartCallback(boolean isChecked, int pos) {
        list.get(pos).isChecked=isChecked;
        notifyDataSetChanged();
        if (cartUICallback!=null){
            cartUICallback.notyprice();
        }
    }

    @Override
    public void notynums() {
        if (cartUICallback!=null){
            cartUICallback.notyprice();
        }
    }

    public class MyVh extends RecyclerView.ViewHolder {

        private final TextView tv_title;
        private final CheckBox checkBox;
        private final XRecyclerView xRecyclerView;

        public MyVh(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            checkBox = itemView.findViewById(R.id.checkbox);
            xRecyclerView = itemView.findViewById(R.id.rv);
        }
    }
}
