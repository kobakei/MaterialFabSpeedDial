package io.github.kobakei.materialfabspeeddial;

import android.animation.Animator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.github.kobakei.materialfabspeeddial.internal.FabSpeedDialMenu;

/**
 * Layout class containing {@link FloatingActionButton} with speed dial animation.
 * Created by keisukekobayashi on 2017/06/12.
 */

public class FabSpeedDial extends FrameLayout {

    private Menu menu;

    private FloatingActionButton fabMain;
    private LinearLayout menuContainer;
    private View touchGuard;

    private List<OnMenuClickListener> listeners = new ArrayList<>();

    private boolean isOpened = false;

    private boolean useTouchGuard = true;

    private static final long MAIN_FAB_ROTATE_DURATION = 200L;
    private static final long MINI_FAB_SHOW_DURATION = 100L;
    private static final long MINI_FAB_SHOW_DELAY = 50L;
    private static final float MINI_FAB_SHOW_TRANSLATION = 24.0f;
    private static final long MINI_FAB_DISMISS_DURATION = 100L;

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

        // Key listener to handle BACK key
        setFocusable(true);
        setFocusableInTouchMode(true);
        requestFocus();
        setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (isOpened) {
                        closeMenu();
                        return true;
                    }
                }
                return false;
            }
        });

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

        useTouchGuard = ta.getBoolean(R.styleable.FabSpeedDial_fab_touchGuard, true);

        int touchGuardColor = ta.getColor(R.styleable.FabSpeedDial_fab_touchGuardColor, Color.argb(128, 0, 0, 0));
        touchGuard.setBackgroundColor(touchGuardColor);

        // Menu
        menu = new FabSpeedDialMenu(context);
        int menuId = ta.getResourceId(R.styleable.FabSpeedDial_fab_menu, 0);
        if (menuId > 0) {
            new MenuInflater(context).inflate(menuId, menu);
        }

        refreshMenus();

        ta.recycle();
    }

    private void refreshMenus() {
        menuContainer.removeAllViews();
        for (int i = 0; i < menu.size(); i++) {
            MenuItem menuItem = menu.getItem(i);
            View itemView = createItemView(menuItem);
            menuContainer.addView(itemView);
        }
    }

    @NonNull
    private View createItemView(final MenuItem menuItem) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View itemView = inflater.inflate(R.layout.fab_speed_dial_item, menuContainer, false);

        // Mini FAB
        FloatingActionButton miniFab = (FloatingActionButton) itemView.findViewById(R.id.fab_mini);
        if (menuItem.getIcon() != null) {
            miniFab.setImageDrawable(menuItem.getIcon());
        }
        /*
        if (menuItem.getDrawableTintList() != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                miniFab.setImageTintList(menuItem.getDrawableTintList());
            } else {
                miniFab.setColorFilter(menuItem.getDrawableTintList().getDefaultColor());
            }
        }
        if (menuItem.getFabBackgroundColor() != null) {
            miniFab.setBackgroundTintList(menu.getFabBackgroundColor());
        }
        if (menuItem.getRippleColor() != 0) {
            miniFab.setRippleColor(menuItem.getRippleColor());
        }*/
        miniFab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                for (OnMenuClickListener listener : listeners) {
                    listener.onMenuClick(itemView, menuItem.getItemId());
                }
                closeMenu();
            }
        });

        // TextView
        TextView label = (TextView) itemView.findViewById(R.id.text);
        label.setText(menuItem.getTitle());
        /*
        if (menuItem.getTitleColor() != null) {
            label.setTextColor(menuItem.getTitleColor());
        }
        if (menuItem.getTitleBackgroundDrawableId() > 0) {
            label.setBackgroundResource(menuItem.getTitleBackgroundDrawableId());
        } else {
            label.setBackgroundColor(Color.WHITE);
        }*/
        label.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                for (OnMenuClickListener listener : listeners) {
                    listener.onMenuClick(itemView, menuItem.getItemId());
                }
                closeMenu();
            }
        });

        return itemView;
    }

    /**
     * Open menu
     */
    public void openMenu() {
        if (isOpened) {
            return;
        }
        fabMain.setSelected(true);
        fabMain.animate().rotation(45.0f)
                .setDuration(MAIN_FAB_ROTATE_DURATION)
                .start();

        for (int i = 0; i < menu.size(); i++) {
            View itemView = menuContainer.getChildAt(i);

            itemView.setAlpha(0.0f);
            itemView.setTranslationY(MINI_FAB_SHOW_TRANSLATION);

            itemView.animate()
                    .translationY(0.0f)
                    .alpha(1.0f)
                    .setDuration(MINI_FAB_SHOW_DURATION)
                    .setStartDelay((menu.size() - 1 - i) * MINI_FAB_SHOW_DELAY)
                    .start();
        }

        menuContainer.setVisibility(View.VISIBLE);
        if (useTouchGuard) {
            touchGuard.setVisibility(View.VISIBLE);
        }
        isOpened = true;
    }

    /**
     * Close menu
     */
    public void closeMenu() {
        if (!isOpened) {
            return;
        }
        fabMain.setSelected(false);
        fabMain.animate().rotation(0.0f)
                .setDuration(MAIN_FAB_ROTATE_DURATION)
                .start();

        for (int i = 0; i < menu.size(); i++) {
            final View itemView = menuContainer.getChildAt(i);
            itemView.animate()
                    .alpha(0.0f)
                    .setDuration(MINI_FAB_DISMISS_DURATION)
                    .setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            menuContainer.setVisibility(View.GONE);
                            itemView.animate().setListener(null).start();
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    })
                    .start();
        }

        touchGuard.setVisibility(View.GONE);
        isOpened = false;
    }

    /**
     * Check whether menu is opened or closes
     * @return true if opened, false otherwise
     */
    public boolean isOpeningMenu() {
        return isOpened;
    }

    /**
     * Show main {@link FloatingActionButton}
     */
    public void show() {
        fabMain.show();
    }

    /**
     * Hide main {@link FloatingActionButton}
     */
    public void hide() {
        if (isOpened) {
            closeMenu();
            fabMain.setRotation(0.0f);
        }
        fabMain.hide();
    }

    /**
     * Check whether main {@link FloatingActionButton} is shown or not
     * @return true if main FAB is shown, false otherwise
     */
    public boolean isShown() {
        return fabMain.isShown();
    }

    /**
     * Add event listener
     * @param listener
     */
    public void addOnMenuClickListener(OnMenuClickListener listener) {
        listeners.add(listener);
    }

    /**
     * Remove event listener
     * @param listener
     */
    public void removeOnMenuClickListener(OnMenuClickListener listener) {
        listeners.remove(listener);
    }

    /**
     * Get main {@link FloatingActionButton}
     * @return
     */
    public FloatingActionButton getMainFab() {
        return fabMain;
    }

    public interface OnMenuClickListener {
        void onMenuClick(View view, int itemId);
    }
}
