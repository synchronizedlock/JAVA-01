#### GC总结
- - -
##### Java对象内存结构 
+ 结构图 
![jvm_object.png](https://upload-images.jianshu.io/upload_images/17638038-978543d154333874.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
+ 对象头和对象引用  
在64位 JVM 中，对象头占据的空间是 12byte(=96bit=64+32)，但是以8字节对齐，所以一个空类的实例至少占用16字节。
在32位 JVM 中，对象头占8个字节，以4的倍数对齐(32=4*8)。所以 new 出来很多简单对象，甚至是 new Object()，都会占用不少内容。
通常在32位 JVM，以及内存小于 -Xmx32G 的64 位JVM 上(默认开启指针压缩)，一个引用占的内存 默认是4个字节。
因此，64位 JVM 一般需要多消耗堆内存。
+ 包装类型  
比原生数据类型消耗的内存要多，详情可以参考 JavaWorld :
Integer:占用16字节(头部8+4=12，数据4字节)，因为 int 部分占4个字节。所以使用 Integer 比原生类型 int 要多消耗 300% 的内存。
Long:一般占用24个字节(头部8+4+数据8=20字节，再对齐)，当然，对象的实际大小由底层平台的内存对齐确定，具体由特定 CPU 平台的 JVM 实现决 定。 看起来一个 Long 类型的对象，比起原生类型 long 多占用了8个字节(也多消耗200%)。
- - -
##### GC对比
+ 不同分代GC搭配选择  
![GC_relation.png](https://upload-images.jianshu.io/upload_images/17638038-3588348e13d8f1ad.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
+ GC性能指标
   - 高分配速率(High Allocation Rate)  
分配速率(Allocation rate)表示单位时间内分配的内存量。通常 使用 MB/sec作为单位。上一次垃圾收集之后，与下一次GC开始之前的年轻代使用量，两者的差值除以时间,就是分配速率。  
简而言之就是：(YoungBefore-上次的YoungAfter) / 时间差
分配速率过高就会严重影响程序的性能，在JVM中可能会导致 巨大的GC开销。  
正常系统:分配速率较低 ~ 回收速率 -> 健康 内存泄漏:分配速率 持续大于 回收速率 -> OOM 性能劣化:分配速率很高 ~ 回收速率 -> 亚健康
   - 过早提升(Premature Promotion)  
提升速率(promotion rate)用于衡量单位时间内从年轻代提升到 老年代的数据量。一般使用 MB/sec 作为单位, 和分配速率类似。  
简而言之就是：(这次年轻代减少量-这次堆的减少量) / 时间差
JVM会将长时间存活的对象从年轻代提升到老年代。根据分代假设，可能存在一种情况，老年代中不仅有存活时间长的对象，也可能有存活时间短的对象。这就是过早提升:对象存活时间 还不够长的时候就被提升到了老年代。  
major GC 不是为频繁回收而设计的，但 major GC 现在也要清理这些生命短暂的对象，就会导致GC暂停时间过长。这会严重影响系统的吞吐量。
+ 新生代GC  
   - Serial：单线程复制算法，eden + from -> to
   - ParNew：Serial的多线程版本
   - Parallel Scavenge： 并行复制，相对ParNew更关注吞吐量。
       + MaxGCPauseMills，GC最大停顿时间
       + GCTimeRatio，最大允许1/(1+n)的垃圾收集时间，默认值是99，即1%
       + +UseAdaptiveSizePolicy，自动调整 Xmn、SurvivorRatio、PretenureSizeThreshold
+ 老年代GC
   - Serial Old：单线程标记清除整理
   - Parallel Old：Serial Old的多线程版本，和Parallel Scavenge一样关注吞吐量
   - CMS：标记清除算法，用空闲列表来管理零散的内存空间，也可以使用 +UseCMSCompactAtFullCollection 对内存进行压缩整理，但是会导致STW更长。分为6个阶段：
     - 初始标记
     - 并发标记
     - 并发预清理
     - 最终标记
     - 并发清除
     - 并发重置
   - CMS退化为Serial Old：CMS运行期间内存无法满足程序需要，会出现 Concurrent Mode Failure，临时启动Serial Old重新处理老年代
+ G1
   - STW时间可预期可配置
   - 将堆内存划分为小块的region，对region标记属于哪个区(Eden、Survivor、Old) ，增量回收，垃圾多的region优先处理
   - Remembered Set  
   - SATB（Snapshot At The Begging）
   - 垃圾回收过程
      + 年轻代  
      Eden满后应用线程暂停，将存活对象拷贝到S区。扫描GCRoots，更新RS，处理RS，复制对象，处理引用
      + 老年代  
      年轻代回收STW，并发标记老年代，重新标记STW，回收百分百为垃圾的块
      + 混合转移暂停  
      清理年轻代，将一部分老年代区域（非百分百垃圾）也回收
      + FGC
      回收失败，退化为SerialGC
