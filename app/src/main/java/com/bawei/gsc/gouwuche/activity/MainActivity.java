package com.bawei.gsc.gouwuche.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.bawei.gsc.gouwuche.R;
import com.bawei.gsc.gouwuche.adapter.CartAdapter;
import com.bawei.gsc.gouwuche.bean.CartBean;
import com.bawei.gsc.gouwuche.callback.CartUICallback;
import com.bawei.gsc.gouwuche.contract.CartContract;
import com.bawei.gsc.gouwuche.presenter.CartPresenter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CartContract.ICartView,CartUICallback,XRecyclerView.LoadingListener {

    private XRecyclerView xRecyclerView;
    private CheckBox checkBox;
    private CartPresenter cartPresenter;
    private int page = 1;
    private CartAdapter cartAdapter;
    private List<CartBean.Cart> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        xRecyclerView = findViewById(R.id.rv);
        checkBox = findViewById(R.id.qx);
        xRecyclerView.setLoadingListener(this);
        xRecyclerView.setLoadingMoreEnabled(true);
        xRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        initData();
    }

    private void initData() {
        cartPresenter = new CartPresenter(this);
        requestData();
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    for (CartBean.Cart datum : data) {
                        datum.isChecked=true;
                        for (CartBean.Cart.Product product : datum.list) {
                            product.isProductChecked=true;
                        }
                    }
                }else {
                    for (CartBean.Cart datum : data) {
                        datum.isChecked=false;
                        for (CartBean.Cart.Product product : datum.list) {
                            product.isProductChecked=false;
                        }
                    }
                }
                cartAdapter.notifyDataSetChanged();
                getprice();
            }
        });

    }
    private void requestData(){
        HashMap<String,String> params = new HashMap<>();
        params.put("uid","71");
        params.put("page",page+"");
        cartPresenter.getCartList(params);
    }

    public void getprice() {
        double price=0;
        for (CartBean.Cart datum : data) {
            for (CartBean.Cart.Product product : datum.list) {
                if (product.isProductChecked) {
                    price += product.price * product.productNum;
                }
            }
        }
        checkBox.setText("￥："+price);
    }
    @Override
    public void failure(String msg) {

    }

    @Override
    public void success(List<CartBean.Cart> list) {
        if (list!=null){
            data=list;
            for (CartBean.Cart datum : data) {
                for (CartBean.Cart.Product product : datum.list) {
                    product.productNum=1;
                }
            }
        }
        //list.remove(0);
        if (page==1){
            xRecyclerView.refreshComplete();
            cartAdapter = new CartAdapter(list, this);
            xRecyclerView.setAdapter(cartAdapter);
            cartAdapter.setCartUICallback(this);
        }else {
            if (cartAdapter==null){
                cartAdapter = new CartAdapter(list, this);
                xRecyclerView.setAdapter(cartAdapter);
                cartAdapter.setCartUICallback(this);
            }else {
                cartAdapter.AddList(list);
            }
            xRecyclerView.loadMoreComplete();
        }

    }

    @Override
    public void notyprice() {
        getprice();
    }

    @Override
    public void onRefresh() {
        page=1;
        requestData();
    }

    @Override
    public void onLoadMore() {
        page++;
        requestData();
    }
}
