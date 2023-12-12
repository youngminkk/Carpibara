package utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Predicate;

import exception.RangeException;
import lombok.Value;
import service.ReviewService;
import service.UserService;

public class Utils {
	public static final Scanner SCANNER = new Scanner(System.in);

	public static String nextLine(String msg) {
		System.out.print(msg);
		return SCANNER.nextLine();
	}

	public static int nextInt(String msg) {
//		System.out.print(msg);
//		return Integer.parseInt(scanner.nextLine());
		return Integer.parseInt(nextLine(msg));
	}

	public static String nextLine(String msg, Predicate<String> pred, String errorMsg){
		String str = null;
		while(true) {
			str = nextLine(msg);
			if(!pred.test(str)) {
				System.out.println(errorMsg);
			} else {
				return str;
			}
		}
	}
	public static int nextInt(String msg, Predicate<String> pred, String errorMsg) {
		return Integer.parseInt(nextLine(msg, pred, errorMsg));
	}
	
	public static boolean chkHangle(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) < '가' || str.charAt(i) > '힣') {
				return false;
			}
		}
		return true;
	}

	public static int chkCarRange(int value) {
		return value;
	}

	public static int chkCarRange(int start, int end, int value) {
		if (value < start || value > end) {
			throw new RangeException();
		}
		return value;
	}
	
	public static int chkAreaRange(int value) {
		return value;
	}

	public static int chkAreaRange(int start, int end, int value) {
		if (value < start || value > end) {
			throw new RangeException();
		}
		return value;
	}
	public static int chkCarRange2(int value) {
		if(value > 5 || 1 > value) {
			throw new RangeException();
		}
		return value;
	}
	
	public static <T> void save() {
		Map<String, List<?>> map = new HashMap<>();
		map.put("users", UserService.getInstance().getUsers());
		map.put("reviews", ReviewService.getInstance().getReviews());
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("users.ser"));
			oos.writeObject(map);
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T load(String name) {
		T ret = (T)new ArrayList<>();
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("users.ser")); 
			ret = ((Map<String, T>) ois.readObject()).get(name);
			ois.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return ret;
	}
}
