# MaterialFabSpeedDial

[![](https://jitpack.io/v/kobakei/MaterialFabSpeedDial.svg)](https://jitpack.io/#kobakei/MaterialFabSpeedDial)

**NOTICE: This project is still experimental. APIs may be changed without compatibility.**

This project aims to provide [FloatingActionButton's speed dial pattern in material design guideline](https://material.io/guidelines/components/buttons-floating-action-button.html#buttons-floating-action-button-transitions).

This project is inspired by [fab-speed-dial](https://github.com/yavski/fab-speed-dial) and overcomes some issues of it (ex. using private APIs of support library).

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

## Getting Started

### Layout

```xml
<io.github.kobakei.materialfabspeeddial.FabSpeedDial
    android:id="@+id/fab"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:fab_fabBackgroundColor="@color/red"
    app:fab_fabDrawable="@drawable/ic_action_fab"
    app:fab_fabDrawableTint="@color/white"
    app:fab_rippleColor="@color/white"
    app:fab_touchGuard="true"
    app:fab_touchGuardColor="@color/transparent_black"/>
```

### Set up

```java
FabSpeedDial fab = (FabSpeedDial) findViewById(R.id.fab);
fab.addMenu(new FabSpeedDialMenu.Builder(this)
        .setItemId(1)
        .setTitle("Alarm")
        .setTitleColor(R.color.fab_mini_text)
        .setTitleBackgroundColor(R.color.fab_mini)
        .setDrawable(R.drawable.ic_action_alarm)
        .setDrawableTint(R.color.fab_mini_text)
        .setFabBackgroundColor(R.color.fab_mini)
        .setRippleColor(R.color.colorAccent)
        .build());
```

### Event listener

```java
fab.addOnMenuClickListener(new FabSpeedDial.OnMenuClickListener() {
        @Override
        public void onMenuClick(View view, int itemId) {
            // do something
        }
    });
```

## TODO

- Set up menus from XML
- Support CoordinatorLayout

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
