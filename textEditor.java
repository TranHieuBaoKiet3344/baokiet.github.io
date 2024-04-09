package models;

public class textEditor {
    String name;
    String content;
    String path;

    public textEditor(String name, String content, String path) {
        this.name = name;
        this.content = content;
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
