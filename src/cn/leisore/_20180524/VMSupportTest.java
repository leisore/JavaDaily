package cn.leisore._20180524;

import java.util.Date;

public class VMSupportTest {

	
	static public long Fib1(long  num)  
    {  
        if ((num == 1) || (num == 0))  
        {  
            return num;  
        }  
        return Fib1(num-1)+Fib1(num-2);  
    }  
	
	public static void main(String[] args) {
		System.out.println(new Date(1535527306125L) );
		System.out.println(Integer.MAX_VALUE);
	}
}
