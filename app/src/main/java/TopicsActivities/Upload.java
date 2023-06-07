package TopicsActivities;

import androidx.annotation.NonNull;

public class Upload {
    private String text;
    private String imageUrl;
    private String imageName;
    private String uploadId;

    public Upload(){
        //constructor method for firebase
    }

    public Upload(String text, String imageUrl,String imageName,String uploadId) {
        //trim method -> remove all additional spaces " noran essa " --> "noranessa
        if( text.trim().equals("") )//check if the name exist
            text = "No Name";
        this.text = text;
        this.imageUrl = imageUrl;
        this.imageName = imageName;
        this.uploadId = uploadId;
    }

    public String getText() {
        return text;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setText(String imageName) {
        this.text = imageName;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getId() {
        return uploadId;
    }

    public void setId(String uploadId) {
        this.uploadId = uploadId;
    }

    @Override
    public String toString() {
        return "Upload{" +
                "text='" + text + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", imageName='" + imageName + '\'' +
                ", uploadId='" + uploadId + '\'' +
                '}';
    }
}
