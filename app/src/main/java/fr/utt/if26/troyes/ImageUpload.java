package fr.utt.if26.troyes;

/**
 * Created by Elkin R on 10/12/2017.
 */

public class ImageUpload {

    public String name;
    public String url;
    public String description;


    public ImageUpload(){

    }

    public ImageUpload(String name, String description, String url) {
        this.name = name;
        this.description = description;
        this.url = url;

    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }


    public String getUrl() {
        return url;
    }


}
