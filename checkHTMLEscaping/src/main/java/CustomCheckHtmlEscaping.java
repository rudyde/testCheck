package main.java;
import java.io.IOException;
import java.util.List;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.semantic.Symbol.MethodSymbol;
import org.sonar.plugins.java.api.semantic.Type;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.Tree.Kind;

import com.google.common.collect.ImmutableList;
 
@Rule(
		key = "CustomCheckHTMLEscapingRule",
		name = "Either cov or <c: or <cms must be available",
		description = "Check code security in JSP and TAG file",
		priority = Priority.CRITICAL,
		tags = {"bug"}
		)

public class CustomCheckHtmlEscaping extends IssuableSubscriptionVisitor {
 
	public static void main(String[] args) throws IOException 
	{
		
	}
  @Override
  public List<Kind> nodesToVisit() {
    return ImmutableList.of(Kind.METHOD);
  }
 
  @Override
  public void visitNode(Tree tree)
  {
    MethodTree method = (MethodTree) tree;
    if (method.parameters().size() == 1) 
    {
      MethodSymbol symbol = method.symbol();
      Type firstParameterType = symbol.parameterTypes().get(0);
      Type returnType = symbol.returnType().type();
      if (returnType.is(firstParameterType.fullyQualifiedName())) 
      {
    	  reportIssue(method.simpleName(), "Neither cov nor <c: nor <cms nor is available. This code has security issue");
      }
    }
  }
}