package p2;

public class Movie implements Comparable<Movie> {

    private String title;
    private String language;
    private String genre;
    private String sinopsis;
    public String year;

    public Movie(String title, String language, String genre, String sinopsis, String year) {
        this.title = title;
        this.language = language;
        this.genre = genre;
        this.sinopsis = sinopsis;
        this.year = year;
    }

    public String getTitle() {
        return this.title;
    }

    public String getLanguage() {
        return this.language;
    }

    public String getGenre() {
        return this.genre;
    }

    public String getSinopsis() {
        return this.sinopsis;
    }

    public String getYear() {
        return this.year;
    }

    public void setYear(String year){
        this.year = year;
    }
    
    public void setGenre(String genre){
        this.genre = genre;
    }
    
    public void setLanguage(String language){
        this.language = language;
    }
    
    public void setTitle(String title){
        this.title = title;
    }
    
    public void setSinopsis(String yesinopsisar){
        this.sinopsis = sinopsis;
    }

    public int compareTo(Movie o) {
        // Si ambas tienen la misma longitud comparamos por orden alfabetico del titulo
        // Invirtiendo el return se ordena en sentido contrario
        if (o.getSinopsis().length() == this.getSinopsis().length()) {
            return o.getTitle().compareTo(this.getTitle());

        }
        return this.getSinopsis().length() - o.getSinopsis().length();
    }
}