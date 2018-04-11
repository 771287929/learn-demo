package com.zhouwei.test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class LambdaTest {

	public static void main(String[] args) {
//		List<String> list=Arrays.asList("java","phtyon",".net","java-SE");
		/*for (String str : list) {
			System.out.println(str);
		}
		System.out.println("-------------");
		list.forEach(e->System.out.println(e));
		System.out.println("==============");
		list.forEach(System.out::println);//方法引用由::双冒号操作符标示
*/		
		/*filter(list, new Predicate<String>() {
			@Override
			public boolean test(String t) {
				// TODO Auto-generated method stub
				return t.startsWith("j");
			}
		});*/
		
//		filter(list, e->e.equals("java"));
//		filter(list, e->((String) e).startsWith("j"));
		
//		list.stream().filter(e->e.startsWith("java")).forEach(n->System.out.println(n));
		
//		List<Integer> ll=Arrays.asList(123,232,234,543);
		
//		for (Integer cost : ll) {
//			double price=cost+.12*cost;
//			System.out.println(price);
//		}
//		ll.stream().map(cost->cost+.12*cost).forEach(System.out::println);
//		List<Double> dlist=ll.stream().map(cost->cost+.12*cost).distinct().collect(Collectors.toList());
//		dlist.forEach(System.out::println);
		/*double dd=ll.stream().map(cost->cost+.12*cost).reduce((a,b)->a+b).get();
		System.out.println(dd);
		double d=ll.stream().map(cost->cost+.12*cost).reduce(0d,Double::sum);
		System.out.println(d);*/
		
		Map<String, Integer> items = new HashMap<>();
		items.put("A", 10);
		items.put("B", 20);
		items.put("C", 30);
		items.put("D", 40);
		items.put("E", 50);
		items.put("F", 60);
		
//		for(Map.Entry<String, Integer> entry:items.entrySet()){
//			System.out.println("key:"+entry.getKey()+" value:"+entry.getValue());
//		}
		
		items.forEach((k,v)->System.out.println("key:"+k+" value:"+v));
	}
	
	public static void filter(List<String> names, Predicate condition) {
	    for(String name: names)  {
	        if(condition.test(name)) {
	            System.out.println(name + " ");
	        }
	    }
	}
}
