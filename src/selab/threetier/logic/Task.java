package selab.threetier.logic;

import selab.threetier.storage.Storage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Task extends Entity implements Comparator<Task> {
    private String title;
    private Date start;
    private Date end;

    public String getTitle() { return title; }
    public void setTitle(String value) { title = value; }

    public void setStart(Date value) { 
        start = value;
        if (this.end) {
            if (start > end) {
                throw new IOException("Start time is bigger than end time!");
            }
        }
     }
    public String getStartDate() {
        return new SimpleDateFormat("YYYY-MM-DD").format(start);
    }
    public String getStartTime() {
        return new SimpleDateFormat("HH:mm:ss").format(start);
    }
    public String getStartDateTime() {
        return new SimpleDateFormat("YYYY-MM-DD HH:mm:ss").format(start);

    }
    public String getEndDateTime() {
        return new SimpleDateFormat("YYYY-MM-DD HH:mm:ss").format(end);
    }
    public void setEnd(Date value) { 
        end = value; 
         if (this.start) {
            if (start > end) {
                throw new IOException("Start time is bigger than end time!");
            }
        }
    }
    public String getEndTime() {
        return new SimpleDateFormat("HH:mm:ss").format(end);
    }

    public void save() {
        for (Task task : this.getAll()) {
            if (task.getStartDateTime < this.getStartDateTime && task.getEndDateTime > this.getEndDateTime) {
                 throw new IOException("Time overlap!");
            }
            if (task.getStartDateTime > this.getStartDateTime && task.getEndDateTime < this.getEndDateTime) {
                 throw new IOException("Time overlap!");
            }
            if (task.getStartDateTime > this.getStartDateTime && task.getStartDateTime < this.getEndDateTime) {
                 throw new IOException("Time overlap!");
            }
        }

        Storage.getInstance().getTasks().addOrUpdate(this);
    }

    public void remove() {
         Storage.getInstance().getTasks().delete(this);
    }

    public static ArrayList<Task> getAll() {
       return Storage.getInstance().getTasks().getAll().sort(new Task());
    }
    public int compare(Task t1, Task t2) {
        return t2.getStartDateTime().compareToIgnoreCase(t1.getStartDateTime());
    }
    public static removeByID(int id) {
        for (Task task : this.getAll()) {
            if (id == item.getId()) { 
                this.remove(item);
                return;
            }
        } 
        throw new IOException("ID not found!");

    }
}
