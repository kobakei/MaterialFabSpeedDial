package io.github.kobakei.materialfabspeeddial;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.util.SortedList;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Menu class of FAB speed dial.
 * This class implements {@link Menu} interface but some methods are marked as deprecated and not implemented.
 *
 * Created by keisuke on 2017/06/13.
 */
public class FabSpeedDialMenu implements Menu {

    @NonNull
    private final Context context;

    private SortedList<MenuItem> menuItems = new SortedList<>(MenuItem.class, new SortedList.Callback<MenuItem>() {
        @Override
        public int compare(MenuItem o1, MenuItem o2) {
            return o1.getOrder() - o2.getOrder();
        }

        @Override
        public void onChanged(int position, int count) {

        }

        @Override
        public boolean areContentsTheSame(MenuItem oldItem, MenuItem newItem) {
            return false;
        }

        @Override
        public boolean areItemsTheSame(MenuItem item1, MenuItem item2) {
            return false;
        }

        @Override
        public void onInserted(int position, int count) {

        }

        @Override
        public void onRemoved(int position, int count) {

        }

        @Override
        public void onMoved(int fromPosition, int toPosition) {

        }
    });

    public FabSpeedDialMenu(@NonNull Context context) {
        this.context = context;
    }

    @Override
    public MenuItem add(CharSequence title) {
        int itemId = menuItems.size() + 1;
        return add(0, itemId, itemId, title);
    }

    @Override
    public MenuItem add(@StringRes int titleRes) {
        int itemId = menuItems.size() + 1;
        return add(0, itemId, itemId, titleRes);
    }

    @Override
    public MenuItem add(int groupId, int itemId, int order, CharSequence title) {
        MenuItem menuItem = new FabSpeedDialMenuItem(context, itemId, groupId, order);
        menuItem.setTitle(title);
        menuItems.add(menuItem);
        return menuItem;
    }

    @Override
    public MenuItem add(int groupId, int itemId, int order, @StringRes int titleRes) {
        MenuItem menuItem = new FabSpeedDialMenuItem(context, itemId, groupId, order);
        menuItem.setTitle(titleRes);
        menuItems.add(menuItem);
        return menuItem;
    }

    @Deprecated
    @Override
    public SubMenu addSubMenu(CharSequence title) {
        throw new RuntimeException("Not implemented");
    }

    @Deprecated
    @Override
    public SubMenu addSubMenu(@StringRes int titleRes) {
        throw new RuntimeException("Not implemented");
    }

    @Deprecated
    @Override
    public SubMenu addSubMenu(int groupId, int itemId, int order, CharSequence title) {
        throw new RuntimeException("Not implemented");
    }

    @Deprecated
    @Override
    public SubMenu addSubMenu(int groupId, int itemId, int order, @StringRes int titleRes) {
        throw new RuntimeException("Not implemented");
    }

    @Deprecated
    @Override
    public int addIntentOptions(int groupId, int itemId, int order, ComponentName caller, Intent[] specifics, Intent intent, int flags, MenuItem[] outSpecificItems) {
        throw new RuntimeException("Not implemented");
    }

    @Deprecated
    @Override
    public void removeItem(int id) {
        throw new RuntimeException("Not implemented");
    }

    @Deprecated
    @Override
    public void removeGroup(int groupId) {
        throw new RuntimeException("Not implemented");
    }

    @Deprecated
    @Override
    public void clear() {
        throw new RuntimeException("Not implemented");
    }

    @Deprecated
    @Override
    public void setGroupCheckable(int group, boolean checkable, boolean exclusive) {
        throw new RuntimeException("Not implemented");
    }

    @Deprecated
    @Override
    public void setGroupVisible(int group, boolean visible) {
        throw new RuntimeException("Not implemented");
    }

    @Deprecated
    @Override
    public void setGroupEnabled(int group, boolean enabled) {
        throw new RuntimeException("Not implemented");
    }

    @Deprecated
    @Override
    public boolean hasVisibleItems() {
        throw new RuntimeException("Not implemented");
    }

    @Deprecated
    @Override
    public MenuItem findItem(int id) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public int size() {
        return menuItems.size();
    }

    @Override
    public MenuItem getItem(int index) {
        return menuItems.get(index);
    }

    @Override
    public void close() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public boolean performShortcut(int keyCode, KeyEvent event, int flags) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public boolean isShortcutKey(int keyCode, KeyEvent event) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public boolean performIdentifierAction(int id, int flags) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void setQwertyMode(boolean isQwerty) {
        throw new RuntimeException("Not implemented");
    }
}
