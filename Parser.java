package p2;


import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;
import java.util.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.*;

public class Parser {
  static HashMap<String, Document> documentos = new HashMap<String, Document>();
  String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
  String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";
  String JAXP_SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";
  String ruta = "http://alberto.gil.webs.uvigo.es/SINT/21-22/";
  int bucle = 1;

  public ArrayList<String> parse(String ubicacion) throws ParserConfigurationException {
    ArrayList<String> encontrados, checkeados, xmlErroneos;
    DocumentBuilderFactory dbFact;
    DocumentBuilder dBuilder;
    Document documento = null;
    ErrorParser error;
    String ficheroActual;
    NodeList nList;
    File schema_parser;
    int contador = 0;
    int x;
    encontrados = new ArrayList<String>();
    checkeados = new ArrayList<String>();
    xmlErroneos = new ArrayList<String>();
    error = new ErrorParser();
    schema_parser = new File(ubicacion);

    dbFact = DocumentBuilderFactory.newInstance();
    dbFact.setValidating(true);
    dbFact.setNamespaceAware(true);
    dbFact.setAttribute(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);
    dbFact.setAttribute(JAXP_SCHEMA_SOURCE, schema_parser);

    dBuilder = dbFact.newDocumentBuilder();
    dBuilder.setErrorHandler(error);

    encontrados.add("mml2001.xml");

    while (bucle == 1) {
      try {
        error.seterrorHandler(0);
        documento = dBuilder.parse(ruta + encontrados.get(contador));
        checkeados.add(encontrados.get(contador));
        if (error.geterrorHandler() == 1 || documento.getDocumentElement().getNodeName() != "Movies") {
          xmlErroneos.add(ruta + encontrados.get(contador));
          error.seterrorHandler(0);
        } else {
          documentos.put(encontrados.get(contador), documento);
          nList = documento.getElementsByTagName("MML");
          for (x = 0; x < nList.getLength(); x++) {
            ficheroActual = (String) nList.item(x).getTextContent().trim();
            if (!encontrados.contains(ficheroActual)) {
              encontrados.add(ficheroActual);
            }
          }
        }
      } catch (Exception e) {
        xmlErroneos.add(encontrados.get(contador));
      }
      contador++;
      if (contador == encontrados.size()) {
        bucle = 0;
      }
    }
    Collections.sort(xmlErroneos);
    return xmlErroneos;
  }

  public HashMap<String, Document> getDocumentos() {
    return documentos;
  }

}

// Si queremos recorrer el Hashmap para ver los documentos correctos añadimos un
// sout aqui con entry.getKey() y entry.getValue()
// for (Map.Entry entry : documentos.entrySet()) {}
