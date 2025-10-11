package com.java.design.patterns.creational.singleton;

public class Lazy{  
	 private static Lazy obj;  
	 private Lazy(){}  
	   
	 public static Lazy getLazy(){  
	        if (obj == null){  
	            obj = new Lazy();//instance will be created at request time  
	        }  
	  return obj;  
	 }  
	  
	 public void doSomething(){  
	 //write your code  
	 }  
	}
