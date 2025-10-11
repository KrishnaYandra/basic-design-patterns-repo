package com.java.design.patterns.creational.singleton;

public class Early{  
	 private static Early obj=new Early();//Early, instance will be created at load time  
	 private Early(){}  
	   
	 public static Early getEarly(){  
	  return obj;  
	 }  
	  
	 public void doSomething(){  
	 //write your code  
	 }  
	}
