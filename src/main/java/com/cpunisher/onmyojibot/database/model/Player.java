package com.cpunisher.onmyojibot.database.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "players")
public class Player {

    public static final String ID_FIELD_NAME = "id";
    public static final String DRAW_COUNT_FIELD_NAME = "draw_count";
    public static final String R_COUNT_FIELD_NAME = "r_count";
    public static final String SR_COUNT_FIELD_NAME = "sr_count";
    public static final String SSR_COUNT_FIELD_NAME = "ssr_count";
    public static final String SP_COUNT_FIELD_NAME = "sp_count";

    @DatabaseField(unique = true, id = true, columnName = ID_FIELD_NAME, canBeNull = false)
    private long id;

    @DatabaseField(columnName = DRAW_COUNT_FIELD_NAME)
    private int drawCount;

    @DatabaseField(columnName = R_COUNT_FIELD_NAME)
    private int rCount;

    @DatabaseField(columnName = SR_COUNT_FIELD_NAME)
    private int srCount;

    @DatabaseField(columnName = SSR_COUNT_FIELD_NAME)
    private int ssrCount;

    @DatabaseField(columnName = SP_COUNT_FIELD_NAME)
    private int spCount;

    public Player() {}

    public Player(long id, int drawCount) {
        this.id = id;
        this.drawCount = drawCount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getDrawCount() {
        return drawCount;
    }

    public void setDrawCount(int drawCount) {
        this.drawCount = drawCount;
    }

    public int getrCount() {
        return rCount;
    }

    public void setrCount(int rCount) {
        this.rCount = rCount;
    }

    public int getSrCount() {
        return srCount;
    }

    public void setSrCount(int srCount) {
        this.srCount = srCount;
    }

    public int getSsrCount() {
        return ssrCount;
    }

    public void setSsrCount(int ssrCount) {
        this.ssrCount = ssrCount;
    }

    public int getSpCount() {
        return spCount;
    }

    public void setSpCount(int spCount) {
        this.spCount = spCount;
    }
}
