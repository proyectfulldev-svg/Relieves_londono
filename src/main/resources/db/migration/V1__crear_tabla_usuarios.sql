CREATE TABLE usuarios
(
    id            BIGINT IDENTITY(1,1) NOT NULL,
    email         NVARCHAR(255)        NOT NULL,
    password_hash NVARCHAR(255)        NOT NULL,
    nombre        NVARCHAR(150)        NOT NULL,
    rol           NVARCHAR(20)         NOT NULL,
    activo        BIT       NOT NULL CONSTRAINT df_usuarios_activo DEFAULT 1,
    created_at    DATETIME2 NOT NULL CONSTRAINT df_usuarios_created_at DEFAULT SYSDATETIME(),
    updated_at    DATETIME2 NOT NULL CONSTRAINT df_usuarios_updated_at DEFAULT SYSDATETIME(),
    CONSTRAINT pk_usuarios PRIMARY KEY (id),
    CONSTRAINT uq_usuarios_email UNIQUE (email),
    CONSTRAINT ck_usuarios_rol CHECK (rol IN ('ADMIN', 'OPERARIO'))
);
