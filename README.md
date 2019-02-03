# MaterialFabSpeedDial

[![CircleCI](https://circleci.com/gh/kobakei/MaterialFabSpeedDial.svg?style=svg)](https://circleci.com/gh/kobakei/MaterialFabSpeedDial) [![](https://jitpack.io/v/kobakei/MaterialFabSpeedDial.svg)](https://jitpack.io/#kobakei/MaterialFabSpeedDial)

<img src="https://user-images.githubusercontent.com/900756/28452393-6f0c58ca-6e2d-11e7-8c73-93f70996578c.gif" width="25%" /> <img src="https://user-images.githubusercontent.com/900756/28452390-6d983efa-6e2d-11e7-8547-f143f9282de1.gif" width="25%" />   <img src="https://user-images.githubusercontent.com/900756/27837816-90560b02-6121-11e7-9a60-13cabe60ff91.png" width="45%" />

This project aims to provide [FloatingActionButton's speed dial pattern in material design guideline](https://material.io/guidelines/components/buttons-floating-action-button.html#buttons-floating-action-button-transitions).

This project is inspired by [fab-speed-dial](https://github.com/yavski/fab-speed-dial) and overcomes some issues of it (ex. using private APIs of support library). This library supports landscape mode and also supports `CoordinatorLayout` so it works as same as `FloatingActionButton` when `Snackbar` is shown.

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

You can also use snapshot version.

```groovy
dependencies {
    compile 'com.github.kobakei:MaterialFabSpeedDial:master-SNAPSHOT'
}
```

### What's new in version 2?

Version 2 is major update and has some big changes.

- Migrate to Android X
- Convert Java to Kotlin

If you don't want to use v2, please use `1.2.1` instead.

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

Or you can set up menu items programmatically.

```kotlin
val menu = FabSpeedDialMenu(this)
menu.add("One").setIcon(R.drawable.ic_action_cut)
menu.add("Two").setIcon(R.drawable.ic_action_copy)
menu.add("Three").setIcon(R.drawable.ic_action_paste)
fab.setMenu(menu)
```

### Event listener

You can detect menu open/close event and menu item click event.

```kotlin
fab.addOnStateChangeListener { open ->
    // do something
}

fab.addOnMenuItemClickListener { fab, textView, itemId ->
    // do something
}
```

### Attributes

|Attribute|Type|Description|Note|
|---|---|---|---|
|fab_menu|Menu resource ID|Menu||
|fab_fabBackgroundColor|color int or ColorStateList|Main FAB background color||
|fab_fabDrawable|Drawable|Main FAB drawable||
|fab_fabDrawableTint|color int or ColorStateList|Main FAB drawable tint||
|fab_fabRippleColor|color int|Main FAB ripple color||
|fab_fabRotationAngle|float|Main FAB rotation angle (degree)|Default: 45.0|
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
|fab_useRippleOnPreLollipop|boolean|Use ripple on pre Lollipop|Default: true|
|fab_useRevealEffect|boolean|Use reveal effect of touch guard|Works on API level >= 21. Default: true|
|fab_extraMargin|dimension|Extra margin between FABs|All FABs have padding `app:useCompatPadding="true"`. To reduce space, set negative value.|
|fab_showHorizontallyOnLandscape|boolean|Show menu items horizontally on landscape mode|Default: true|

`fab_miniFab***List` attribute is array version of `fab_miniFab***`. If you want to set different values to each items, please use `fab_miniFab***List` instead of `fab_miniFab***`.　Notice that size of resource array must be same as menu item size.

## Known Issues

- In Android 4.x, there is an issue about **selector of vector drawable XML**. If you want to use such XML, according to this [StackOverflow](https://stackoverflow.com/a/38012842), you need to add the following snippet in your Activity classes.

```Java
static {
    AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
}
```

## License

```
Copyright (c) 2017-present Keisuke Kobayashi

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
