package test.java;
 
import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

import main.java.CustomCheckHtmlEscaping;
 
public class CustomCheckHtmlEscapingTest {
 
  @Test
  public void test()
  {
	  JavaCheckVerifier.verify("src/test/files/CustomCheckHtmlEscaping.java", new CustomCheckHtmlEscaping());
  }
 
}