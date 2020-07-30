package com.cnr.cnrmodule.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.cnr.cnrmodule.R;

public class MainTabButton extends FrameLayout {

    TextView tabBar;
    TextView unreadNumber;
    int tabIconId = 0;
    int tabIconSelectId = 0;

    public MainTabButton(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public MainTabButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MainTabButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MainTabButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MainTabButton);
        tabIconId = ta.getResourceId(R.styleable.MainTabButton_tabIcon, 0);
        tabIconSelectId = ta.getResourceId(R.styleable.MainTabButton_tabSelectedIcon, 0);
        String tabTxt = ta.getString(R.styleable.MainTabButton_tabTxt);
        ta.recycle();

        LayoutInflater.from(context).inflate(R.layout.main_tab_button, this);
        tabBar = findViewById(R.id.tabbar);
        unreadNumber = findViewById(R.id.unreadNumber);
        if (tabIconId != 0) {
            setTabTopDrawable(tabIconId);
        }
        if (!TextUtils.isEmpty(tabTxt)) {
            tabBar.setText(tabTxt);
        }
    }

    /**
     * 设置未读数字
     *
     * @param number
     * @return
     */
    public void setUnreadNumber(int number) {
        if (number > 0) {
            unreadNumber.setVisibility(View.VISIBLE);
        } else {
            unreadNumber.setVisibility(View.GONE);
        }
        unreadNumber.setText(String.valueOf(number));
    }

    /**
     * 设置小红点
     *
     * @param isVisible
     */
    public void setRedOvalVisible(int isVisible) {
        unreadNumber.setVisibility(isVisible);
    }

    /**
     * 设置是否选中
     *
     * @param selected
     * @return
     */
    public void setHasSelected(boolean selected) {
        tabBar.setSelected(selected);
        if (tabIconId != 0 && tabIconSelectId != 0) {
            setTabTopDrawable(selected ? tabIconSelectId : tabIconId);
        }
    }

    public void setTabTopDrawable(int resId) {
        Drawable drawable = getResources().getDrawable(resId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tabBar.setCompoundDrawables(null, drawable, null, null);
    }
}
