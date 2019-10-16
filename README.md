# Tinker热修复技术
 * @author AI
 * note：
 * Tinker热修复优势：
 *    1、无需重新发布新版本，省时省力
 *    2、用户无感知修复，无需下载最新应用，代价小
 *    3、修复成功率高，把损失降到最低
 * 热修复能完成那些修复：
 *    1、代码修复
 *    2、资源修复
 *    3、so库修复
 * 热修复插桩原理：
 *    1、越靠前的Dex优先被系统使用，基于类级别的修复
 *    |---------------------------------------------------|
 *    |patch.dex classes.dex classes1.dex classes2.dex ...|Elements
 *    |---------------------------------------------------|
 *    2、原理是Hook了ClassLoader.pathList.dexElements[]
 *    3、因为ClassLoader的findClass是通过遍历dexElements[]中的dex来寻找类，找到就不会再找了
 *    4、.java javac  class  dex.bat  .dex
 * 任意地址下载的apk：
 *    1、app只要安装成功，同时复制一份apk文件到私有目录：data/app/packageName-1/base.apk
 *    2、类加载器加载的是私有目录下的
 * 思路：将修复好的dex文件下载到本机，替换或者插队有bug的dex文件
 *    1、创建一个BaseDexClassLoader子类，DexClassLoader加载器(临时)
 *    2、用自己创建的类加载器，加载修复好的class2.dex(服务器的接口下载)
 *    3、将自己和系统的dexElements数组进行合并，并且设置优先级(将修复包的dex文件排序到数组的最前面)
 *    4、通过反射技术，赋值给系统的pathList
 * 注意事项：
 *    1、主包dex尽量不要有bug
 *    2、必须是运行时修复
 *
 * 测试过程：
 *     1、先安装有bug的包
 *     2、然后修复bug，重新打包，把新得到的apk解压，得到classes2.dex,放到/sdcard目录下，进行修复
 *

