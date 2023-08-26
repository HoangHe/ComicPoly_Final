package hehvph21007.poly.comicpoly.models;

import java.util.List;

public class CommentDTO {
    private String _id;
    private String content;
    private String time;
    private ComicDTO id_comic;
    private UserDTO id_user;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ComicDTO getId_comic() {
        return id_comic;
    }

    public void setId_comic(ComicDTO id_comic) {
        this.id_comic = id_comic;
    }

    public UserDTO getId_user() {
        return id_user;
    }

    public void setId_user(UserDTO id_user) {
        this.id_user = id_user;
    }

    public CommentDTO(String _id, String content, String time, ComicDTO id_comic, UserDTO id_user) {
        this._id = _id;
        this.content = content;
        this.time = time;
        this.id_comic = id_comic;
        this.id_user = id_user;
    }

    // Các getter và setter cho các trường

    public class ComicDTO {
        private String _id;
        private String name;
        private String desc;
        private String author;
        private String publish;
        private String image;
        private List<Object> content;
        private int __v;

        public ComicDTO(String _id, String name, String desc, String author, String publish, String image, List<Object> content, int __v) {
            this._id = _id;
            this.name = name;
            this.desc = desc;
            this.author = author;
            this.publish = publish;
            this.image = image;
            this.content = content;
            this.__v = __v;
        }

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

        public List<Object> getContent() {
            return content;
        }

        public void setContent(List<Object> content) {
            this.content = content;
        }

        public int get__v() {
            return __v;
        }

        public void set__v(int __v) {
            this.__v = __v;
        }
        // Các getter và setter cho các trường
    }

    public class UserDTO {
        private String _id;
        private String userName;
        private String passWord;
        private String email;
        private String fullName;
        private String token;
        private int __v;

        public UserDTO(String _id, String userName, String passWord, String email, String fullName, String token, int __v) {
            this._id = _id;
            this.userName = userName;
            this.passWord = passWord;
            this.email = email;
            this.fullName = fullName;
            this.token = token;
            this.__v = __v;
        }

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPassWord() {
            return passWord;
        }

        public void setPassWord(String passWord) {
            this.passWord = passWord;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public int get__v() {
            return __v;
        }

        public void set__v(int __v) {
            this.__v = __v;
        }
        // Các getter và setter cho các trường
    }

}
