package com.minbo.javademo;

public class App5 {
	
	public static void main(String[] args) {
		
	}

	class parent {
		int i = 0;
		
		public parent() {
			this.print(2);
		}

		public void print(int i) {
			this.i = i;
		}
	}

	class sub extends parent {
		public sub() {
			
		}
	}
}
