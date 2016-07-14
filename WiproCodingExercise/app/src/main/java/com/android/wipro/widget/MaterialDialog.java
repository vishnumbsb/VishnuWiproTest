package com.android.wipro.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


import com.android.wipro.R;
import com.android.wipro.util.Utils;


public class MaterialDialog extends Dialog implements DialogInterface {

    private static Context tmpContext;
    private static MaterialDialog instance;

    private View mDialogView;
    private static MaterialDialogListener mListener;

    private TextView mTitle;
    private TextView mMessage;

    private Button mButton1;
    private Button mButton2;

    private Context mContext;

    private boolean mIsTablet;

    public MaterialDialog(Context context) {
        super(context);
        mContext = context;
        init(context);
    }


    public MaterialDialog(Context context, int theme) {
        super(context, theme);
        mContext = context;
        init(context);
    }

    public static MaterialDialog getInstance(Context context) {
        try {
            if (instance == null || !tmpContext.equals(context)) {
                synchronized (MaterialDialog.class) {
                    if (instance == null || !tmpContext.equals(context)) {
                        instance = new MaterialDialog(context, R.style.MaterialDialog);
                        mListener = (MaterialDialogListener) context;
                    }
                }
            }
            tmpContext = context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement CustomDialogListener");
        }
        return instance;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        init(mContext);

        mIsTablet = Utils.isTablet(mContext);

        if (mIsTablet) {
            WindowManager.LayoutParams params = getWindow().getAttributes();
            params.height = mContext.getResources().getDimensionPixelSize(R.dimen.floating_height);
            params.width = mContext.getResources().getDimensionPixelSize(R.dimen.floating_width);
            getWindow().setAttributes((WindowManager.LayoutParams) params);
        } else {
            WindowManager.LayoutParams params = getWindow().getAttributes();
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            getWindow().setAttributes((WindowManager.LayoutParams) params);
        }


    }


    private void init(Context context) {
        mDialogView = View.inflate(context, R.layout.dialog_layout, null);

        mTitle = (TextView) mDialogView.findViewById(R.id.dialogTitle);
        mMessage = (TextView) mDialogView.findViewById(R.id.dialogMessage);

        mButton1 = (Button) mDialogView.findViewById(R.id.button1);
        mButton2 = (Button) mDialogView.findViewById(R.id.button2);

        this.setCancelable(false);
        this.setCanceledOnTouchOutside(false);

        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDialogPositiveClick(instance, Integer.valueOf((String) v.getTag()));
            }
        });

        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDialogNegativeClick(instance, Integer.valueOf((String) v.getTag()));
            }
        });

        setContentView(mDialogView);
    }

    public MaterialDialog withTitle(CharSequence title) {
        mTitle.setText(title);
        return this;
    }

    public MaterialDialog withMessage(int textResId) {
        mMessage.setText(textResId);
        return this;
    }

    public MaterialDialog withMessage(CharSequence msg) {
        mMessage.setText(msg);
        return this;
    }

    public MaterialDialog withTag(Object tag) {
        mButton1.setTag(tag);
        mButton2.setTag(tag);
        return this;
    }

    public MaterialDialog isButton1Visible(int visibility) {
        mButton1.setVisibility(visibility);
        return this;
    }

    public MaterialDialog isButton2Visible(int visibility) {
        mButton2.setVisibility(visibility);
        return this;
    }

    public MaterialDialog setButton1Click(View.OnClickListener click) {
        mButton1.setOnClickListener(click);
        return this;
    }

    public MaterialDialog setButton2Click(View.OnClickListener click) {
        mButton2.setOnClickListener(click);
        return this;
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    public interface MaterialDialogListener {
        public void onDialogPositiveClick(Dialog dialog, int tagid);

        public void onDialogNegativeClick(Dialog dialog, int tagid);
    }
}
