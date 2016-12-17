/**
* 时间：2016/12/08
* 作者：代文超
* 目的：意在测试java语言中servlet请求处理机制
* 结果：成功
* 总结：1,每一个请求都会产生一个新的线程（个人觉得高并发下有些工具该上得上，
*     线程池的处理机制还有待研究），servlet是以单例形式加载到容器，至于
*     何时加载取决于配置。
*     2,本测试结果类似于数据库高并发模式失控时产生的结果，线程实际上是cpu
*	  的分片处理（时钟周期，比如51单片机的晶振，cpu执行语句的速度是被控制的）。
*     3,java里面有两个有意思的概念，第一，引用传递和值传递；第二，深拷贝和浅拷贝。
*	  个人认为和今天要调查的主题有某种关联。
*     4,如果一个变量是成员变量（或者说是实例变量），那么多个线程对同一个对象的成员变量进行操作时，它们对该成员变量是彼此影响的，也就是说一个线程对成员变量的改变会影响到另一个线程。
*　    如果一个变量是局部变量，那么每个线程都会有一个该局部变量的拷贝（即便是同一个对象中的方法的局部变量，也会对每一个线程有一个拷贝），一个线程对该局部变量的改变不会影响到其他线程。
*/
public class Demo {
	public static void main(String[] args) throws Exception{
		Share a = new Share();
		Thread t = new Thread(a);
		Thread t2 = new Thread(a);//此处证明最终为引用传递
		t.start();
		t2.start();
		Thread.currentThread().sleep((long) 1 * 1000);//注意抛出异常
		System.out.println("go back");
	}
}

class Share implements Runnable {
	public int i = 0;//成员变量
	public void run(){
		doPost();
	}
	public void doPost(){
		//放在这里就是局部变量
		System.out.println("go out");
		while(i<50){
			System.out.println(Thread.currentThread().getName()+"echo-----"+i);
			i++;
		}
	}
}