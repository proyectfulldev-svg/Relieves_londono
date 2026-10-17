# Relieves Londoño — Backend

Sistema de gestión para un taller de mármol funerario: facturas, proveedores, órdenes de compra,
inventario y estadísticas. Esta API REST la consume un frontend en Angular.

## Stack

- Java 21, Spring Boot 4.1.1, Maven (usar `mvnw` / `mvnw.cmd`)
- SQL Server 2025 local: `localhost:1433`, base de datos `relieves_londono`, usuario `sa`
- La contraseña de la base de datos se lee de la variable de entorno `DB_PASSWORD` (nunca escribirla en archivos)
- Spring Web MVC, Spring Data JPA, Validation, Flyway (`flyway-sqlserver`), Lombok, DevTools, Actuator
- Tests de integración con Testcontainers (SQL Server en Docker)
- Todavía NO hay Spring Security; se agregará junto con la autenticación sobre la tabla `usuarios`

Paquete base: `com.relieveslondono.gestion`

## Convenciones

- **Arquitectura por módulo**: cada módulo (factura, proveedor, ordencompra, inventario, etc.) tiene su
  propio paquete con `Controller`, `Service`, `Repository`, `Entity` y un subpaquete `dto/`.
  Ejemplo: `com.relieveslondono.gestion.proveedor.ProveedorService`.
- **La lógica de negocio va SOLO en el Service.** Controllers y Repositories no contienen reglas de negocio.
- **Los Controllers reciben y devuelven DTOs, nunca entidades.**
- **Base de datos**: cada cambio es una nueva migración Flyway en `src/main/resources/db/migration`
  (`V<n>__descripcion.sql`). Nunca editar una migración ya aplicada.
  Hibernate está en `ddl-auto=validate`: no crea ni modifica tablas.
- En SQL Server usar `NVARCHAR` para textos.
- **Nombres de clases y métodos en español**, siguiendo el dominio del negocio
  (p. ej. `FacturaService.crearFactura`, `ProveedorRepository.buscarPorNit`).
- No agregar dependencias sin que se pidan explícitamente.
