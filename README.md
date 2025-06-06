## Merv BottomNavigationView 
### 🍥 makes classic bottom menus a thing of the past! It brings life to modern applications with its animated interactions, focused icons and fully customizable structure.

[![Platform](https://img.shields.io/badge/Platform-Android-brightgreen.svg?style=flat)](https://www.android.com/)
[![](https://jitpack.io/v/b3ddodev/Merv-BottomNavigationView.svg)](https://jitpack.io/#b3ddodev/Merv-BottomNavigationView)
[![API](https://img.shields.io/badge/API-29%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=29)
[![Instagram Badge](https://img.shields.io/badge/Instagram-%20%40berdan.bdy-brightgreen)](https://www.instagram.com/berdan.bdy)


### Buy Me a Coffee <3
<img src="https://media1.giphy.com/media/v1.Y2lkPTc5MGI3NjExMG9zaGl2Mjd2cHlydWc4enBqNHkzajJ3NnpydHN4YmNwMnltemg3ayZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/7CjEqtZUm2OBmJ9eha/giphy.gif" width="300"/>

[![Buy Me a Coffee](https://img.buymeacoffee.com/button-api/?text=Buy+me+a+coffee&emoji=&slug=withb3ddodo&button_colour=FF5F5F&font_colour=ffffff&font_family=Poppins&outline_colour=000000&coffee_colour=FFDD00)](https://www.buymeacoffee.com/withb3ddodo)



### Jitpack

Add the JitPack repository to your `settings.gradle` file
```groovy
allprojects {
	repositories {
		...
		mavenCentral()
		maven { url 'https://jitpack.io' }
	}
}
```
### Dependency
Add the dependency in your `build.gradle`

```groovy
dependencies {
    implementation 'com.github.b3ddodev:Merv-BottomNavigationView:{release-version}'
}
```

### 🧩 Demo

<img src="https://github.com/b3ddodev/Merv-BottomNavigationView/blob/master/demo.gif" width="300"/>

---

### Using the library
Default XML Sample :
```
<com.merv.bottomnavigationview.library.MervBottomNavigationView
  android:id="@+id/mMervBottomNavigationView"
  android:layout_width="match_parent"
  android:layout_height="wrap_content"
  android:layout_alignParentBottom="true"
  android:layout_alignParentStart="true"
  android:layout_alignParentEnd="true"
  
  app:mBottomNavMorphBackgroundColor="@color/white"
  app:mBottomNavMorphBackgroundShadowLayerSize="6dp"
  
  app:mBottomNavCircleColor="#FF7043"
  app:mBottomNavCircleShadowLayerSize="4dp"
  
  app:mBottomNavSelectedIconColor="#FFFFFF"
  app:mBottomNavUnselectedIconColor="#FF7043"
  
  app:mBottomNavSelectedIconSize="28dp"
  app:mBottomNavUnselectedIconSize="22dp"
  
  app:mBottomNavTextColor="@color/black"
  app:mBottomNavTextSize="12sp"
  app:mBottomNavTextFakeBold="true"
  app:mBottomNavTextChooseFont="@font/outfit_medium"
  app:mBottomNavTextMarginTop="4dp"
  
  app:mBottomNavSelectedIndex="0"
  app:mBottomNavMenuResource="@menu/menu"
  
  app:mBottomNavAnimationInterpolator="BOUNCE"
  app:mBottomNavItemAnimationDuration="300" />
```
Default Programatic Sample :
```
.setBottomNavBackgroundColor(Color.WHITE)
.setBottomNavBackgroundElevation(6f)

.setBottomNavCircleColor(Color.parseColor("#FF7043"))
.setBottomNavCircleShadowElevation(4f)

.setBottomNavSelectedIconColor(Color.WHITE)
.setBottomNavUnselectedIconColor(Color.parseColor("#FF7043"))

.setBottomNavSelectedIconSize(28f)
.setBottomNavUnselectedIconSize(22f)

.setBottomNavTextColor(Color.BLACK)
.setBottomNavTextSize(12f)
.setBottomNavTextFakeBold(true)
.setBottomNavTextFont(ResourcesCompat.getFont(this, R.font.outfit_medium))
.setBottomNavTextMarginTop(4f)

.setBottomNavSelectedIndex(0)
.setMenu(R.menu.menu)

.setBottomNavAnimationInterpolator(MervInterpolator.BOUNCE)
.setBottomNavItemAnimationDuration(300);
```
Manuel Add Items :
```
mMervBottomNavigationView.ClearReloadItems();
mMervBottomNavigationView.setAddItem(ContextCompat.getDrawable(this,R.drawable.ic_preview_home),"Home");
 mMervBottomNavigationView.setAddItem(ContextCompat.getDrawable(this,R.drawable.ic_preview_search),"Search");
 mMervBottomNavigationView.setAddItem(ContextCompat.getDrawable(this,R.drawable.ic_preview_add),"Add");
 mMervBottomNavigationView.setAddItem(ContextCompat.getDrawable(this,R.drawable.ic_preview_reaction),"Reaction");
 mMervBottomNavigationView.setAddItem(ContextCompat.getDrawable(this,R.drawable.ic_preview_favorite),"Favorite");
(MAX 5 ITEMS)
```
OnNavItemSelectedListener :
```
        mMervBottomNavigationView.setOnBottomNavItemSelectedListener(new OnNavItemSelectedListener() {
            @Override
            public void onItemSelected(int mIndex) {
                switch (mIndex){
                    case 0:
                        Toast.makeText(MainActivity.this, "HOME = INDEX : " + mIndex, Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(MainActivity.this, "SEARCH = INDEX : " + mIndex, Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(MainActivity.this, "ADD = INDEX : " + mIndex, Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(MainActivity.this, "REACTION = INDEX : " + mIndex, Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Toast.makeText(MainActivity.this, "FAVORITE = INDEX : " + mIndex, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
```
### Nav Animation Type  
#### Nav animations you can adjust: `MervInterpolator`

Type | Description  
--------- | -----------  
`𝙇𝙄𝙉𝙀𝘼𝙍` | The circle moves at a constant speed with no acceleration or bounce — a straight, robotic transition.  
`𝘼𝘾𝘾𝙀𝙇𝙀𝙍𝘼𝙏𝙀` | The circle starts slowly and gains speed toward the end — a fast landing effect.  
`𝘿𝙀𝘾𝙀𝙇𝙀𝙍𝘼𝙏𝙀` | The circle starts fast and slows down smoothly as it reaches its target — a soft, graceful stop.  
`𝘼𝘾𝘾𝙀𝙇𝙀𝙍𝘼𝙏𝙀_𝘿𝙀𝘾𝙀𝙇𝙀𝙍𝘼𝙏𝙀` | The circle eases in and out — starting slow, speeding up, and slowing down again in a fluid motion.  
`𝙊𝙑𝙀𝙍𝙎𝙃𝙊𝙊𝙏` | The circle overshoots its target slightly and bounces back — creating a dynamic snap effect.  
`𝘽𝙊𝙐𝙉𝘾𝙀` | The circle lands with a bounce, like it's made of rubber — playful and energetic.  
`𝙉𝙊𝙉𝙀` | No animation is applied. The circle instantly jumps to its new position with no transition.  
`𝘼𝙉𝙏𝙄𝘾𝙄𝙋𝘼𝙏𝙀` | The circle slightly moves backward before quickly moving forward — giving a "wind-up" effect.  
`𝘼𝙉𝙏𝙄𝘾𝙄𝙋𝘼𝙏𝙀_𝙊𝙑𝙀𝙍𝙎𝙃𝙊𝙊𝙏` | The circle pulls back, shoots past the target, and settles in — dramatic and expressive.  
`𝙁𝘼𝙎𝙏_𝙊𝙐𝙏_𝙎𝙇𝙊𝙒_𝙄𝙉` | The circle enters quickly and slows down smoothly — a modern, natural-feeling transition.



## License

```
The MIT License (MIT)

Copyright (c) 2025 b3ddo dEV' (github.com/b3ddodev)

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

