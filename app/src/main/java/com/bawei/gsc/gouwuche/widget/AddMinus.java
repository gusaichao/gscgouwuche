package com.bawei.gsc.gouwuche.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.gsc.gouwuche.R;

public class AddMinus extends LinearLayout {

    private TextView tv_jian;
    private TextView tv_jia;
    private EditText ed_num;
    private int num=1;
    public AddMinus(Context context) {
        super(context);
        init(context);
    }

    public AddMinus(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AddMinus(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(final Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.add_minus_layout,this,true);
        tv_jian = view.findViewById(R.id.tv_jian);
        tv_jia = view.findViewById(R.id.tv_jia);
        ed_num = view.findViewById(R.id.ed_num);
        ed_num.setText("1");

        tv_jia.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                num++;
                ed_num.setText(num+"");
                if (addminusCallback!=null){
                    addminusCallback.addCallback(num);
                }
            }
        });
        tv_jian.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                num--;
                if (num==0){
                    num=1;
                    Toast.makeText(context,"不能再减了 ",Toast.LENGTH_SHORT).show();
                }
                ed_num.setText(num+"");
                if (addminusCallback!=null){
                    addminusCallback.addCallback(num);
                }
            }
        });

    }
    private AddminusCallback addminusCallback;

    public void setAddminusCallback(AddminusCallback addminusCallback) {
        this.addminusCallback = addminusCallback;
    }

    public interface AddminusCallback{
        void addCallback(int num);
    }
}
