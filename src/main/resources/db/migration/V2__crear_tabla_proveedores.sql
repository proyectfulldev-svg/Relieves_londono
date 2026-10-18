CREATE TABLE proveedores
(
    id         BIGINT IDENTITY(1,1) NOT NULL,
    nombre     NVARCHAR(150)        NOT NULL,
    producto   NVARCHAR(150)        NOT NULL,
    telefono   NVARCHAR(30)         NOT NULL,
    email      NVARCHAR(255)        NULL,
    direccion  NVARCHAR(255)        NULL,
    notas      NVARCHAR(1000)       NULL,
    activo     BIT       NOT NULL CONSTRAINT df_proveedores_activo DEFAULT 1,
    created_at DATETIME2 NOT NULL CONSTRAINT df_proveedores_created_at DEFAULT SYSDATETIME(),
    updated_at DATETIME2 NOT NULL CONSTRAINT df_proveedores_updated_at DEFAULT SYSDATETIME(),
    CONSTRAINT pk_proveedores PRIMARY KEY (id)
);

CREATE INDEX ix_proveedores_nombre ON proveedores (nombre);
