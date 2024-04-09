
public class TC2 {

	public static void main(String [] Args)
	{
////		Find Maximum Element in an Array:
//			Write a program to find the maximum element in an array.
//			[3,67,34,90,56,78,34,23,14] [3,67,34,90,56,78,34,23,101,14]
		
		int  number[] = {3,67,34,90,56,78,34,23,101,14};
		
		int max = number[0];
		
		int min = number[0];
		
		for(int i = 0; i<number.length; i++) {
			
			if (max < number[i]) {
				
				max = number[i];
				
				
			}
		}
		for(int i = 0; i<number.length; i++) {
			
			if (min > number[i]) {
				
				min = number[i];
				
				
			}
		}
		System.out.println(max);
		System.out.println(min);
	}
}
