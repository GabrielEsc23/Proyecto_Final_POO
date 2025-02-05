# Smart Shop

## Descripción
Smart Shop es una aplicación de compras en línea que permite a los usuarios agregar productos al carrito, visualizar el subtotal y realizar pagos con tarjeta de crédito o débito. Además, cuenta con un sistema de autenticación que distingue entre clientes y administradores.

- **Clientes** pueden:
  - Agregar productos al carrito.
  - Ver el subtotal de su compra.
  - Realizar pagos con tarjeta de crédito o débito.
  - Ver el total de la compra.

- **Administradores** pueden:
  - Acceder a un menú CRUD para gestionar los productos del catálogo.
  - Crear, leer, modificar y eliminar productos.
  - Agregar imágenes a los productos proporcionando la ruta de la imagen.

## Tecnologías utilizadas
- **Java (Swing)** para la interfaz gráfica.
- **MySQL** para la base de datos.
- **JDBC** para la conexión con la base de datos.
- **iText (lowagie)** para la generación de facturas en PDF.

## Instalación y ejecución
1. **Clonar el repositorio**
   ```sh
   git clone https://github.com/tu-repositorio/smart_shop.git
   ```
2. **Configurar la base de datos**
   - Crear una base de datos en MySQL llamada `smart_shop`.
   - Importar el archivo `smart_shop.sql` que contiene la estructura de la base de datos.
3. **Configurar la conexión a la base de datos**
   - Modificar los parámetros en la clase `crud.java`:
     ```java
     private final String url = "jdbc:mysql://localhost:3306/smart_shop";
     private final String user = "root";
     private final String password = "";
     ```
4. **Ejecutar la aplicación**
   - Abrir el proyecto en un IDE como IntelliJ IDEA o Eclipse.
   - Compilar y ejecutar `main.java`.

## Uso
### Cliente
1. Iniciar sesión como cliente.
2. Explorar el catálogo y agregar productos al carrito.
3. Revisar el subtotal y proceder con el pago.
4. Ingresar los datos de la tarjeta y completar la compra.
5. Descargar la factura en PDF.

### Administrador
1. Iniciar sesión como administrador.
2. Acceder al menú CRUD.
3. Gestionar productos:
   - Crear un nuevo producto ingresando nombre, precio y ruta de imagen.
   - Leer información de un producto existente.
   - Modificar detalles de un producto.
   - Eliminar productos del catálogo.

## Funcionalidades principales
 **Carrito de compras** con actualización en tiempo real.
 **Pago con tarjeta** con validación de datos.
 **Gestión de productos** para administradores.
 **Generación de facturas en PDF** tras la compra.
 **Base de datos MySQL** para almacenamiento seguro.
 
 ## Vista de la Aplicación
 (`---`)
 ![Login](https://github.com/GabrielEsc23/Proyecto_Final_POO/raw/master/Imagen_Interfaz/Login.png)
 (`---`)
![Catálogo](https://github.com/GabrielEsc23/Proyecto_Final_POO/raw/master/Imagen_Interfaz/Catalogo.png)
(`---`)
![Carrito](https://github.com/GabrielEsc23/Proyecto_Final_POO/raw/master/Imagen_Interfaz/Carrito.png)
(`---`)
![Pagos](https://github.com/GabrielEsc23/Proyecto_Final_POO/raw/master/Imagen_Interfaz/Pagos.png)
(`---`)
![CRUD](https://github.com/GabrielEsc23/Proyecto_Final_POO/raw/master/Imagen_Interfaz/CRUD.png)


## Autor
- **[Edison Gabriel Escobar Obando]** 


