package ru.solovev.xml.build;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class EntryObj {

    @XmlElement(name = "field", required = true)
    private int field;

    public EntryObj() {
    }

    public EntryObj(int field) {
        this.field = field;
    }

   /* public int getField() {
        return field;
    }*/

    public void setField(int field) {
        this.field = field;
    }

    @Override
    public String toString() {
        return "\n\t\t<field> " + field + " </field>";
    }
}
