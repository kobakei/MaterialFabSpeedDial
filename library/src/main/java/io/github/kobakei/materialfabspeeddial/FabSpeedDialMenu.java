package io.github.kobakei.materialfabspeeddial;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;

/**
 * Created by keisuke on 2017/06/12.
 */

public class FabSpeedDialMenu {

    private int itemId;
    private String title;
    @DrawableRes private int drawableId;
    private ColorStateList fabBackgroundColor;

    FabSpeedDialMenu() {

    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ColorStateList getFabBackgroundColor() {
        return fabBackgroundColor;
    }

    public void setFabBackgroundColor(ColorStateList fabBackgroundColor) {
        this.fabBackgroundColor = fabBackgroundColor;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }

    public static class Builder {

        @NonNull private final Context context;

        private int itemId;
        private String title;
        @DrawableRes private int drawableId;
        @ColorRes private int fabBackgroundColorId;

        public Builder(@NonNull Context context) {
            this.context = context;
        }

        public FabSpeedDialMenu build() {
            FabSpeedDialMenu menu = new FabSpeedDialMenu();
            menu.setItemId(itemId);
            menu.setTitle(title);
            menu.setDrawableId(drawableId);
            menu.setFabBackgroundColor(ContextCompat.getColorStateList(context, fabBackgroundColorId));
            return menu;
        }

        public Builder setItemId(int itemId) {
            this.itemId = itemId;
            return this;
        }

        public Builder setTitle(@StringRes int titleId) {
            this.title = context.getString(titleId);
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setDrawable(@DrawableRes int drawableId) {
            this.drawableId = drawableId;
            return this;
        }

        public Builder setFabBackgroundColor(@ColorRes int colorId) {
            this.fabBackgroundColorId = colorId;
            return this;
        }
    }

}
