CREATE TABLE IF NOT EXISTS t_drone (
    id                INTEGER PRIMARY KEY AUTOINCREMENT,
    model             TEXT    NOT NULL,
    manufacturer      TEXT    NOT NULL,
    max_flight_time   INTEGER,
    max_range         INTEGER,
    weight            REAL,
    payload_capacity  REAL,
    created_at        TEXT    NOT NULL,
    updated_at        TEXT    NOT NULL
);
