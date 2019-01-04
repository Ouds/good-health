package scalajava;

public class HybridTest4j {

	public static void main(String[] args) {
		Hybrid4Scala h4Scala = new Hybrid4Scala();
		String str = h4Scala.getStr();
		System.out.println("run in Java, " + str);

		ComplexFunction cmplxFun = new ComplexFunction();
		cmplxFun.greet();
	}
}