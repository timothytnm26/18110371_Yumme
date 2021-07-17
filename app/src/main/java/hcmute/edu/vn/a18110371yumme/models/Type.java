package hcmute.edu.vn.a18110371yumme.models;

import java.io.Serializable;

public class Type implements Serializable {
    private String TypeID;
    private String TypeName;
    private String Description;
    private String Image;

    public String getTypeID() {return TypeID;    }

    public void setTypeID(String typeID) {
        TypeID = typeID;
    }

    public String getTypeName() {
        return TypeName;
    }

    public void setTypeName(String typeName) {
        TypeName = typeName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        this.Description = description;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        this.Image = image;
    }

    public Type() {
    }

    public Type(String typeID, String typeName, String description, String image) {
        this.TypeID = typeID;
        this.TypeName = typeName;
        this.Description = description;
        this.Image = image;
    }

    @Override
    public String toString() {
        return TypeName;
    }

}

