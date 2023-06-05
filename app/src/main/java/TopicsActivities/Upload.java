package TopicsActivities;

public class Upload {
    private String imageName;
    private String imageUrl;

    public Upload(){
        //constructor method for firebase
    }

    public Upload(String imageName, String imageUrl) {
        //trim method -> remove all additional spaces " noran essa " --> "noranessa
        if( imageName.trim().equals("") )//check if the name exist
            imageName = "No Name";
        this.imageName = imageName;
        this.imageUrl = imageUrl;
    }

    public String getImageName() {
        return imageName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
