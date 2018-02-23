package temp.xgzh.util;

import java.util.Properties;

public class CodeGeneration {
	public static void main(String[] args) {
		GenerationEntityCode.Generation("aAa.aaa");
	}
}

class GenerationEntityCode {
	static void Generation(String ClassName) {
		Generation(ClassName, null);
	}

	static void Generation(String FullClassName, Properties ds) {
		int lastSpot = FullClassName.indexOf(".");
		if (lastSpot > 0) {
			String className = FullClassName.substring(lastSpot + 1);
			String packageName = FullClassName.substring(0, lastSpot);
			System.out.println(className);
			System.out.println(packageName);
		}

	}

}