# SGSSI-20.Lab04.1.1
## Enunciado
Modificar el programa para que el resumen MD5 resultante comience por la secuencia de 0x0s más larga que se pueda obtener en un minuto de ejecución del programa o, como mínimo por 0x00.
Probar su funcionamiento con el fichero de texto SGSSI-20.CB.04.txt. Si se consigue completar con éxito, dejar accesible el fichero resultante por medio de una url y tomar nota, tanto de la cadena de ocho caracteres hexadecimales que se ha añadido al final, como del resumen MD5 resultante, para introducirlos en el formulario S.5.3.C.

## Instalación
Para utilizar este programa se necesita un programa para Java, ya sea Eclipse, IntelliJ... En resumidas cuentas, un programa que pueda ejecutar un IDE Java. Tambíen se puede utilizar el Powershell de Windows o el bash de Linux.

## Ejecución
Una vez descargado se abre el proyecto mediante dicho programa. Antes de ejecutarlo, hay que introducir el archivo en la carpeta del proyecto.

Se abre la única clase que hay "Principal" y se ven dos comentarios al lado de unas líneas de código, ahí se modifica el nombre del fichero original (del que se quiere conseguir el digest MD5) y el fichero donde se va a guardar.

Una vez hecho esto, se ejecuta el programa y tras un minuto de ejecución, nos saldra por consola el digest más largo conseguido junto al valor hexadecimal sumado para conseguir dicho digest. También se genera un fichero con el nombre que le hayamos proporcionado con el contenido del fichero original + el valor hexadecimal.

## Uso de SHA-256
Para generar el digest mediante SHA-256 solo hay que modificar la primera línea del método "digestEmpezandoPor0s", donde pone MD5 cambiarlo y escribir SHA-256.
