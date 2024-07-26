# LK-Biblio
LK-Biblio es un proyecto creado para la materia Programación Avanzada de la Facultad Da Vinci. 
El objetivo principal de este proyecto es simular el funcionamiento de un sistema de una Biblioteca, donde se puede crear
libros, categorias, prestamos y usuarios, asi mismo se podrán editar y eliminar en caso de ser necesario.

### Funcionalidades Principales
- Login de usuarios.
- Consulta de Libros.
- Creación, Edición y Eliminación de: 
    - Usuarios.
    - Prestamos.
    - Libros.
    - Categorias.

### Tecnologías Utilizadas
- Java para la lógica.
- SQL para la creación de Consultas.
- JFrame para las interfaces gráficas.

### Estructura del Proyecto
El proyecto está estructurado en las siguientes partes:
- `Controllers/`: Contiene todos los metodos y funciones que se conectan a la BBDD para realizar consultas.
  - `Tables/`: Tiene el codigo que genera las tablas, para mantener más "Limpio el view" [Se mejorará para unirtodas en 1 clase]
- `DB/`: Contiene la conexion a la BBDD.
- `Exceptions/`: Contiene las clases de excepciones personalizadas (actualmente cuenta con 1)
- `Interfaces/`: Contiene el archivo IActualizar, que solo tiene la funcion que se utiliza en algunas vitas para "actualizar" la info
- `Models/`: Archivos que simulan los modelos de la BBDD
- `SQL/`: Archivo que contiene el .sql para crear la BBDD
- `Views/`: Todas las vistas gráficas con sus codigos y .form

### Autores
- Lucas Karbo



