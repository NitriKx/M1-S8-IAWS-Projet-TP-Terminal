package iaws.tblabsauzzya.ugmont.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by terry on 15/04/15.
 */
@XmlRootElement
public class Film {

    private String title;
    private String type;
    private String year;
    private String imdbID;

    public Film(String title, String type, String year, String imdbID) {
        this.title = title;
        this.type = type;
        this.year = year;
        this.imdbID = imdbID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }
}
