```
Classfile /Users/easley_ray/my_repo/JAVA-01/Week_01/jvm-core-01/target/classes/org/easley/jvm/homework01/WrappedInt.class
  Last modified Jan 13, 2021; size 1043 bytes
  MD5 checksum 535a725741867c95fec91b2a381b6eed
  Compiled from "WrappedInt.java"
public class org.easley.jvm.homework01.WrappedInt
  minor version: 0
  major version: 52
  flags: (0x0021) ACC_PUBLIC, ACC_SUPER
  this_class: #3                          // org/easley/jvm/homework01/WrappedInt
  super_class: #5                         // java/lang/Object
  interfaces: 0, fields: 1, methods: 7, attributes: 1
  // 常量池表，存放编译期生成的各种字面量与符号引用，这些内容将在类加载后存放到方法区的运行时常量池中
Constant pool:
   #1 = Methodref          #5.#29         // java/lang/Object."<init>":()V
   #2 = Fieldref           #3.#30         // org/easley/jvm/homework01/WrappedInt.val:I
   #3 = Class              #31            // org/easley/jvm/homework01/WrappedInt
   #4 = Methodref          #3.#32         // org/easley/jvm/homework01/WrappedInt."<init>":(I)V
   #5 = Class              #33            // java/lang/Object
   #6 = Utf8               val
   #7 = Utf8               I
   #8 = Utf8               <init>
   #9 = Utf8               (I)V
  #10 = Utf8               Code
  #11 = Utf8               LineNumberTable
  #12 = Utf8               LocalVariableTable
  #13 = Utf8               this
  #14 = Utf8               Lorg/easley/jvm/homework01/WrappedInt;
  #15 = Utf8               add
  #16 = Utf8               (Lorg/easley/jvm/homework01/WrappedInt;)Lorg/easley/jvm/homework01/WrappedInt;
  #17 = Utf8               augend
  #18 = Utf8               subtract
  #19 = Utf8               subtrahend
  #20 = Utf8               multiply
  #21 = Utf8               multiplicand
  #22 = Utf8               divide
  #23 = Utf8               divisor
  #24 = Utf8               remainder
  #25 = Utf8               getVal
  #26 = Utf8               ()I
  #27 = Utf8               SourceFile
  #28 = Utf8               WrappedInt.java
  #29 = NameAndType        #8:#34         // "<init>":()V
  #30 = NameAndType        #6:#7          // val:I
  #31 = Utf8               org/easley/jvm/homework01/WrappedInt
  #32 = NameAndType        #8:#9          // "<init>":(I)V
  #33 = Utf8               java/lang/Object
  #34 = Utf8               ()V
{
  public org.easley.jvm.homework01.WrappedInt(int);
  // descriptor描述了方法的入参类型和返回类型，此处是构造方法，故为int类型的入参和void返回，后续方法不赘述
    descriptor: (I)V
    flags: (0x0001) ACC_PUBLIC
    Code:
    // stack：栈深度，locals：局部变量表元素个数，args_size：参数个数：this+val
      stack=2, locals=2, args_size=2
      // aload0在非静态方法中是把this压栈（非静态方法中局部变量表第一个参数是this）
         0: aload_0
         // 调用Obejct类的初始化方法消耗掉栈顶的this引用
         1: invokespecial #1                  // Method java/lang/Object."<init>":()V
         // 再次加载this到栈顶
         4: aload_0
         // 将局部变量表第二个参数val压栈（构造方法传入的参数）
         5: iload_1
         // 将val的值设置给val字段
         6: putfield      #2                  // Field val:I
         // 搞定开溜，返回this的引用
         9: return
         // 后面加减乘除类的指令就不赘述了
      // 记录方法的源码行号范围   
      LineNumberTable:
        line 14: 0
        line 15: 4
        line 16: 9
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0      10     0  this   Lorg/easley/jvm/homework01/WrappedInt;
            0      10     1   val   I

  public org.easley.jvm.homework01.WrappedInt add(org.easley.jvm.homework01.WrappedInt);
    descriptor: (Lorg/easley/jvm/homework01/WrappedInt;)Lorg/easley/jvm/homework01/WrappedInt;
    flags: (0x0001) ACC_PUBLIC
    Code:
      stack=4, locals=2, args_size=2
         0: new           #3                  // class org/easley/jvm/homework01/WrappedInt
         3: dup
         4: aload_0
         5: getfield      #2                  // Field val:I
         8: aload_1
         9: getfield      #2                  // Field val:I
        12: iadd
        13: invokespecial #4                  // Method "<init>":(I)V
        16: areturn
      LineNumberTable:
        line 19: 0
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0      17     0  this   Lorg/easley/jvm/homework01/WrappedInt;
            0      17     1 augend   Lorg/easley/jvm/homework01/WrappedInt;

  public org.easley.jvm.homework01.WrappedInt subtract(org.easley.jvm.homework01.WrappedInt);
    descriptor: (Lorg/easley/jvm/homework01/WrappedInt;)Lorg/easley/jvm/homework01/WrappedInt;
    flags: (0x0001) ACC_PUBLIC
    Code:
      stack=4, locals=2, args_size=2
         0: new           #3                  // class org/easley/jvm/homework01/WrappedInt
         3: dup
         4: aload_0
         5: getfield      #2                  // Field val:I
         8: aload_1
         9: getfield      #2                  // Field val:I
        12: isub
        13: invokespecial #4                  // Method "<init>":(I)V
        16: areturn
      LineNumberTable:
        line 23: 0
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0      17     0  this   Lorg/easley/jvm/homework01/WrappedInt;
            0      17     1 subtrahend   Lorg/easley/jvm/homework01/WrappedInt;

  public org.easley.jvm.homework01.WrappedInt multiply(org.easley.jvm.homework01.WrappedInt);
    descriptor: (Lorg/easley/jvm/homework01/WrappedInt;)Lorg/easley/jvm/homework01/WrappedInt;
    flags: (0x0001) ACC_PUBLIC
    Code:
      stack=4, locals=2, args_size=2
         0: new           #3                  // class org/easley/jvm/homework01/WrappedInt
         3: dup
         4: aload_0
         5: getfield      #2                  // Field val:I
         8: aload_1
         9: getfield      #2                  // Field val:I
        12: imul
        13: invokespecial #4                  // Method "<init>":(I)V
        16: areturn
      LineNumberTable:
        line 27: 0
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0      17     0  this   Lorg/easley/jvm/homework01/WrappedInt;
            0      17     1 multiplicand   Lorg/easley/jvm/homework01/WrappedInt;

  public org.easley.jvm.homework01.WrappedInt divide(org.easley.jvm.homework01.WrappedInt);
    descriptor: (Lorg/easley/jvm/homework01/WrappedInt;)Lorg/easley/jvm/homework01/WrappedInt;
    flags: (0x0001) ACC_PUBLIC
    Code:
      stack=4, locals=2, args_size=2
         0: new           #3                  // class org/easley/jvm/homework01/WrappedInt
         3: dup
         4: aload_0
         5: getfield      #2                  // Field val:I
         8: aload_1
         9: getfield      #2                  // Field val:I
        12: idiv
        13: invokespecial #4                  // Method "<init>":(I)V
        16: areturn
      LineNumberTable:
        line 31: 0
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0      17     0  this   Lorg/easley/jvm/homework01/WrappedInt;
            0      17     1 divisor   Lorg/easley/jvm/homework01/WrappedInt;

  public org.easley.jvm.homework01.WrappedInt remainder(org.easley.jvm.homework01.WrappedInt);
    descriptor: (Lorg/easley/jvm/homework01/WrappedInt;)Lorg/easley/jvm/homework01/WrappedInt;
    flags: (0x0001) ACC_PUBLIC
    Code:
      stack=4, locals=2, args_size=2
         0: new           #3                  // class org/easley/jvm/homework01/WrappedInt
         3: dup
         4: aload_0
         5: getfield      #2                  // Field val:I
         8: aload_1
         9: getfield      #2                  // Field val:I
        12: irem
        13: invokespecial #4                  // Method "<init>":(I)V
        16: areturn
      LineNumberTable:
        line 35: 0
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0      17     0  this   Lorg/easley/jvm/homework01/WrappedInt;
            0      17     1 divisor   Lorg/easley/jvm/homework01/WrappedInt;

  public int getVal();
    descriptor: ()I
    flags: (0x0001) ACC_PUBLIC
    Code:
      stack=1, locals=1, args_size=1
         0: aload_0
         1: getfield      #2                  // Field val:I
         4: ireturn
      LineNumberTable:
        line 39: 0
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0       5     0  this   Lorg/easley/jvm/homework01/WrappedInt;
}
SourceFile: "WrappedInt.java"
```
