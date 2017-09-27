# NationalGeographic  国家地理
NationalGeographic  国家地理是一个第三方的国家地理-每日精选客户端     
一个比官方还像官方,有质感、功能完善的国家地理-每日精选客户端 (逃)   
使用了最新技术Android Architecture Components（lifecycle 组件） & Kotlin (填坑之旅)

<img src="/screenshots/1.gif" alt="screenshot" title="screenshot" width="270" height="486" />   <img src="/screenshots/2.gif" alt="screenshot" title="screenshot" width="270" height="486" />  <img src="/screenshots/3.gif" alt="screenshot" title="screenshot" width="270" height="486" />  
  
## 技术细节
国家地理的UI基本都是基于原生实现     
使用了Android Architecture Components + Databinding作为整体架构        
使用了Kotlin

使用了以下开源库

* Retrofit
* OkHttp
* Glide
* PhotoView


国家地理的技术

* 手动实现了上拉加载更多，并加入了加载错误的容错，点击重新加载回调
* CardView + 多种type的RecyclerView展示列表
* SQLite实现本地收藏
* 使用ViewPager来展示图片
* OkHttp + Retrofit实现的网络模块
* Glide加载图片，下载图片，处理图片缓存
* webview显示页面
* 简单的分享功能

想法来自

* [National-Geography](https://github.com/bogerchan/National-Geography) 感谢！
  
## 国家地理的功能

### 已实现的功能
* 每日精选列表
* 每日精选查看
* 收藏
* 分享
* 下载

### 后续国家地理将持续迭代优化

## 源码分析&教程
最新的东西还是遇到了很多坑，后续给大家分析、填坑，点关注不迷路，给国家地理一颗star吧 

后续发布到我的[小站](http:wheat7.com)和[简书](http://www.jianshu.com/u/6005415e3069)

## 更新日志
* v1.0(2017-9-27): 初版

## 讨论  
群号：198233012

  <img src="/screenshots/qq.jpeg" alt="screenshot" title="screenshot" width="270" height="370" />  
  

## License

GPL 3.0

## 免责申明

应用中的所有数据以及资源来源于网络，所有内容和商标的版权归原创者或所有方所有，应用仅作学习交流之用，严禁用于商业用途，违反申明所引发的一切问题由使用者承担

## 看看其他的
*  [VRPlayer-一个极简但是强大的本地VR视频播放器](https://github.com/wheat7/VRPlayer) 带源码分析
*  [腰果-Gank with Databinding](https://github.com/wheat7/Cashew)

