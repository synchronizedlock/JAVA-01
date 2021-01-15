```
Classfile /Users/easley_ray/my_repo/JAVA-01/Week_01/jvm-core-01/target/classes/org/easley/jvm/homework01/WrappedIntTest.class
  Last modified Jan 13, 2021; size 1192 bytes
  MD5 checksum 14371c531701248693b5dc9dbad02440
  Compiled from "WrappedIntTest.java"
public class org.easley.jvm.homework01.WrappedIntTest
  minor version: 0
  major version: 52
  flags: (0x0021) ACC_PUBLIC, ACC_SUPER
  this_class: #11                         // org/easley/jvm/homework01/WrappedIntTest
  super_class: #12                        // java/lang/Object
  interfaces: 0, fields: 1, methods: 3, attributes: 1
  // 常量池表，存放编译期生成的各种字面量与符号引用，这些内容将在类加载后存放到方法区的运行时常量池中
Constant pool:
   #1 = Methodref          #12.#40        // java/lang/Object."<init>":()V
   #2 = Class              #41            // org/easley/jvm/homework01/WrappedInt
   #3 = Methodref          #2.#42         // org/easley/jvm/homework01/WrappedInt."<init>":(I)V
   #4 = Fieldref           #11.#43        // org/easley/jvm/homework01/WrappedIntTest.NUMS:[Lorg/easley/jvm/homework01/WrappedInt;
   #5 = Methodref          #2.#44         // org/easley/jvm/homework01/WrappedInt.add:(Lorg/easley/jvm/homework01/WrappedInt;)Lorg/easley/jvm/homework01/WrappedInt;
   #6 = Methodref          #2.#45         // org/easley/jvm/homework01/WrappedInt.getVal:()I
   #7 = Methodref          #2.#46         // org/easley/jvm/homework01/WrappedInt.subtract:(Lorg/easley/jvm/homework01/WrappedInt;)Lorg/easley/jvm/homework01/WrappedInt;
   #8 = Methodref          #2.#47         // org/easley/jvm/homework01/WrappedInt.multiply:(Lorg/easley/jvm/homework01/WrappedInt;)Lorg/easley/jvm/homework01/WrappedInt;
   #9 = Methodref          #2.#48         // org/easley/jvm/homework01/WrappedInt.divide:(Lorg/easley/jvm/homework01/WrappedInt;)Lorg/easley/jvm/homework01/WrappedInt;
  #10 = Methodref          #2.#49         // org/easley/jvm/homework01/WrappedInt.remainder:(Lorg/easley/jvm/homework01/WrappedInt;)Lorg/easley/jvm/homework01/WrappedInt;
  #11 = Class              #50            // org/easley/jvm/homework01/WrappedIntTest
  #12 = Class              #51            // java/lang/Object
  #13 = Utf8               NUMS
  #14 = Utf8               [Lorg/easley/jvm/homework01/WrappedInt;
  #15 = Utf8               <init>
  #16 = Utf8               ()V
  #17 = Utf8               Code
  #18 = Utf8               LineNumberTable
  #19 = Utf8               LocalVariableTable
  #20 = Utf8               this
  #21 = Utf8               Lorg/easley/jvm/homework01/WrappedIntTest;
  #22 = Utf8               main
  #23 = Utf8               ([Ljava/lang/String;)V
  #24 = Utf8               num
  #25 = Utf8               Lorg/easley/jvm/homework01/WrappedInt;
  #26 = Utf8               args
  #27 = Utf8               [Ljava/lang/String;
  #28 = Utf8               sum
  #29 = Utf8               operand
  #30 = Utf8               product
  #31 = Utf8               quotient
  #32 = Utf8               remainder
  #33 = Utf8               StackMapTable
  #34 = Class              #27            // "[Ljava/lang/String;"
  #35 = Class              #41            // org/easley/jvm/homework01/WrappedInt
  #36 = Class              #14            // "[Lorg/easley/jvm/homework01/WrappedInt;"
  #37 = Utf8               <clinit>
  #38 = Utf8               SourceFile
  #39 = Utf8               WrappedIntTest.java
  #40 = NameAndType        #15:#16        // "<init>":()V
  #41 = Utf8               org/easley/jvm/homework01/WrappedInt
  #42 = NameAndType        #15:#52        // "<init>":(I)V
  #43 = NameAndType        #13:#14        // NUMS:[Lorg/easley/jvm/homework01/WrappedInt;
  #44 = NameAndType        #53:#54        // add:(Lorg/easley/jvm/homework01/WrappedInt;)Lorg/easley/jvm/homework01/WrappedInt;
  #45 = NameAndType        #55:#56        // getVal:()I
  #46 = NameAndType        #57:#54        // subtract:(Lorg/easley/jvm/homework01/WrappedInt;)Lorg/easley/jvm/homework01/WrappedInt;
  #47 = NameAndType        #58:#54        // multiply:(Lorg/easley/jvm/homework01/WrappedInt;)Lorg/easley/jvm/homework01/WrappedInt;
  #48 = NameAndType        #59:#54        // divide:(Lorg/easley/jvm/homework01/WrappedInt;)Lorg/easley/jvm/homework01/WrappedInt;
  #49 = NameAndType        #32:#54        // remainder:(Lorg/easley/jvm/homework01/WrappedInt;)Lorg/easley/jvm/homework01/WrappedInt;
  #50 = Utf8               org/easley/jvm/homework01/WrappedIntTest
  #51 = Utf8               java/lang/Object
  #52 = Utf8               (I)V
  #53 = Utf8               add
  #54 = Utf8               (Lorg/easley/jvm/homework01/WrappedInt;)Lorg/easley/jvm/homework01/WrappedInt;
  #55 = Utf8               getVal
  #56 = Utf8               ()I
  #57 = Utf8               subtract
  #58 = Utf8               multiply
  #59 = Utf8               divide
{
  public org.easley.jvm.homework01.WrappedIntTest();
  // 默认生成的无参构造方法
    descriptor: ()V
    flags: (0x0001) ACC_PUBLIC
    Code:
    // 栈深度1，本地变量表只有一个this，参数数量1（this）
      stack=1, locals=1, args_size=1
         0: aload_0
         1: invokespecial #1                  // Method java/lang/Object."<init>":()V
         4: return
      LineNumberTable:
        line 10: 0
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0       5     0  this   Lorg/easley/jvm/homework01/WrappedIntTest;

  public static void main(java.lang.String[]);
    descriptor: ([Ljava/lang/String;)V
    flags: (0x0009) ACC_PUBLIC, ACC_STATIC
    Code:
      stack=3, locals=6, args_size=1
      // WrappedInt sum = new WrappedInt(0);
         0: new           #2                  // class org/easley/jvm/homework01/WrappedInt
         3: dup
         4: iconst_0
         5: invokespecial #3                  // Method org/easley/jvm/homework01/WrappedInt."<init>":(I)V
         // 保存到sum
         8: astore_1
         // 取静态字段NUMS
         9: getstatic     #4                  // Field NUMS:[Lorg/easley/jvm/homework01/WrappedInt;
        // 取下表对应的元素引用给num
        12: astore_2
        // num入栈
        13: aload_2
        // NUMS长度
        14: arraylength
        // 保存长度到3号坑
        15: istore_3
        // 0压栈
        16: iconst_0
        // 0扔到第4个坑，其实就是编译器生成的fori里的i,i=0循环开始
        17: istore        4
        // i入栈
        19: iload         4
        // length入栈
        21: iload_3
        // length >= i?，判断for有没跑完
        22: if_icmpge     44
        25: aload_2
        26: iload         4
        28: aaload
        29: astore        5
        31: aload_1
        32: aload         5
        // 两数相加
        34: invokevirtual #5                  // Method org/easley/jvm/homework01/WrappedInt.add:(Lorg/easley/jvm/homework01/WrappedInt;)Lorg/easley/jvm/homework01/WrappedInt;
        // 更新sum
        37: astore_1
        38: iinc          4, 1
        // for循环结束
        41: goto          19
        // 后面都是些简单的逻辑，只解释了静态代码块，其他的就不赘述了
        44: new           #2                  // class org/easley/jvm/homework01/WrappedInt
        47: dup
        48: iconst_4
        49: invokespecial #3                  // Method org/easley/jvm/homework01/WrappedInt."<init>":(I)V
        52: astore_2
        53: aload_1
        54: invokevirtual #6                  // Method org/easley/jvm/homework01/WrappedInt.getVal:()I
        57: aload_2
        58: invokevirtual #6                  // Method org/easley/jvm/homework01/WrappedInt.getVal:()I
        61: if_icmple     70
        64: aload_1
        65: aload_2
        66: invokevirtual #7                  // Method org/easley/jvm/homework01/WrappedInt.subtract:(Lorg/easley/jvm/homework01/WrappedInt;)Lorg/easley/jvm/homework01/WrappedInt;
        69: astore_3
        70: aload_1
        71: aload_2
        72: invokevirtual #8                  // Method org/easley/jvm/homework01/WrappedInt.multiply:(Lorg/easley/jvm/homework01/WrappedInt;)Lorg/easley/jvm/homework01/WrappedInt;
        75: astore_3
        76: aload_1
        77: aload_2
        78: invokevirtual #9                  // Method org/easley/jvm/homework01/WrappedInt.divide:(Lorg/easley/jvm/homework01/WrappedInt;)Lorg/easley/jvm/homework01/WrappedInt;
        81: astore        4
        83: aload_1
        84: aload_2
        85: invokevirtual #10                 // Method org/easley/jvm/homework01/WrappedInt.remainder:(Lorg/easley/jvm/homework01/WrappedInt;)Lorg/easley/jvm/homework01/WrappedInt;
        88: astore        5
        90: return
      LineNumberTable:
        line 14: 0
        line 15: 9
        line 16: 31
        line 15: 38
        line 19: 44
        line 20: 53
        line 21: 64
        line 23: 70
        line 24: 76
        line 25: 83
        line 26: 90
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
           31       7     5   num   Lorg/easley/jvm/homework01/WrappedInt;
            0      91     0  args   [Ljava/lang/String;
            9      82     1   sum   Lorg/easley/jvm/homework01/WrappedInt;
           53      38     2 operand   Lorg/easley/jvm/homework01/WrappedInt;
           76      15     3 product   Lorg/easley/jvm/homework01/WrappedInt;
           83       8     4 quotient   Lorg/easley/jvm/homework01/WrappedInt;
           90       1     5 remainder   Lorg/easley/jvm/homework01/WrappedInt;
      StackMapTable: number_of_entries = 3
        frame_type = 255 /* full_frame */
          offset_delta = 19
          locals = [ class "[Ljava/lang/String;", class org/easley/jvm/homework01/WrappedInt, class "[Lorg/easley/jvm/homework01/WrappedInt;", int, int ]
          stack = []
        frame_type = 248 /* chop */
          offset_delta = 24
        frame_type = 252 /* append */
          offset_delta = 25
          locals = [ class org/easley/jvm/homework01/WrappedInt ]

  // 静态块
  static {};
    descriptor: ()V
    flags: (0x0008) ACC_STATIC
    Code:
      stack=6, locals=0, args_size=0
         // 数组初始大小3入栈
         0: iconst_3
         // 整个新数组
         1: anewarray     #2                  // class org/easley/jvm/homework01/WrappedInt
         // 复制一份栈顶引用
         4: dup
         // 下标0入栈
         5: iconst_0
         6: new           #2                  // class org/easley/jvm/homework01/WrappedInt
         9: dup
         // 1作为val入栈用于生成第一个元素
        10: iconst_1
        11: invokespecial #3                  // Method org/easley/jvm/homework01/WrappedInt."<init>":(I)V
        14: aastore
        15: dup
        // 下标1入栈，重复上面的操作...
        16: iconst_1
        17: new           #2                  // class org/easley/jvm/homework01/WrappedInt
        20: dup
        21: iconst_2
        22: invokespecial #3                  // Method org/easley/jvm/homework01/WrappedInt."<init>":(I)V
        25: aastore
        26: dup
        // 下标2入栈，重复上面的操作...
        27: iconst_2
        28: new           #2                  // class org/easley/jvm/homework01/WrappedInt
        31: dup
        32: iconst_3
        33: invokespecial #3                  // Method org/easley/jvm/homework01/WrappedInt."<init>":(I)V
        36: aastore
        // 数组初始化完成，设置到静态字段NUMS
        37: putstatic     #4                  // Field NUMS:[Lorg/easley/jvm/homework01/WrappedInt;
        // 开溜
        40: return
      LineNumberTable:
        line 11: 0
}
SourceFile: "WrappedIntTest.java"
```
