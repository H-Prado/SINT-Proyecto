package p2;

import java.io.*;
import java.util.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.w3c.dom.*;
import javax.xml.parsers.ParserConfigurationException;
import jakarta.servlet.annotation.*;



public class Sint163P2 extends HttpServlet {

  private final static String contrasenha = "ProyectoSint";

  Parser parser = new Parser();
  HashMap<String, Document> xmls = new HashMap<String, Document>();
  private static DataModel dataModel = null;
  ArrayList<String> xmlErrors = new ArrayList<>();

  public void init(ServletConfig config) {
    ServletContext context;
    String ubicacion;
    try {
      context = config.getServletContext();
      ubicacion = context.getRealPath("/p2/mml.xsd");
      xmlErrors = parser.parse(ubicacion);
      xmls = parser.getDocumentos();
    } catch (ParserConfigurationException e) {
    }
    dataModel = new DataModel(xmls);
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException, NullPointerException {

    String contrasenhaUsuario, phase, auto, lang, id;
    contrasenhaUsuario = request.getParameter("p");
    phase = request.getParameter("pphase");
    auto = request.getParameter("auto");
    lang = request.getParameter("plang");
    id = request.getParameter("pid");

    if (phase == null) {
      phase = "01";
    }
    if (contrasenhaUsuario == null) {
      response.setCharacterEncoding("utf-8");
      if (auto == null) {
        FrontEnd.sinContra(response);
      } else if (!auto.equals("true")) {
        FrontEnd.sinContra(response);
      } else {
        FrontEnd.NoNavegadorsinContra(response);
      }
    } else if (!contrasenhaUsuario.equals(contrasenha)) {
      response.setCharacterEncoding("utf-8");
      if (auto == null) {
        FrontEnd.contraErronea(response);
      } else if (auto.equals("true")) {
        FrontEnd.contraErronea(response);
      } else {
        FrontEnd.noNavegadorContraErronea(response);
      }
    } else if ((phase.equals("22") || phase.equals("23")) && lang == null) {
      if (auto == null) {
        FrontEnd.sinLang(response);
      } else if (!auto.equals("true")) {
        FrontEnd.sinLang(response);
      } else {
        FrontEnd.noNavegadorSinLang(response);
      }
    } else if (phase.equals("23") && id == null) {
      if (auto == null) {
        FrontEnd.sinActor(response);
      }
      if (!auto.equals("true")) {
        FrontEnd.sinActor(response);
      } else {
        FrontEnd.noNavegadorSinActor(response);
      }
    } else {
      switch (phase) {
        case "null":
          this.doGetPhase01(request, response);
          break;
        case "01":
          this.doGetPhase01(request, response);
          break;
        case "02":
          this.doGetPhase02(request, response);
          break;
        case "21":
          this.doGetPhase21(request, response);
          break;
        case "22":
          this.doGetPhase22(request, response);
          break;
        case "23":
          this.doGetPhase23(request, response);
          break;
      }
    }
  }

  public void doGetPhase01(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String auto;
    auto = request.getParameter("auto");
    response.setCharacterEncoding("utf-8");

    if (auto == null) {
      FrontEnd.HTML01(response);
    } else if (!auto.equals("true")) {
      FrontEnd.HTML01(response);
    } else {
      FrontEnd.XML01(response);
    }
  }

  public void doGetPhase02(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String auto;
    auto = request.getParameter("auto");
    response.setCharacterEncoding("utf-8");

    if (auto == null) {
      FrontEnd.HTML02(response, xmlErrors);
    } else if (!auto.equals("true")) {
      FrontEnd.HTML02(response, xmlErrors);
    } else { // enviar XML
      FrontEnd.XML02(response, xmlErrors);
    }
  }

  public void doGetPhase21(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String auto;
    auto = request.getParameter("auto");
    ArrayList<String> listaLangs = new ArrayList<String>(dataModel.getQ2Langs());
    response.setCharacterEncoding("utf-8");

    if (auto == null) {
      FrontEnd.HTML21(response, listaLangs);
    } else if (!auto.equals("true")) {
      FrontEnd.HTML21(response, listaLangs);
    } else {
      FrontEnd.XML21(response, listaLangs);
    }
  }

  public void doGetPhase22(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String auto, lang;
    auto = request.getParameter("auto");
    lang = request.getParameter("plang");
    ArrayList<Cast> listaCast = new ArrayList<Cast>(dataModel.getQ2Cast(lang));
    response.setCharacterEncoding("utf-8");

    if (auto == null) {
      FrontEnd.HTML22(response, lang, listaCast);
    } else if (!auto.equals("true")) {
      FrontEnd.HTML22(response, lang, listaCast);
    } else { // enviar XML
      FrontEnd.XML22(response, listaCast);
    }
  }

  public void doGetPhase23(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String auto, lang, id;
    auto = request.getParameter("auto");
    lang = request.getParameter("plang");
    id = request.getParameter("pid");
    Cast cast = new Cast("name", id, "rol", "contact");
    ArrayList<Movie> listaMovie = new ArrayList<Movie>();
    listaMovie = dataModel.getQ2Movies(lang, cast);
    response.setCharacterEncoding("utf-8");

    if (auto == null) {
      FrontEnd.HTML23(response, lang, cast, listaMovie);
    } else if (!auto.equals("true")) {
      FrontEnd.HTML23(response, lang, cast, listaMovie);
    } else {
      FrontEnd.XML23(response, listaMovie);
    }
  }

}
