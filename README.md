# MaterialFabSpeedDial

[![](https://jitpack.io/v/kobakei/MaterialFabSpeedDial.svg)](https://jitpack.io/#kobakei/MaterialFabSpeedDial)

This project aims to provide [FloatingActionButton's speed dial pattern in material design guideline](https://material.io/guidelines/components/buttons-floating-action-button.html#buttons-floating-action-button-transitions).

This project is inspired by [fab-speed-dial](https://github.com/yavski/fab-speed-dial) and overcomes some issues of it (ex. using private APIs of support library). This library also supports `CoordinatorLayout` so it works as same as `FloatingActionButton` when `Snackbar` is shown.

## Download

Project build.gradle

```groovy
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```

App build.gradle

```groovy
dependencies {
    compile 'com.github.kobakei:MaterialFabSpeedDial:LATEST_VERSION'
}
```

`LATEST_VERSION` is [![](https://jitpack.io/v/kobakei/MaterialFabSpeedDial.svg)](https://jitpack.io/#kobakei/MaterialFabSpeedDial)

## Getting Started

### Add to layout file

You can put `FabSpeedDial` in your layout XML.

```xml
<io.github.kobakei.materialfabspeeddial.FabSpeedDial
    android:id="@+id/fab"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:fab_menu="@menu/fab"/>
```

Menu file is as below. Supported attributes of menu are `id`, `title`, `icon`, `orderInCategory`, `visible`, `enabled`. Other attributes will be ignored.

```xml
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android">
    <item
        android:id="@+id/one"
        android:title="Cut"
        android:icon="@drawable/ic_action_cut"
        android:orderInCategory="1"/>
    <item
        android:id="@+id/two"
        android:title="Copy"
        android:icon="@drawable/ic_action_copy"
        android:orderInCategory="2"/>
    <item
        android:id="@+id/three"
        android:title="Paste"
        android:icon="@drawable/ic_action_paste"
        android:orderInCategory="3"/>
</menu>
```

Or you can set up menu items in Java.

```java
FabSpeedDialMenu menu = new FabSpeedDialMenu(this);
menu.add("One").setIcon(R.drawable.ic_action_cut);
menu.add("Two").setIcon(R.drawable.ic_action_copy);
menu.add("Three").setIcon(R.drawable.ic_action_paste);
fab.setMenu(menu);
```

### Attributes

|Attribute|Type|Description|Note|
|---|---|---|---|
|fab_menu|Menu resource ID|Menu||
|fab_fabBackgroundColor|color int or ColorStateList|Main FAB background color||
|fab_fabDrawable|Drawable|Main FAB drawable||
|fab_fabDrawableTint|color int or ColorStateList|Main FAB drawable tint||
|fab_fabRippleColor|color int|Main FAB ripple color||
|fab_miniFabBackgroundColor|color int or ColorStateList|Mini FAB background color||
|fab_miniFabBackgroundColorList|array of color int or ColorStateList|Mini FAB background color|Array size must be same as menu item size|
|fab_miniFabDrawableTint|color int or ColorStateList|Mini FAB drawable tint||
|fab_miniFabDrawableTintList|array of color int or ColorStateList|Mini FAB drawable tint|Array size must be same as menu item size|
|fab_miniFabRippleColor|color int or ColorStateList|Mini FAB ripple color||
|fab_miniFabRippleColorList|array of color int or ColorStateList|Mini FAB ripple color|Array size must be same as menu item size|
|fab_miniFabTextColor|color int or ColorStateList|Mini FAB text color||
|fab_miniFabTextColorList|array of color int or ColorStateList|Mini FAB text color|Array size must be same as menu item size|
|fab_miniFabTextBackground|drawable|Mini FAB text background||
|fab_miniFabTextBackgroundList|drawable|Mini FAB text background|Array size must be same as menu item size|
|fab_useTouchGuard|boolean|Use touch guard or not||
|fab_touchGuardColor|color int|Touch guard color|Default: #80000000|

`fab_miniFab***List` attribute is array version of `fab_miniFab***`. If you want to set different values to each items, please use `fab_miniFab***List` instead of `fab_miniFab***`.

### Event listener

```java
fab.addOnMenuItemClickListener(new FabSpeedDial.OnMenuItemClickListener() {
    @Override
    public void onMenuItemClick(FloatingActionButton fab, TextView textView, int itemId) {
        // do something
    }
});
```

## License

```
Copyright (c) 2017 Keisuke Kobayashi

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
