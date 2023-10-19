# skins-project

**Instrucciones para ejecutar el proyecto de forma local:**
1. Crear una base de datos MYSQL con el nombre "skins"
2. El puerto configurado es el 8001
3. Usando postman se pueden probar las rutas de la API:
  • GET /skins/avaible - Devuelve una lista de todas las skins disponibles para comprar.
  • POST /skins/buy - Permite a los usuarios adquirir una skin y guardarla en la base de datos.
        Hay que mandar por lo menos el nombre de la skin en formato JSON
  • GET /skins/myskins - Devuelve una lista de las skins compradas por el usuario.
  • PUT /skins/color - Permite a los usuarios cambiar el color de una skin comprada.
        Hay que mandar por lo menos el nombre y el nuevo color de la skin en formato JSON
  • DELETE /skins/delete/{id} - Permite a los usuarios eliminar una skin comprada.
  • GET /skin/getskin/{id} – Devuelve una determinada skin.


**Detalles de proyecto**
1. Proyecto creado con spring framework
2. El archivo con las skins disponibles en formato JSON esta en la carpeta "data" y se usa para verificar los datos de las skins disponibles
3. La base de datos representa un solo usuario que puede realizar las consultas a la API mencionadas arriba
