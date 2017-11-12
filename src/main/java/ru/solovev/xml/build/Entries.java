package ru.solovev.xml.build;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement(name = "entries")
public class Entries {

    @XmlElement(name = "entry", required = true)
    private ArrayList<EntryObj> list = new ArrayList<>();

    public Entries() {
    }

    public boolean add(EntryObj entryObj) {
        return list.add(entryObj);
    }

    public void setList(ArrayList<EntryObj> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "<entries> \n\t<entry>" + list + " \n\t</entry>\n</entries>";
    }
}
