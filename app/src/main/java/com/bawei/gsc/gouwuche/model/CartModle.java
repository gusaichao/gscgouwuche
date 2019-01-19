package com.bawei.gsc.gouwuche.model;

import com.bawei.gsc.gouwuche.api.Api;
import com.bawei.gsc.gouwuche.contract.CartContract;
import com.bawei.gsc.gouwuche.net.OkHttpCallback;
import com.bawei.gsc.gouwuche.net.OkHttpUtils;

import java.util.HashMap;

public class CartModle implements CartContract.ICartModel {

    @Override
    public void getCartList(HashMap<String, String> params, final ICartModelCallback cartModelCallback) {
        OkHttpUtils.getInstance().doPost(Api.API, params, new OkHttpCallback() {
            @Override
            public void failure(String msg) {
                if (cartModelCallback!=null){
                    cartModelCallback.failure(msg);
                }
            }

            @Override
            public void success(String result) {
                if (cartModelCallback!=null){
                    cartModelCallback.success(result);
                }
            }
        });
    }

    public interface ICartModelCallback{
        void failure(String msg);
        void success(String result);
    }
}
