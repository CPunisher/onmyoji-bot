package com.cpunisher.onmyojibot.database.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "draw_result")
public class DrawResult {

    public static final String PLAYER_ID_FIELD = "player_id";
    public static final String SHIKIGAMI_ID = "shikigami_id";

    @DatabaseField(columnName = PLAYER_ID_FIELD, canBeNull = false)
    private long playerId;

    @DatabaseField(columnName = SHIKIGAMI_ID, canBeNull = false)
    private long shikigamiId;

    public DrawResult() {}

    public DrawResult(long playerId, long shikigamiId) {
        this.playerId = playerId;
        this.shikigamiId = shikigamiId;
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public long getShikigamiId() {
        return shikigamiId;
    }

    public void setShikigamiId(int shikigamiId) {
        this.shikigamiId = shikigamiId;
    }
}
