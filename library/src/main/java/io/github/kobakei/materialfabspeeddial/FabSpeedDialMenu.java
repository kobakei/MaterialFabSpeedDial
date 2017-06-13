package io.github.kobakei.materialfabspeeddial;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;

/**
 * Menu class of FAB speed dial
 * Created by keisuke on 2017/06/12.
 */

public class FabSpeedDialMenu {

    private int itemId;
    private String title;
    private ColorStateList titleColor;
    @DrawableRes private int titleBackgroundDrawableId;
    @DrawableRes private int drawableId;
    private ColorStateList drawableTintList;
    private ColorStateList fabBackgroundColor;
    @ColorInt private int rippleColor;

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

    public ColorStateList getDrawableTintList() {
        return drawableTintList;
    }

    public void setDrawableTintList(ColorStateList drawableTint) {
        this.drawableTintList = drawableTint;
    }

    public ColorStateList getTitleColor() {
        return titleColor;
    }

    public void setTitleColor(ColorStateList titleColor) {
        this.titleColor = titleColor;
    }

    public int getRippleColor() {
        return rippleColor;
    }

    public void setRippleColor(int rippleColor) {
        this.rippleColor = rippleColor;
    }

    @Override
    public int hashCode() {
        return itemId;
    }

    public int getTitleBackgroundDrawableId() {
        return titleBackgroundDrawableId;
    }

    public void setTitleBackgroundDrawableId(int titleBackgroundDrawableId) {
        this.titleBackgroundDrawableId = titleBackgroundDrawableId;
    }

    /**
     * Builder class
     */
    public static class Builder {

        @NonNull private final Context context;

        private int itemId;
        private String title;
        @ColorRes private int titleColorId;
        @ColorRes private int titleBackgroundColorId;
        @DrawableRes private int titleBackgroundDrawableId;
        @DrawableRes private int drawableId;
        @ColorRes private int drawableTint;
        @ColorRes private int fabBackgroundColorId;
        @ColorRes private int rippleColorId;

        public Builder(@NonNull Context context) {
            this.context = context;
        }

        public FabSpeedDialMenu build() {
            FabSpeedDialMenu menu = new FabSpeedDialMenu();
            if (itemId == 0) {
                throw new IllegalStateException("itemId must be set");
            }
            menu.setItemId(itemId);
            menu.setTitle(title);
            if (titleColorId > 0) {
                menu.setTitleColor(ContextCompat.getColorStateList(context, titleColorId));
            }
            if (titleBackgroundDrawableId > 0) {
                menu.setTitleBackgroundDrawableId(titleBackgroundDrawableId);
            }
            menu.setDrawableId(drawableId);
            if (drawableTint > 0) {
                menu.setDrawableTintList(ContextCompat.getColorStateList(context, drawableTint));
            }
            if (fabBackgroundColorId > 0) {
                menu.setFabBackgroundColor(ContextCompat.getColorStateList(context, fabBackgroundColorId));
            }
            if (rippleColorId > 0) {
                menu.setRippleColor(ContextCompat.getColor(context, rippleColorId));
            }
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

        public Builder setTitleColor(@ColorRes int colorId) {
            this.titleColorId = colorId;
            return this;
        }

        public Builder setTitleBackgroundDrawable(@DrawableRes int drawableId) {
            this.titleBackgroundDrawableId = drawableId;
            return this;
        }

        public Builder setDrawable(@DrawableRes int drawableId) {
            this.drawableId = drawableId;
            return this;
        }

        public Builder setDrawableTint(@ColorRes int colorId) {
            this.drawableTint = colorId;
            return this;
        }

        public Builder setFabBackgroundColor(@ColorRes int colorId) {
            this.fabBackgroundColorId = colorId;
            return this;
        }

        public Builder setRippleColor(@ColorRes int colorId) {
            this.rippleColorId = colorId;
            return this;
        }
    }

}
