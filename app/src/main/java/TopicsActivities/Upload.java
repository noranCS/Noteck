package TopicsActivities;

public class Upload {
    private String text;
    private String imageUrl;

    public Upload(){
        //constructor method for firebase
    }

    public Upload(String imageName, String imageUrl) {
        //trim method -> remove all additional spaces " noran essa " --> "noranessa
        if( imageName.trim().equals("") )//check if the name exist
            imageName = "No Name";
        this.text = imageName;
        this.imageUrl = imageUrl;
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
}
