package com.woniu;

public class IcalcImpl implements Icalc {

	public int add(int a, int b) {
		int r = a+b;
		System.out.println("IcalcImpl.add()");
		
		return r;
	}

	public int sub(int a, int b) {
		int r = a-b;
		System.out.println("IcalcImpl.sub()");
		return r;
	}

	public int mul(int a, int b) {
		int r = a*b;
		System.out.println("IcalcImpl.mul()");
		return r;
	}

	public int div(int a, int b) {
		int r = a/b;
		System.out.println("IcalcImpl.div()");
		return r;
	}

	
}
