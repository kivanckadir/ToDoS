package mobileprogramming.kivanc.com.todos.model;

/**
 * Created by Kivanc on 12.12.2017.
 */

public class Todo {
    private long id;
    private String title;
    private String detail;
    private String date;
    private String time;
    private String priority;

    public Todo(long id, String title, String detail, String date, String time, String priority) {
        this.id = id;
        this.title = title;
        this.detail = detail;
        this.date = date;
        this.time = time;
        this.priority = priority;
    }

    public Todo() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", detail='" + detail + '\'' +
                ", date=" + date +
                ", time=" + time +
                ", priority='" + priority + '\'' +
                '}';
    }
}
