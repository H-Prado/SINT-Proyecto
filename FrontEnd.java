package p2;

import java.io.*;
import java.util.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;


public class FrontEnd {
  static public void noNavegadorSinActor(HttpServletResponse response) throws IOException {
    response.setContentType("text/xml");
    PrintWriter out = response.getWriter();
    out.println("<wrongRequest>no param:pid</wrongRequest>");
  }

  static public void noNavegadorSinLang(HttpServletResponse response) throws IOException {
    response.setContentType("text/xml");
    PrintWriter out = response.getWriter();
    out.println("<wrongRequest>no param:plang</wrongRequest>");
  }

  static public void noNavegadorContraErronea(HttpServletResponse response) throws IOException {
    response.setContentType("text/xml");
    PrintWriter out = response.getWriter();
    out.println("<wrongRequest>bad passwd</wrongRequest>");
  }

  static public void NoNavegadorsinContra(HttpServletResponse response) throws IOException {
    response.setContentType("text/xml");
    PrintWriter out = response.getWriter();
    out.println("<wrongRequest>no passwd</wrongRequest>");
  }

  static public void sinActor(HttpServletResponse response) throws IOException {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    out.println("no param:pid");
    out.println("<HTML><body><form>");
    out.println("<div> &copy;Hugo Prado Sueiro (2021 - 2022) </div>");
    out.println("</form></body></HTML>");
  }

  static public void sinLang(HttpServletResponse response) throws IOException {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    out.println("no param:plang");
    out.println("<HTML><body><form>");
    out.println("<div> &copy;Hugo Prado Sueiro (2021 - 2022) </div>");
    out.println("</form></body></HTML>");
  }

  static public void contraErronea(HttpServletResponse response) throws IOException {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    out.println("Revisa la contraseña, por favor");
    out.println("<HTML><body><form>");
    out.println("<div> &copy;Hugo Prado Sueiro (2021 - 2022) </div>");
    out.println("</form></body></HTML>");
  }

  static public void sinContra(HttpServletResponse response) throws IOException {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    out.println("No has introducido ninguna contraseña");
    out.println("<HTML><body><form>");
    out.println("<div> &copy;Hugo Prado Sueiro (2021 - 2022) </div>");
    out.println("</form></body></HTML>");
  }

  // PHASE 01 Inicio

  static public void HTML01(HttpServletResponse response) throws IOException {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();

    out.println("<h1>Cines Prado</h1>");
    out.println("<h2>Bienvenidos a este servicio</h2>");
    out.println("<h3>Selecciona la consulta a realizar: </h3>");
    out.println("<h4><p><a href='?p=39495406JJ&pphase=02'>Ver los ficheros erróneos</a></p></h4>");
    out.println(
        "<h4><p><a href='?p=39495406JJ&pphase=21'>Consulta 2: filmografía de un protagonista en un idioma</a></p></h4>");
    out.println("<HTML><body><form>");
    out.println("<div> &copy;Hugo Prado Sueiro (2021 - 2022) </div>");
    out.println("</form></body></HTML>");
  }

  static public void XML01(HttpServletResponse response) throws IOException {
    response.setContentType("text/xml");
    PrintWriter out = response.getWriter();
    out.println("<service>");
    out.println("<status>OK</status>");
    out.println("</service>");
  }

  // PHASE 02 Ficheros con fallos

  static public void HTML02(HttpServletResponse response, ArrayList<String> listaErrors) throws IOException {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    String error = "";
    int numErrors = 0;
    int numFatalErrors = 0;
    int numWarnings = 0;

    for (int x = 0; x < listaErrors.size(); x++) {
      if (listaErrors.get(x).startsWith("h"))
        numErrors = numErrors + 1;
      if (listaErrors.get(x).startsWith("m"))
        numFatalErrors = numFatalErrors + 1;
      if (!listaErrors.get(x).startsWith("m") && !listaErrors.get(x).startsWith("h"))
        numWarnings = numWarnings + 1;
    }

    out.println("<h1>Cines Prado</h1>");
    out.println("<h4>Ficheros con warning: " + numWarnings + "</h4>");
    /*
     * for (int x=0; x < listaErrors.size(); x++) {
     * if(!listaErrors.get(x).startsWith("m") &&
     * !listaErrors.get(x).startsWith("h")){
     * error = listaErrors.get(x);
     * out.println("<h6><p>" + "    " + error + "</p></h6>");
     * }
     */
    out.println("<h4>Ficheros con Errores:" + numErrors + "</h4>");
    out.println("<listaErrors>");
    for (int x = 0; x < listaErrors.size(); x++) {
      if (listaErrors.get(x).startsWith("h")) {
        error = listaErrors.get(x);
        out.println("<h6><p>" + error + "</p></h6>");
      }
    }
    out.println("<h4>Ficheros con Errores Fatales: " + numFatalErrors + "</h4>");
    for (int x = 0; x < listaErrors.size(); x++) {
      if (listaErrors.get(x).startsWith("m")) {
        error = "http://alberto.gil.webs.uvigo.es/SINT/21-22/" + listaErrors.get(x);
        out.println("<h6><p>" + error + "</p></h6>");
      }
    }
    out.println("</listaErrors>");
    out.println("<p><a href='?p=39495406JJ&pphase=01'>Atrás</a></p>");
    out.println("<HTML><body><form>");
    out.println("<h5><div> &copy;Hugo Prado Sueiro (2021 - 2022) </div></h5>");
    out.println("</form></body></HTML>");
  }

  static public void XML02(HttpServletResponse response, ArrayList<String> listaErrors) throws IOException {
    response.setContentType("text/xml");
    PrintWriter out = response.getWriter();

    out.println("<wrongDocs>");

    out.println("<warnings>");
    out.println("</warnings>");

    out.println("<errors>");
    for (int x = 0; x < listaErrors.size(); x++) {
      if(listaErrors.get(x).startsWith("h")) out.println("<error><file>" + listaErrors.get(x) + "</file></error>");
    }
    out.println("</errors>");

    out.println("<fatalerrors>");
    for (int x = 0; x < listaErrors.size(); x++) {
         if(listaErrors.get(x).startsWith("m")) out.println("<fatalerror><file>" + listaErrors.get(x) + "</file></fatalerror>");
    }
    out.println("</fatalerrors>");

    out.println("</wrongDocs>");
  }

  // PHASE 21 Selecciona un idioma

  static public void HTML21(HttpServletResponse response, ArrayList<String> listaLanguages) throws IOException {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    String plang = "null";

    out.println("<h1>Cines Prado</h1>");
    out.println("<h3>Consulta 2: Fase 1");
    out.println("<h4>Selecciona un idioma:");
    out.println("<listaLanguages>");
    for (int x = 0; x < listaLanguages.size(); x++) {
      int y = x + 1;
      plang = listaLanguages.get(x);
      out.println("<h6>" + y + ".<a href='?p=39495406JJ&pphase=22&plang=" + plang + "'>" + plang + "</a>" + "</h6>");
    }
    out.println("</listaErrors>");
    out.println("<p><a href='?p=39495406JJ&pphase=01'>Inicio</a></p>");
    out.println("<HTML><body><form>");
    out.println("<h5><div> &copy;Hugo Prado Sueiro (2021 - 2022) </div></h5>");
    out.println("</form></body></HTML>");
  }

  static public void XML21(HttpServletResponse response, ArrayList<String> listaLanguages) throws IOException {
    response.setContentType("text/xml");
    PrintWriter out = response.getWriter();

    out.println("<langs>");

    for (int x = 0; x < listaLanguages.size(); x++) {
      out.println("<lang>" + listaLanguages.get(x) + "</lang>");
    }

    out.println("</langs>");
  }

  // PHASE 22 Selecciona un actor

  static public void HTML22(HttpServletResponse response, String lang, ArrayList<Cast> listaCasts) throws IOException {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    String pid = "null";
    String pname = "null";

    out.println("<h1>Cines Prado</h1>");
    out.println("<h3>Consulta 2: Fase 2 (Idioma =  " + lang + ")</h3>");
    out.println("<h4>Selecciona un proptagonista:");
    for (int x = 0; x < listaCasts.size(); x++) {
      int y = x + 1;
      pid = listaCasts.get(x).getId();
      pname = listaCasts.get(x).getName();
      out.println("<h6>" + y + ".<a href='?p=39495406JJ&pphase=23&plang=" + lang + "&pid=" + pid + "'>Actor='" + pname
          + "'</a>--- ID = '" + listaCasts.get(x).getId() + "' --- Contacto = '" + listaCasts.get(x).getContact()
          + "'</h6>");
    }
    out.println("<p><a href='?p=39495406JJ&pphase=21'>Atrás</a></p>");
    out.println("<HTML><body><form>");
    out.println("<h5><div> &copy;Hugo Prado Sueiro (2021 - 2022) </div></h5>");
    out.println("</form></body></HTML>");
  }

  static public void XML22(HttpServletResponse response, ArrayList<Cast> listaCasts) throws IOException {
    response.setContentType("text/xml");
    PrintWriter out = response.getWriter();

    out.println("<thecast>");
    for (int x = 0; x < listaCasts.size(); x++) {
      out.println(
          "<cast" + " id='" + listaCasts.get(x).getId() +
              "' contact='" + listaCasts.get(x).getContact()+"'>"+listaCasts.get(x).getName()+"</cast>");
    }
    out.println("</thecast>");
  }

  // PHASE 23 Muestra las peliculas acorde a lo seleccionado

  static public void HTML23(HttpServletResponse response, String lang, Cast cast, ArrayList<Movie> listaMovies)
      throws IOException {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();

    out.println("<h2>Cines Prado</h2>");
    out.println("<h3>Consulta 2: Fase 3 (Idioma = " + lang + ",Protaginista = " + cast.getId() + ")</h3>");
    out.println("<h3>Este es el resultado de la consulta:");
    out.println("<listaMovies>");
    for (int x = 0; x < listaMovies.size(); x++) {
      int y = x + 1;
      out.println("<h5>" + y + ".Pelicula = '" + listaMovies.get(x).getTitle() +"("+listaMovies.get(x).getYear()+")"+
          "' --- Géneros = '" + listaMovies.get(x).getGenre() +
          "' --- Sinopsis = '" + listaMovies.get(x).getSinopsis() +
          "'</h5>");
    }
    out.println("</listaMovies>");
    out.println("<p><a href='?p=39495406JJ&pphase=22&plang=" + lang + "'>Atrás</a></p>");
    out.println("<p><a href='?p=39495406JJ&pphase=01'>Inicio</a></p>");
    out.println("<HTML><body><form>");
    out.println("<h5><div> &copy;Hugo Prado Sueiro (2021 - 2022) </div></h5>");
    out.println("</form></body></HTML>");

  }

  static public void XML23(HttpServletResponse response, ArrayList<Movie> listaMovies) throws IOException {
    response.setContentType("text/xml");
    PrintWriter out = response.getWriter();

    out.println("<movies>");
    for (int x = 0; x < listaMovies.size(); x++) {
      out.println("<movie" + " year='" + listaMovies.get(x).getYear() +
          "' genres='" + listaMovies.get(x).getGenre() +
          "' synopsis='" + listaMovies.get(x).getSinopsis()+"'>"+listaMovies.get(x).getTitle()+"</movie>");
    }
    out.println("</movies>");
  }

}
