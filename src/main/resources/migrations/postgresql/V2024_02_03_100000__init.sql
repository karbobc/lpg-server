CREATE TABLE t_user
(
    id         BIGINT                                     NOT NULL
        CONSTRAINT t_user_pk_id
            PRIMARY KEY,
    real_name  VARCHAR(8)   DEFAULT ''::CHARACTER VARYING NOT NULL,
    mobile     VARCHAR(24)  DEFAULT ''::CHARACTER VARYING NOT NULL,
    address    VARCHAR(128) DEFAULT ''::CHARACTER VARYING NOT NULL,
    state      SMALLINT     DEFAULT 0                     NOT NULL,
    version    INTEGER      DEFAULT 0                     NOT NULL,
    created_at TIMESTAMP    DEFAULT CURRENT_TIMESTAMP     NOT NULL,
    updated_at TIMESTAMP    DEFAULT CURRENT_TIMESTAMP     NOT NULL
);

CREATE TABLE t_cylinder
(
    id         BIGINT                                    NOT NULL
        CONSTRAINT t_cylinder_pk_id
            PRIMARY KEY,
    serial_no  VARCHAR(32) DEFAULT ''::CHARACTER VARYING NOT NULL
        CONSTRAINT t_cylinder_unique_idx_serial_no
        UNIQUE,
    barcode    VARCHAR(32) DEFAULT ''::CHARACTER VARYING NOT NULL
        CONSTRAINT t_cylinder_unique_idx_barcode
        UNIQUE,
    state      SMALLINT    DEFAULT 0                     NOT NULL,
    version    INTEGER     DEFAULT 0                     NOT NULL,
    created_at TIMESTAMP   DEFAULT CURRENT_TIMESTAMP     NOT NULL,
    updated_at TIMESTAMP   DEFAULT CURRENT_TIMESTAMP     NOT NULL
);

CREATE TABLE t_delivery
(
    id          BIGINT                              NOT NULL
        CONSTRAINT t_delivery_pk_id
            PRIMARY KEY,
    user_id     BIGINT                              NOT NULL,
    cylinder_id BIGINT                              NOT NULL,
    state       SMALLINT  DEFAULT 0                 NOT NULL,
    version     INTEGER   DEFAULT 0                 NOT NULL,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE FUNCTION func_trigger_set_timestamp()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = current_timestamp;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER set_timestamp
    BEFORE UPDATE
    ON t_user
    FOR EACH ROW
    EXECUTE PROCEDURE func_trigger_set_timestamp();

CREATE TRIGGER set_timestamp
    BEFORE UPDATE
    ON t_cylinder
    FOR EACH ROW
    EXECUTE PROCEDURE func_trigger_set_timestamp();

CREATE TRIGGER set_timestamp
    BEFORE UPDATE
    ON t_delivery
    FOR EACH ROW
    EXECUTE PROCEDURE func_trigger_set_timestamp();