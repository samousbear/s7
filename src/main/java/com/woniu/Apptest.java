package com.woniu;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;

import javax.xml.ws.handler.Handler;
import org.junit.Test;


interface Interceptor{
	void init();
	void destory() ;
	Object intercept(ActionIvokcation ai) throws Exception;
}


class ActionIvokcation{
	private Method method;
	private Object target;
	private Object[] args;
	public ActionIvokcation(Method method, Object target, Object[] args) {
		super();
		this.method = method;
		this.target = target;
		this.args = args;
	}
	
	public Object invoke() throws Exception {
		return method.invoke(target, args);
		
	}
}


class Myhandler implements InvocationHandler{
	private Object target;
	private Interceptor interceptor;
	public Myhandler(Object target,Interceptor interceptor) {
		super();
		this.target = target;
		this.interceptor= interceptor;
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object r = interceptor.intercept(new ActionIvokcation(method,target,args));
		return r;
	}
}

//´úÀí
class MyProxy {
	public Object getProxy(Object target,Interceptor interceptor ) {
		
		ClassLoader cl = MyProxy.class.getClassLoader();
		Class[] interfaces = target.getClass().getInterfaces();
		Myhandler mh = new Myhandler(target,interceptor);
		Object proxy = Proxy.newProxyInstance(cl,interfaces,mh);
	return proxy;
	}
	public Object getProxy(Object proxy) throws Exception {
		List<Interceptor> interceptor = new ArrayList<Interceptor>();
		Reader in = new FileReader("src/main/resources/foo.txt");
		BufferedReader br = new BufferedReader(in);
		String line = null;
		while((line=br.readLine())!=null) {
			interceptor.add((Interceptor)(Class.forName(line).newInstance()));
		}
		for (Interceptor interceptor2 : interceptor) {
			proxy = getProxy(proxy,interceptor2);
		}
		for(int i=interceptor.size()-1;i>=0;i--) {
			proxy = getProxy(proxy,interceptor.get(i));
		}
		return proxy;
		
	}
}
//==================================================
class A implements Interceptor{

	public void init() {
		
	}

	public void destory() {
		
	}

	public Object intercept(ActionIvokcation ai) throws Exception {
		System.out.println("1111111111111");
		Object r = ai.invoke();
		System.out.println("2222222222222");
		return r;
	}

	
	
}
class B implements Interceptor{

	public void init() {
		
	}

	public void destory() {
		
	}

	public Object intercept(ActionIvokcation ai) throws Exception {
		System.out.println("bbbbbbbbbbbbbbbbbbb");
		Object r = ai.invoke();
		System.out.println("bbbbbbbb");
		return r;
	}

	
	
}
class C implements Interceptor{

	public void init() {
		
	}

	public void destory() {
		
	}

	public Object intercept(ActionIvokcation ai) throws Exception {
		System.out.println("ccccccccccccccccc");
		Object r = ai.invoke();
		System.out.println("ccccccccccccccccccc");
		return r;
	}

	
	
}
public class Apptest {
	@Test
	public void testApp() throws Exception {
		Icalc target = new IcalcImpl();
		MyProxy mm = new MyProxy();
		Icalc proxy = (Icalc) mm.getProxy(target);
		proxy.add(3, 5);
		
	}
}
