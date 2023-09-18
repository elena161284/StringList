package pro.sky.StringList;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class StringListApplication {

	public static void main(String[] args) {
		StringList stringList = new StringListImpl();
		stringList.add("test1"); // 0
		stringList.add("test2"); // 1
		stringList.add("test3"); // 2
		stringList.add("test4"); // 3

		stringList.add(3, "test6");

		stringList.remove(1);

		System.out.println(Arrays.toString(stringList.toArray()));

		stringList.clear();
		System.out.println(stringList.size());
	}
}