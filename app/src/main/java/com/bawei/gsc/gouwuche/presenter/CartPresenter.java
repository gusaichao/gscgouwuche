package com.bawei.gsc.gouwuche.presenter;

import com.bawei.gsc.gouwuche.bean.CartBean;
import com.bawei.gsc.gouwuche.contract.CartContract;
import com.bawei.gsc.gouwuche.model.CartModle;
import com.google.gson.Gson;

import java.util.HashMap;

public class CartPresenter extends CartContract.ICartPresenter {

    private CartModle cartModle;
    private CartContract.ICartView iCartView;

    public CartPresenter(CartContract.ICartView iCartView) {
        this.iCartView = iCartView;
        cartModle = new CartModle();
    }

    @Override
    public void getCartList(HashMap<String, String> params) {
        cartModle.getCartList(params, new CartModle.ICartModelCallback() {
            @Override
            public void failure(String msg) {
                iCartView.failure(msg);
            }

            @Override
            public void success(String result) {
                CartBean cart = new Gson().fromJson(result, CartBean.class);

                iCartView.success(cart.data);
            }
        });
    }
}
