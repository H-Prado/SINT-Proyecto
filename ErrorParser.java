package p2;

import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.SAXParseException;

public class ErrorParser extends DefaultHandler {
  private int errorHandler;

  public ErrorParser() {
    int errorHandler = 0;
  }

  public int geterrorHandler() {
    return errorHandler;
  }

  public void seterrorHandler(int errorAux) {
    errorHandler = errorAux;
  }

  // Si hace falta ver los logs o algo podemos poner un sout
  public void warning(SAXParseException spe) {
	System.out.println("Warning: "+spe.toString()); 
 }

  public void error(SAXParseException spe) {
System.out.println("Error: "+spe.toString());
    seterrorHandler(1);
  }

  public void fatalError(SAXParseException spe) {
System.out.println("Fatal Error: "+spe.toString());
    seterrorHandler(1);
  }
}
