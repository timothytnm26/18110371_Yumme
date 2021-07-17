package hcmute.edu.vn.a18110371yumme.models;

import java.io.Serializable;

class AdoredStore implements Serializable {
    private int Like;

    public int getLike() {
        return Like;
    }

    public void setLike(int like) {
        Like = like;
    }

    public AdoredStore() {
    }

    public AdoredStore(int like) {

        Like = like;
    }
}
