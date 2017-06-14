# AndroidUtils （工具类集合）
### 简介： 人生苦短，我用AndroidUtils..

---
  

 1. [KeyBoardUtils](https://github.com/guohaiyang1992/AndroidUtils/blob/master/utils/src/main/java/com/android/utils/common/KeyBoardUtils.java)  --关闭键盘的工具类（支持activity、dialog、fragement,无需传入对应的控件）

 2. [UnitUtils](https://github.com/guohaiyang1992/AndroidUtils/blob/master/utils/src/main/java/com/android/utils/common/UnitUtils.java) --单位转换的工具类（支持 sp、dp、px的互相转换，注意进行初始化 init）
 3. [ThreadUtils](https://github.com/guohaiyang1992/AndroidUtils/blob/master/utils/src/main/java/com/android/utils/common/ThreadUtils.java) --简单的线程工具类（切换线程、判断线程状态等）
  
 4. [TaskManager](https://github.com/guohaiyang1992/AndroidUtils/blob/master/utils/src/main/java/com/android/utils/common/TaskManager.java) --线程切换工具类（支持链式调用，不破坏原有逻辑），与其相关类Task（最小调度类）、ThreadUtils（线程切换类）</br>
  
 5. [ViewHolder](https://github.com/guohaiyang1992/AndroidUtils/blob/master/utils/src/main/java/com/android/utils/common/ViewHolder.java) --视图缓存类（用于Listview、GridView、RecycleView）</br>
  
 6. [StringUtils](https://github.com/guohaiyang1992/AndroidUtils/blob/master/utils/src/main/java/com/android/utils/common/StringUtils.java) --字符串处理类（判断是否为null为空，字符串安全转int、float、double）

 7.  [FileUtils](https://github.com/guohaiyang1992/AndroidUtils/blob/master/utils/src/main/java/com/android/utils/common/FileUtils.java) --文件处理类（判断文件是否存在，删除旧文件创建文件夹）

 8.  [SingletonFactory](https://github.com/guohaiyang1992/AndroidUtils/blob/master/utils/src/main/java/com/android/utils/common/SingletonFactory.java) --单例工厂类（通过反射生成单例对象，无需自己去写对应方法）

 9.  [IntentUtils](https://github.com/guohaiyang1992/AndroidUtils/blob/master/utils/src/main/java/com/android/utils/common/IntentUtils.java) --意图判断类（在调用系统有关软件时，需要贤判断一下，该类型软件是否存在，避免找不到activity异常。）

 10.  [ObjectUtils](https://github.com/guohaiyang1992/AndroidUtils/blob/master/utils/src/main/java/com/android/utils/common/ObjectUtils.java) --对象工具类（用于判断是不是null,支持多个或者单个）

 11.  [KeyBoardHelper](https://github.com/guohaiyang1992/AndroidUtils/blob/master/utils/src/main/java/com/android/utils/common/KeyBoardHelper.java) --键盘监听辅助类（用于弥补系统无法监听键盘状态的辅助类，支持对dialog和activity的监听）</br>
