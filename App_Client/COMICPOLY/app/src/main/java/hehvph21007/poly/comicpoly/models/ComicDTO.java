package hehvph21007.poly.comicpoly.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ComicDTO implements Parcelable {
    String _id;
    String name;
    String desc;
    String author;
    String publish;
    String image;
    List<String> content;

    public ComicDTO(String _id, String name, String desc, String author, String publish, String image, List<String> content) {
        this._id = _id;
        this.name = name;
        this.desc = desc;
        this.author = author;
        this.publish = publish;
        this.image = image;
        this.content = content;
    }

    protected ComicDTO(Parcel in) {
        _id = in.readString();
        name = in.readString();
        desc = in.readString();
        author = in.readString();
        publish = in.readString();
        image = in.readString();
        content = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(name);
        dest.writeString(desc);
        dest.writeString(author);
        dest.writeString(publish);
        dest.writeString(image);
        dest.writeStringList(content);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<ComicDTO> CREATOR = new Parcelable.Creator<ComicDTO>() {
        @Override
        public ComicDTO createFromParcel(Parcel in) {
            return new ComicDTO(in);
        }

        @Override
        public ComicDTO[] newArray(int size) {
            return new ComicDTO[size];
        }
    };

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublish() {
        return publish;
    }

    public void setPublish(String publish) {
        this.publish = publish;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }
}
