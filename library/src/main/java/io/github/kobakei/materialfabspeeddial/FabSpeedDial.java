package io.github.kobakei.materialfabspeeddial;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.view.menu.MenuBuilder;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by keisukekobayashi on 2017/06/12.
 */

public class FabSpeedDial extends FrameLayout {

    private List<FabSpeedDialMenu> menus = new ArrayList<>();

    private FloatingActionButton fabMain;
    private LinearLayout menuContainer;
    private View touchGuard;
    private List<View> itemViews = new ArrayList<>();

    private List<OnMenuClickListener> listeners = new ArrayList<>();

    private boolean isOpened = false;

    public FabSpeedDial(@NonNull Context context) {
        super(context);
        initLayout(context, null, 0);
    }

    public FabSpeedDial(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initLayout(context, attrs, 0);
    }

    public FabSpeedDial(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout(context, attrs, defStyleAttr);
    }

    private void initLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fab_speed_dial, this, false);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.BOTTOM | Gravity.END;
        addView(view, params);

        fabMain = (FloatingActionButton) findViewById(R.id.fab_main);
        fabMain.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpened) {
                    closeMenu();
                } else {
                    openMenu();
                }
            }
        });

        menuContainer = (LinearLayout) findViewById(R.id.menu_container);

        touchGuard = findViewById(R.id.touch_guard);
        touchGuard.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                closeMenu();
            }
        });

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FabSpeedDial, defStyleAttr, 0);

        // Read attrs
        Drawable drawable = ta.getDrawable(R.styleable.FabSpeedDial_fab_fabDrawable);
        if (drawable != null) {
            fabMain.setImageDrawable(drawable);
        }

        ColorStateList fabBackgroundColor = ta.getColorStateList(R.styleable.FabSpeedDial_fab_fabBackgroundColor);
        if (fabBackgroundColor != null) {
            fabMain.setBackgroundTintList(fabBackgroundColor);
        }

        ColorStateList fabDrawableTint = ta.getColorStateList(R.styleable.FabSpeedDial_fab_fabDrawableTint);
        if (fabDrawableTint != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                fabMain.setImageTintList(fabDrawableTint);
            } else {
                fabMain.setColorFilter(fabDrawableTint.getDefaultColor());
            }
        }

        int rippleColor = ta.getColor(R.styleable.FabSpeedDial_fab_rippleColor, Color.WHITE);
        fabMain.setRippleColor(rippleColor);

        ta.recycle();
    }

    public void addMenu(FabSpeedDialMenu menu) {
        menus.add(menu);
        refreshMenus();
    }

    private void refreshMenus() {
        menuContainer.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(getContext());
        for (final FabSpeedDialMenu menu : menus) {
            final View itemView = inflater.inflate(R.layout.fab_speed_dial_item, menuContainer, false);

            // Mini FAB
            FloatingActionButton miniFab = (FloatingActionButton) itemView.findViewById(R.id.fab_mini);
            if (menu.getDrawableId() > 0) {
                miniFab.setImageResource(menu.getDrawableId());
            }
            if (menu.getDrawableTintList() != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    miniFab.setImageTintList(menu.getDrawableTintList());
                } else {
                    miniFab.setColorFilter(menu.getDrawableTintList().getDefaultColor());
                }
            }
            if (menu.getFabBackgroundColor() != null) {
                miniFab.setBackgroundTintList(menu.getFabBackgroundColor());
            }
            if (menu.getRippleColor() != 0) {
                miniFab.setRippleColor(menu.getRippleColor());
            }
            miniFab.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (OnMenuClickListener listener : listeners) {
                        listener.onMenuClick(itemView, menu.getItemId());
                    }
                    closeMenu();
                }
            });

            // TextView
            TextView label = (TextView) itemView.findViewById(R.id.text);
            label.setText(menu.getTitle());
            if (menu.getTitleColor() != null) {
                label.setTextColor(menu.getTitleColor());
            }
            if (menu.getTitleBackgroundColor() != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    label.setBackgroundTintList(menu.getTitleBackgroundColor());
                } else {
                    label.setBackgroundColor(menu.getTitleBackgroundColor().getDefaultColor());
                }
            }
            label.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (OnMenuClickListener listener : listeners) {
                        listener.onMenuClick(itemView, menu.getItemId());
                    }
                    closeMenu();
                }
            });

            menuContainer.addView(itemView);
            itemViews.add(itemView);
        }
    }

    public void openMenu() {
        fabMain.setSelected(true);
        fabMain.animate().rotation(45.0f)
                .setDuration(300L)
                .start();

        for (int i = itemViews.size() - 1; i >= 0; i--) {
            View itemView = itemViews.get(i);

            itemView.setAlpha(0.0f);
            itemView.setTranslationY(24.0f);

            itemView.animate()
                    .translationY(0.0f)
                    .alpha(1.0f)
                    .setDuration(100L)
                    .setStartDelay((itemViews.size() - 1 - i) * 50L)
                    .start();
        }

        menuContainer.setVisibility(View.VISIBLE);
        touchGuard.setVisibility(View.VISIBLE);
        isOpened = true;
    }

    public void closeMenu() {
        fabMain.setSelected(false);
        fabMain.animate().rotation(0.0f)
                .setDuration(300L)
                .start();

        menuContainer.setVisibility(View.GONE);
        touchGuard.setVisibility(View.GONE);
        isOpened = false;
    }

    public void show() {
        fabMain.show();
    }

    public void hide() {
        fabMain.hide();
    }

    public boolean isShown() {
        return fabMain.isShown();
    }

    public void addOnMenuClickListener(OnMenuClickListener listener) {
        listeners.add(listener);
    }

    public void removeOnMenuClickListener(OnMenuClickListener listener) {
        listeners.remove(listener);
    }

    public interface OnMenuClickListener {
        void onMenuClick(View view, int itemId);
    }
}
