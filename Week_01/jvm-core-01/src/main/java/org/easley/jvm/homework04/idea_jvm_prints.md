```
➜  ~ jstat -class 5114
Loaded  Bytes  Unloaded  Bytes     Time
  3485  6735.7       16    13.9       1.18
  
➜  ~ jstat -compiler 5114
Compiled Failed Invalid   Time   FailedType FailedMethod
    1502      0       0     1.79          0
    
➜  ~ jstat -gc 5114
 S0C    S1C    S0U    S1U      EC       EU        OC         OU       MC     MU    CCSC   CCSU   YGC     YGCT    FGC    FGCT    CGC    CGCT     GCT
10752.0 10752.0  0.0    0.0   65536.0   3316.7   112640.0    4511.9   21296.0 20730.3 2432.0 2292.2      2    0.008   1      0.017   -          -    0.025
S0C：S0大小
S1C：S1大小
S0U：S0使用大小
S1U：S1使用大小
EC：Eden的大小
EU：Eden使用大小
OC：老年代大小
OU：老年代使用大小
MC：Metaspace大小
MU：Metaspace使用大小
CCSC:CompressedClassSpace大小
CCSU:CompressedClassSpace使用大小
YGC：YGC次数
YGCT：YGC消耗时间
FGC：FGC次数
FGCT：FGC消耗时间
GCT：垃圾回收消耗总时间
 
➜  ~ jstat -gccapacity 5114
 NGCMN    NGCMX     NGC     S0C   S1C       EC      OGCMN      OGCMX       OGC         OC       MCMN     MCMX      MC     CCSMN    CCSMX     CCSC    YGC    FGC   CGC
 87040.0 238592.0  87040.0 10752.0 10752.0  65536.0   175104.0   478208.0   112640.0   112640.0      0.0 1069056.0  21296.0      0.0 1048576.0   2432.0      2     1     -
NGCMN：新生代最小容量
NGCMX：新生代最大容量
NGC：当前新生代容量
S0C：S0大小
S1C：S1大小
EC：Eden大小
OGCMN：老年代最小容量
OGCMX：老年代最大容量
OGC：当前老年代大小
OC:当前老年代大小，OGC = sum(all OC)，因为OldGen只有一个OldSpace，所以他们相等。
MCMN:最小Metaspace容量
MCMX：最大Metaspace容量
MC：当前Metaspace大小
CCSMN：最小CompressedClassSpace大小
CCSMX：最大CompressedClassSpace大小
CCSC：当前CompressedClassSpace大小
YGC：YGC次数
FGC：FGC次数   

// 略过一些
jstat -gcnew 5114
jstat -gcnewcapacity 5114
jstat -gcold 5114
jstat -gcoldcapacity 5114
jstat -gcmetacapacity 5114

// 总结垃圾回收统计
➜  ~ jstat -gcutil 5114
  S0     S1     E      O      M     CCS    YGC     YGCT    FGC    FGCT    CGC    CGCT     GCT
  0.00   0.00   5.06   4.01  97.34  94.25      2    0.008     1    0.017     -        -    0.025
S0：S0当前使用比例
S1：S1当前使用比例
E：Eden使用比例
O：老年代使用比例
M：Metaspace使用比例
CCS：CompressedClassSpace使用比例
YGC：YGC次数
FGC：FGC次数
FGCT：FGC消耗时间
GCT：垃圾回收消耗总时间

➜  ~ jstat -printcompilation 5114
Compiled  Size  Type Method
    1502   7879    1 java/net/URLClassLoader$1 run
Compiled：最近编译方法的数量
Size：最近编译方法的字节码数量
Type：最近编译方法的编译类型
Method：方法名标识
总结：只有一次fgc，eden区使用很少，两个survivor区也没用。
     gc时间也很短，内存占用很健康。

// jstack太多就不贴了，没什么问题
jstack 5114
jstack 5114|grep 45d8 -A 30

// jmap连不上，略过。
```
