package io.github.kobakei.materialfabspeeddial.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.RestrictTo;
import android.support.annotation.StringRes;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by keisuke on 2017/06/13.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
public class FabSpeedDialMenu implements Menu {

    private final Context context;

    private List<MenuItem> menuItems = new ArrayList<>();

    public FabSpeedDialMenu(Context context) {
        this.context = context;
    }

    @Override
    public MenuItem add(CharSequence title) {
        throw new RuntimeException("");
    }

    @Override
    public MenuItem add(@StringRes int titleRes) {
        throw new RuntimeException("");
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
        throw new RuntimeException("");
    }

    @Override
    public SubMenu addSubMenu(CharSequence title) {
        throw new RuntimeException("");
    }

    @Override
    public SubMenu addSubMenu(@StringRes int titleRes) {
        throw new RuntimeException("");
    }

    @Override
    public SubMenu addSubMenu(int groupId, int itemId, int order, CharSequence title) {
        throw new RuntimeException("");
    }

    @Override
    public SubMenu addSubMenu(int groupId, int itemId, int order, @StringRes int titleRes) {
        throw new RuntimeException("");
    }

    @Override
    public int addIntentOptions(int groupId, int itemId, int order, ComponentName caller, Intent[] specifics, Intent intent, int flags, MenuItem[] outSpecificItems) {
        throw new RuntimeException("");
    }

    @Override
    public void removeItem(int id) {
        throw new RuntimeException("");
    }

    @Override
    public void removeGroup(int groupId) {
        throw new RuntimeException("");
    }

    @Override
    public void clear() {
        throw new RuntimeException("");
    }

    @Override
    public void setGroupCheckable(int group, boolean checkable, boolean exclusive) {
        throw new RuntimeException("");
    }

    @Override
    public void setGroupVisible(int group, boolean visible) {
        throw new RuntimeException("");
    }

    @Override
    public void setGroupEnabled(int group, boolean enabled) {
        throw new RuntimeException("");
    }

    @Override
    public boolean hasVisibleItems() {
        throw new RuntimeException("");
    }

    @Override
    public MenuItem findItem(int id) {
        throw new RuntimeException("");
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
        throw new RuntimeException("");
    }

    @Override
    public boolean performShortcut(int keyCode, KeyEvent event, int flags) {
        throw new RuntimeException("");
    }

    @Override
    public boolean isShortcutKey(int keyCode, KeyEvent event) {
        throw new RuntimeException("");
    }

    @Override
    public boolean performIdentifierAction(int id, int flags) {
        throw new RuntimeException("");
    }

    @Override
    public void setQwertyMode(boolean isQwerty) {
        throw new RuntimeException("");
    }
}
