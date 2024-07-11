VERSION DE JDK: Es necesario tener instalada la version openJDK 17 u otras en adealante.

INSTRUCCIONES PARA CORRER EL PROGRAMA:
-Se deben clonar/copiar los archivos del repositorio.
-Ademas debe estar instalado maven, de no tenerlo se puede ejecutar el comando
`sudo apt install mvn`. para instalarlo.
Finalmente, en la terminal, dirigirse al root del proyecto y ejecutar el comando: `mvn javafx:run`

CONDICIONES DE LA INTERFAZ VISUAL EN CONFIG

- **ColoresJugadores:** el diseño está hecho para que cada tipo de jugador sea el nombre de una foto en la carpeta `/resources/images/` de manera que se pueden agregar distintas fotos para los personajes. 
Si se agrega un personaje se debe agregar el color en ColoresJugadores y que sea el nombre de la imagen. ej: el ColorJugador SOMBRERO representa `SOMBRERO.png`.

+ **ColoresComprables:** los ColoresComprables representan el color del barrio de los comprables. Para que se puedan agregar colores nuevos y sean representados visualmente en el tablero,
deben ser nombres de colores reales en ingles y en minuscula. ej: el ColorComprable lightblue representa el barrio de celestes y se ve de ese color en la visual

+ **ListaCasilla:** la lista casilla puede modificarse para que haya cantidades de casillas que sean multiplos de 4 o no, y las casillas que van en las esquinas como PASO, SALIDA, CARCEL e IR_A_CARCEL pueden 
estar en los lados tambien. Lo mismo para, las casillas que normalmente estan en los lados, pueden estar en las esquinas y se van a ver.
