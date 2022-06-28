package p2;

import java.util.*;
import org.w3c.dom.*;

public class DataModel {

  NodeList nodos = null;

  private HashMap<String, Document> docs;

  public DataModel(HashMap<String, Document> d) {
    docs = d;
  }

  public ArrayList<String> getQ2Langs() {
    ArrayList<String> listaLang = new ArrayList<String>();
    String cadenaLangs;
    String[] lang;

    for (Map.Entry<String, Document> entry : docs.entrySet()) {
      String key = entry.getKey();
      Document doc = entry.getValue();

      for (int i = 0; i < doc.getElementsByTagName("Movie").getLength(); i++) {
        Element eElement = (Element) doc.getElementsByTagName("Movie").item(i);
        cadenaLangs = eElement.getAttribute("langs");
        lang = cadenaLangs.split(" ");
        for (int z = 0; z < lang.length; z++)
          if (!listaLang.contains(lang[z]))
            listaLang.add(lang[z]);
      }
    }
    Collections.sort(listaLang);
    return listaLang;
  }

  public ArrayList<Cast> getQ2Cast(String lang) {
    // buscar en los DOM la información de las películas del año pyear
    ArrayList<Cast> listaCast = new ArrayList<Cast>();
    Element eElement2;
    boolean existe;
    String contacto = "";

    for (Map.Entry<String, Document> entry : docs.entrySet()) {
      String key = entry.getKey();
      Document doc = entry.getValue();
      for (int i = 0; i < doc.getElementsByTagName("Movie").getLength(); i++) {
        Element eElement = (Element) doc.getElementsByTagName("Movie").item(i);
        // Si el elemento Movie que estamos analizando esta en el idioma que nos
        // interesa seguimos
        if (eElement.getAttribute("langs").contains(lang)) {
          // bucle para recorrer todos los Casts de nuestro Movie actual
          for (int w = 0; w < eElement.getElementsByTagName("Cast").getLength(); w++) {
            eElement2 = (Element) eElement.getElementsByTagName("Cast").item(w);
            // Cogemos el contacto
            contacto = "";
            try {
              contacto = eElement2.getElementsByTagName("Email").item(0).getTextContent();
            } catch (NullPointerException e) {
              contacto = eElement2.getElementsByTagName("Phone").item(0).getTextContent();
            }

            Cast actor = new Cast(eElement2.getElementsByTagName("Name").item(0).getTextContent(),
                eElement2.getAttribute("id"), null, contacto);
            existe = false;
            for (int b = 0; b < listaCast.size(); b++) {
              if (listaCast.get(b).getId().equals(eElement2.getAttribute("id")))
                existe = true;
            }
            if (!existe) {
              listaCast.add(actor);
              System.out.println(
                  "Actor: " + eElement2.getElementsByTagName("Name").item(0).getTextContent() +
                      "--- ID = " + eElement2.getAttribute("id") +
                      " --- Contacto = " + contacto);
            }
          }
        }
      }
    }
    // System.out.println("Hay " + listaCast.size() + " actores que interpretan
    // peliculas en " + lang + "\n");
    Collections.sort(listaCast);
    ArrayList<Cast> ordenCasting = new ArrayList<Cast>();
    ArrayList<Cast> ordenCastingDos = new ArrayList<Cast>();
    ArrayList<Cast> ordenCastingDefinitivo = new ArrayList<Cast>();

    for(int w = 0; w < listaCast.size(); w++){
      if(listaCast.get(w).getContact().matches("^\\d{9}$")) ordenCasting.add(listaCast.get(w));
      else ordenCastingDos.add(listaCast.get(w));
    }
    for(int xd = 0; xd < ordenCasting.size(); xd++) ordenCastingDefinitivo.add(ordenCasting.get(xd));
    for(int xp = 0; xp < ordenCastingDos.size(); xp++) ordenCastingDefinitivo.add(ordenCastingDos.get(xp));
    return ordenCastingDefinitivo;
  }

  public ArrayList<Movie> getQ2Movies(String lang, Cast cast) {
    ArrayList<Movie> listaMovies = new ArrayList<Movie>();
    NodeList listaSinopsis;
    Element eElement2;
    String generos = "";
    String id = cast.getId();
    String sinopsis;
    String year;
    int z;

    for (Map.Entry<String, Document> entry : docs.entrySet()) {
      String key = entry.getKey();
      Document doc = entry.getValue();
      year = doc.getElementsByTagName("Year").item(0).getTextContent();
      for (int i = 0; i < doc.getElementsByTagName("Movie").getLength(); i++) {
        Element eElement = (Element) doc.getElementsByTagName("Movie").item(i);
        // Si el elemento Movie que estamos analizando esta en el idioma que nos
        // interesa seguimos
        if (eElement.getAttribute("langs").contains(lang)) {
          // bucle para recorrer todos los Casts de nuestro Movie actual
          for (int w = 0; w < eElement.getElementsByTagName("Cast").getLength(); w++) {
            eElement2 = (Element) eElement.getElementsByTagName("Cast").item(w);
            // Si el Cast pertenece al actor que nos interesa, cogemos la informacion
            if (eElement2.getAttribute("id").equals(id)) {
              System.out.println(eElement2.getElementsByTagName("Name").item(0).getTextContent());
              // Cogemos todos los generos
              generos = eElement.getElementsByTagName("Genre").item(0).getTextContent();
              z = 1;
              while (eElement.getElementsByTagName("Genre").item(z) != null) {
                generos = generos + "," + eElement.getElementsByTagName("Genre").item(z).getTextContent();
                z++;
              }
              // Cogemos la sinopsis que no está entre tags en el XML
              sinopsis = "";
              listaSinopsis = eElement.getChildNodes();
              for (int v = 0; v < listaSinopsis.getLength(); v++) {
                Node child = listaSinopsis.item(v);
                if (child.getNodeType() == org.w3c.dom.Node.TEXT_NODE) {
                  sinopsis = sinopsis + child.getNodeValue();
                }
              }
              sinopsis = sinopsis.trim();
              // Añadimos la pelicula a la lista
              listaMovies.add(
                  new Movie(eElement.getElementsByTagName("Title").item(0).getTextContent(), null, generos, sinopsis, year));
              // Imprimimos el resultado
              System.out.println(
                  "Pelicula = " + eElement.getElementsByTagName("Title").item(0).getTextContent() +
                      " --- Generos --- = " + generos +
                      " --- Sinopsis = " + sinopsis +
                      "\n\n");
            }
          }
        }
      }
    }
    // Ordenamos la lista de peliculas antes de retornarla
    Collections.sort(listaMovies);
    ArrayList<Movie> entregaDefinitiva = new ArrayList<Movie>();
    int contenido = 0;
    for(int xp = 0; xp < listaMovies.size(); xp++){
       contenido = 0;
      for(int xd = 0; xd < entregaDefinitiva.size(); xd++){
        if(listaMovies.get(xp).getTitle().equals(entregaDefinitiva.get(xd).getTitle()) && listaMovies.get(xp).getSinopsis().equals(entregaDefinitiva.get(xd).getSinopsis())) contenido = 1;
      }
      if(contenido == 0) entregaDefinitiva.add(listaMovies.get(xp));
    }
    return entregaDefinitiva;
  }
}
