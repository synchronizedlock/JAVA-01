package org.easley.demo;

/**
 * 全民制作人们大家好，我是练习时长两年半的个人练习生蔡徐坤
 * BGM:得♂得♂得♂(弹簧音)
 *
 * @author Easley
 * @date 2021/2/25
 * @since 1.0
 */
public class Kunkun {

    public static void main(String[] args) throws InterruptedException {
        Kunkun kunkun = new Kunkun();
        kunkun.sing();
        kunkun.jump();
        kunkun.rap();
        kunkun.basketball();
    }

    private void sing() throws InterruptedException {
        System.out.println("唱");
        Thread.sleep(10);
    }

    private void jump() throws InterruptedException {
        System.out.println("跳");
        Thread.sleep(20);
    }

    private void rap() throws InterruptedException {
        System.out.println("rap");
        Thread.sleep(30);
    }

    private void basketball() throws InterruptedException {
        System.out.println("篮球");
        Thread.sleep(40);
    }
}
