package com.bawei.gsc.gouwuche.contract;

import com.bawei.gsc.gouwuche.bean.CartBean;
import com.bawei.gsc.gouwuche.model.CartModle;

import java.util.HashMap;
import java.util.List;

public interface CartContract {

    public abstract class ICartPresenter{
        public abstract void getCartList(HashMap<String,String> params);
    }

    interface ICartModel {
        void getCartList(HashMap<String ,String> params, CartModle.ICartModelCallback cartModelCallback);
    }

    interface ICartView{
        void failure(String msg);
        void success(List<CartBean.Cart> list);
    }
}
