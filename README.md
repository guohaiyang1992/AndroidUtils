# AndroidUtils
android 工具类集合
---
  1.KeyBoardUtils  --关闭键盘的工具类（支持activity、dialog、fragement,无需传入对应的控件）</br>
  
  2.UnitUtils --单位转换的工具类（支持 sp、dp、px的互相转换，注意进行初始化 init）</br>
  
  3.ThreadUtils --简单的线程工具类（切换线程、判断线程状态等）</br>
  
  4.TaskManager --线程切换工具类（支持链式调用，不破坏原有逻辑），与其相关类Task（最小调度类）、ThreadUtils（线程切换类）</br>
  
  5.ViewHolder --视图缓存类（用于Listview、GridView、RecycleView）</br>
  
  6.StringUtils --字符串处理类（判断是否为null为空，字符串安全转int、float、double）</br>
  
  7.FileUtils --文件处理类（判断文件是否存在，删除旧文件创建文件夹）</br>

  8.SingletonFactory --单例工厂类（通过反射生成单例对象，无需自己去写对应方法）</br>

  9.IntentUtils --意图判断类（在调用系统有关软件时，需要贤判断一下，该类型软件是否存在，避免找不到activity异常。）</br>
