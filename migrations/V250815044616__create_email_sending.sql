CREATE TABLE "public".email_sending
(
    id         bigserial PRIMARY KEY,
    recipient  VARCHAR(50)  NOT NULL,
    subject    varchar(150) NOT NULL,
    model      jsonb        NOT NULL,
    reason     smallint     NOT NULL,
    format     smallint     NOT NULL,
    created_at timestamp    NOT NULL,
    updated_at timestamp    NOT NULL
);
