CREATE TABLE "public".email_sending
(
    id         serial PRIMARY KEY,
    recipient  VARCHAR(50)  NOT NULL,
    subject    varchar(150) NOT NULL,
    text       text         NOT NULL,
    status     smallint     NOT NULL,
    updated_at timestamp    NOT NULL,
    created_at timestamp    NOT NULL
);
