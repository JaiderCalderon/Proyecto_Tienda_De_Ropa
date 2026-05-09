# Sistema de Gestión - Tienda de Ropa

Aplicación de escritorio en Java para administrar clientes, productos e inventario de una tienda de ropa, con persistencia de datos en archivos JSON.

---

## Tabla de Contenidos

- [Descripción](#descripción)
- [Tecnologías](#tecnologías)
- [Arquitectura](#arquitectura)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Modelos de Datos](#modelos-de-datos)
- [Capas de la Aplicación](#capas-de-la-aplicación)
- [Funcionalidades](#funcionalidades)
- [Persistencia de Datos](#persistencia-de-datos)
- [Instalación y Ejecución](#instalación-y-ejecución)
- [Validaciones del Sistema](#validaciones-del-sistema)
- [Patrones de Diseño](#patrones-de-diseño)
- [Limitaciones y Mejoras Futuras](#limitaciones-y-mejoras-futuras)
- [Contribuidores](#contribuidores)

---

## Descripción

Sistema de gestión para tienda de ropa desarrollado como proyecto universitario. Permite registrar clientes, gestionar el catálogo de productos con control de inventario, y procesar ventas con carrito de compras. Los datos se persisten localmente en archivos JSON, lo que permite retenerlos entre sesiones sin necesidad de una base de datos externa.

---

## Tecnologías

| Tecnología | Versión | Uso |
|---|---|---|
| Java | 17 | Lenguaje principal |
| Swing | (JDK integrado) | Interfaz gráfica de usuario |
| Maven | 3.6+ | Gestión de dependencias y build |
| Gson | 2.10.1 | Serialización/deserialización JSON |
| NetBeans | Cualquiera | IDE recomendado (archivos `.form`) |

---

## Arquitectura

El proyecto implementa **MVC (Modelo-Vista-Controlador)** combinado con el patrón **Repository**:

```
┌─────────────────────────────────────────────────────┐
│                     VISTA (View)                    │
│        ClientPanel │ ProductPanel │ SalePanel       │
└──────────────────────┬──────────────────────────────┘
                       │ usa
┌──────────────────────▼──────────────────────────────┐
│                  SERVICIO (Service)                 │
│     ClientService │ ProductService │ SaleService    │
└──────────────────────┬──────────────────────────────┘
                       │ usa
┌──────────────────────▼──────────────────────────────┐
│               REPOSITORIO (Repository)              │
│  ClientRepository │ ProductRepository │ SaleRepository │
└──────────────────────┬──────────────────────────────┘
                       │ lee/escribe
┌──────────────────────▼──────────────────────────────┐
│              PERSISTENCIA (JSON Files)              │
│     clients.json │ products.json │ sales.json       │
└─────────────────────────────────────────────────────┘
```

---

## Estructura del Proyecto

```
TIENDA_DE_ROPA/
├── pom.xml                              # Configuración Maven
├── README.md                            # Este archivo
├── data/                                # Archivos de persistencia JSON
│   ├── clients.json
│   ├── products.json
│   └── sales.json
└── src/
    └── main/
        └── java/
            └── com/grupo4/manageclient/
                ├── ManageClient.java    # Punto de entrada (main)
                ├── MainFrame.java       # Ventana principal con pestañas
                ├── model/
                │   ├── Client.java
                │   ├── Product.java
                │   ├── Sale.java
                │   └── SaleDetail.java
                ├── repository/
                │   ├── interfaces/
                │   │   ├── IClientRepository.java
                │   │   ├── IProductRepository.java
                │   │   └── ISaleRepository.java
                │   ├── ClientRepository.java
                │   ├── ProductRepository.java
                │   └── SaleRepository.java
                ├── service/
                │   ├── IClientService.java
                │   ├── IProductService.java
                │   ├── ISaleService.java
                │   ├── ClientService.java
                │   ├── ProductService.java
                │   └── SaleService.java
                └── view/
                    ├── ClientPanel.java / .form
                    ├── ProductPanel.java / .form
                    └── SalePanel.java / .form
```

---

## Modelos de Datos

### Client
| Atributo | Tipo | Descripción |
|---|---|---|
| `idClient` | int | Identificador único |
| `nameClient` | String | Nombre completo |
| `email` | String | Correo electrónico |
| `phoneNumber` | String | Número de teléfono |

### Product
| Atributo | Tipo | Descripción |
|---|---|---|
| `idProduct` | int | Identificador único |
| `nameProduct` | String | Nombre del producto |
| `size` | String | Talla (XS, S, M, L, XL…) |
| `color` | String | Color del producto |
| `unitPrice` | double | Precio por unidad |
| `stock` | int | Unidades disponibles en inventario |

### Sale
| Atributo | Tipo | Descripción |
|---|---|---|
| `idSale` | int | Identificador único |
| `client` | Client | Cliente que realizó la compra |
| `details` | List\<SaleDetail\> | Lista de productos comprados |
| `total` | double | Total calculado automáticamente |

### SaleDetail
| Atributo | Tipo | Descripción |
|---|---|---|
| `product` | Product | Producto vendido |
| `quantity` | int | Cantidad vendida |
| `unitPrice` | double | Precio unitario al momento de la venta |
| `subtotal` | double | Calculado: `quantity × unitPrice` |

---

## Capas de la Aplicación

### Modelos (`model/`)
Clases POJO que representan las entidades del negocio. `Sale` calcula su `total` sumando los `subtotal` de todos sus `SaleDetail`. `SaleDetail` calcula automáticamente su `subtotal` al asignar `product` o `quantity`.

### Repositorios (`repository/`)
Responsables de la persistencia. Cada repositorio:
- Carga datos desde el archivo JSON correspondiente al instanciarse.
- Mantiene los datos en un `ArrayList` en memoria durante la ejecución.
- Guarda en JSON después de cada operación de escritura (save, update, delete).

| Repositorio | Archivo | Operaciones |
|---|---|---|
| `ClientRepository` | `data/clients.json` | save, getAll, findById, update, delete |
| `ProductRepository` | `data/products.json` | save, getAll, findById, update, delete |
| `SaleRepository` | `data/sales.json` | save, getAll, findById, existsProductInSales |

### Servicios (`service/`)
Contienen la lógica de negocio y todas las validaciones antes de delegar al repositorio.

**ClientService:**
- Valida que el ID sea positivo y no esté duplicado.
- Valida el formato del email con expresión regular.
- Valida que el teléfono sea numérico y tenga entre 7 y 10 dígitos.

**ProductService:**
- Valida que el precio sea positivo y el stock no sea negativo.
- Impide eliminar un producto si está asociado a alguna venta registrada.

**SaleService:**
- Verifica que el cliente exista antes de procesar la venta.
- Valida stock disponible por cada producto antes de confirmar.
- Descuenta automáticamente el stock de cada producto al registrar la venta.
- Calcula totales y subtotales de forma automática.

### Vistas (`view/`)
Paneles Swing generados con el GUI Builder de NetBeans (archivos `.form`).

**ClientPanel** — Gestión de clientes:
- Tabla de clientes con selección para editar.
- Formulario para crear/editar (ID, nombre, email, teléfono).
- Botones: Nuevo, Guardar, Actualizar, Eliminar, Limpiar.

**ProductPanel** — Gestión de productos:
- Tabla de productos con selección para editar.
- Formulario para crear/editar (ID, nombre, talla, color, precio, stock).
- Botones: Nuevo, Guardar, Actualizar, Eliminar, Limpiar.

**SalePanel** — Gestión de ventas:
- ComboBox para seleccionar cliente.
- ComboBox para seleccionar producto y campo de cantidad.
- **Carrito de compras**: permite agregar y quitar productos antes de confirmar.
- Tabla de historial de ventas registradas.
- Cálculo automático del total de la venta.

### MainFrame
Ventana principal (950×650 px) con un `JTabbedPane` que aloja los tres paneles. Al cambiar de pestaña, recarga los datos de la pestaña activa para mantener la información sincronizada.

---

## Funcionalidades

### Clientes
- [x] Registrar nuevo cliente con validaciones
- [x] Listar todos los clientes en tabla
- [x] Seleccionar cliente de la tabla para editar
- [x] Actualizar datos del cliente
- [x] Eliminar cliente

### Productos
- [x] Registrar nuevo producto con control de inventario
- [x] Listar todos los productos en tabla
- [x] Seleccionar producto de la tabla para editar
- [x] Actualizar datos del producto
- [x] Eliminar producto (solo si no tiene ventas asociadas)

### Ventas
- [x] Seleccionar cliente para la venta
- [x] Agregar productos al carrito con cantidad y validación de stock
- [x] Quitar productos del carrito
- [x] Calcular subtotales y total automáticamente
- [x] Confirmar venta y descontar stock del inventario
- [x] Ver historial de ventas registradas

---

## Persistencia de Datos

Los datos se almacenan en tres archivos JSON dentro del directorio `data/` relativo al directorio de ejecución del proyecto:

```
data/
├── clients.json    # Lista de clientes registrados
├── products.json   # Catálogo de productos e inventario
└── sales.json      # Historial de ventas con sus detalles
```

Los archivos se crean automáticamente si no existen. Los datos **se conservan entre sesiones** — al cerrar y volver a abrir la aplicación, toda la información persiste.

**Ejemplo de estructura `clients.json`:**
```json
[
  {
    "idClient": 1,
    "nameClient": "Juan Pérez",
    "email": "juan@example.com",
    "phoneNumber": "3001234567"
  }
]
```

---

## Instalación y Ejecución

### Prerrequisitos
- Java 17 o superior (`java -version`)
- Maven 3.6+ (`mvn -version`)

### Clonar el repositorio
```bash
git clone <url-del-repositorio>
cd Proyecto_Tienda_De_Ropa/TIENDA_DE_ROPA
```

### Compilar
```bash
mvn clean compile
```

### Ejecutar
```bash
mvn exec:java
```

O con Java directamente después de compilar:
```bash
java -cp target/classes com.grupo4.manageclient.ManageClient
```

### Abrir en NetBeans
1. Abrir NetBeans IDE.
2. **File → Open Project** y seleccionar la carpeta `TIENDA_DE_ROPA`.
3. Hacer clic derecho en el proyecto → **Run**.

---

## Validaciones del Sistema

| Campo | Regla |
|---|---|
| ID (cliente/producto) | Debe ser entero positivo y no estar duplicado |
| Nombre | No puede estar vacío |
| Email | Formato válido: `usuario@dominio.ext` |
| Teléfono | Solo dígitos, entre 7 y 10 caracteres |
| Talla / Color | No pueden estar vacíos |
| Precio | Debe ser mayor a 0 |
| Stock | No puede ser negativo |
| Cantidad en venta | Debe ser mayor a 0 y no superar el stock disponible |
| Eliminar producto | Solo si no está asociado a ninguna venta |

---

## Patrones de Diseño

| Patrón | Aplicación en el proyecto |
|---|---|
| **MVC** | Separación en paquetes `model`, `service`/`repository`, `view` |
| **Repository** | Interfaces `IClientRepository`, `IProductRepository`, `ISaleRepository` abstraen la persistencia |
| **Dependency Injection** | Los servicios reciben repositorios por constructor; las vistas reciben servicios por constructor |
| **Strategy** (implícito) | Las interfaces de servicio permiten intercambiar implementaciones |
| **Observer** (Swing) | `ActionListener`, `ListSelectionListener`, `ChangeListener` en los componentes UI |

---

## Limitaciones y Mejoras Futuras

### Limitaciones actuales
- Persistencia en archivos planos JSON (sin transacciones ni concurrencia).
- No soporta múltiples usuarios simultáneos.
- No hay autenticación ni roles de usuario.
- Las ventas no se pueden editar ni eliminar una vez registradas.
- No hay reportes ni estadísticas de ventas.

### Mejoras sugeridas
- [ ] Migrar la persistencia a una base de datos relacional (MySQL, PostgreSQL, SQLite).
- [ ] Implementar autenticación de usuarios con roles (admin, vendedor).
- [ ] Agregar módulo de reportes: ventas por fecha, productos más vendidos, ingresos.
- [ ] Implementar edición y cancelación de ventas con reversión de stock.
- [ ] Agregar exportación de datos a CSV o PDF.
- [ ] Mejorar la UI con Look & Feel personalizado o migrar a JavaFX.
- [ ] Escribir pruebas unitarias para servicios y repositorios.

---

## Contribuidores

| Nombre | Rol |
|---|---|
| Angel | Modelos y servicios de clientes |
| Samuel | Modelos y servicios de ventas |
| Grupo 4 | Desarrollo general del proyecto |

---

## Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo `LICENSE` para más detalles.
