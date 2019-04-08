package com.nongyeos.loan.core.model;
import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MsgQueue {
	Queue<ResMsg> q = new ConcurrentLinkedQueue<>();
	
	public boolean add(ResMsg e) {
		return q.add(e);
	}

	public ResMsg poll() {
		return q.poll();
	}
	
	public ResMsg peek() {
		return q.peek();
	}
	
	public ResMsg[] toArray() {
		return (ResMsg[])q.toArray();
	}
	
	public String toJson() {
		return null;
	}
	/*
	Stack<ResMsg> stack;
	
	public ResMsg push(ResMsg item) {
		return stack.push(item);
	}
	
	public ResMsg pop() {
		return stack.pop();
	}
	
	public ResMsg peek() {
		return stack.peek();
	}
	
	public boolean empty() {
		return stack.empty();
	}
	*/

	public Queue<ResMsg> getQ() {
		return q;
	}

	public void setQ(Queue<ResMsg> q) {
		this.q = q;
	}
}
