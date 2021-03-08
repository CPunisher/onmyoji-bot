package com.cpunisher.onmyojibot.database.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "players")
public class Player {

    public static final String ID_FIELD_NAME = "id";
    public static final String DRAW_COUNT_FIELD_NAME = "draw_count";

    @DatabaseField(unique = true, id = true, columnName = ID_FIELD_NAME, canBeNull = false)
    private Long id;

    @DatabaseField(columnName = DRAW_COUNT_FIELD_NAME)
    private int drawCount;

    public Player() {}

    public Player(Long id, int drawCount) {
        this.id = id;
        this.drawCount = drawCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getDrawCount() {
        return drawCount;
    }

    public void setDrawCount(int drawCount) {
        this.drawCount = drawCount;
    }
}
