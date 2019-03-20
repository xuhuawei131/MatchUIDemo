# MatchUIDemo
适配ui的demo
matchType 有三种模式:  
宽高比例模式(ratioType),横向纵向缩放模式(scaleType),原始模式(originType)。 
  
宽高比例模式(ratioType)：  
这个模式适用于某个方向大小固定，但是另一个方向不限制高度的，但是要按照某个比例进行适配的情况，比如ScrollView中，宽度为屏幕宽度，高度可以无限延申  

需要使用 app:baseWidth="true"和 app:ratioValue="1.5"进行设置  

app:baseWidth：  
如果baseWidth为true，表示是以宽度为基准，所以就设置app:widthDesign，  因为我的设计图中手机尺寸是375*667，
所以app:widthDesign="375"表示全屏的意思  
  
如果baseWidth为false,表示以高度为基准，所以必须设置app:heightDesign， 

app:ratioValue  
app:ratioValue表示的是宽高比例值，是以宽度除以高度的浮点数  

横向纵向缩放模式(scaleType)：  
这个是严格要按照效果图的大小进行适配的情况，这个情况不考虑控件的比例，  所以app:baseWidth="true"和 app:ratioValue="1.5"在这种模式就是无效的
因为横向缩放的比例值与纵向缩放的比例值不一定是一样的，我们是按照效果图的屏幕宽高与手机的宽高进行比值，虽然不保证缩放统一比值，
但是可以保证横向上比值是一致的，纵向上的比值是一致的。

这个情况需要同时输入app:widthDesign和app:heightDesign  

原始模式(originType)：  
原始模式就是原来的dp模式，这个不用多说了
  
  
需要注意的是DesignScreenWidth和DesignScreenHeight 是设计图上屏幕的尺寸，如果公司是同一套设计的尺寸 那么在ScaleViewHelper中设置好默认的尺寸就好了，
在xml中就不用写了，除非一个项目中 出现多种设计图的尺寸，那么就必须要写了。
