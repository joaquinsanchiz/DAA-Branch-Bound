# Maximum diversity problem

> by_ Joaquin Sanchiz Navarro, alu0100893755_

---

En esta memoria se describirán brevemente los elementos necesarios para comprender los diseños propuestos. Entre otros, se describen la estructura de datos que almacena las soluciones del problema, la estructura de entornos usada en la búsqueda local, como se construye la lista restringida de candidatos del algoritmo GRASP y cómo se obtienen las cotas del algoritmo de ramificación y poda.

### Estructura de datos

La estructura de datos usada en esta práctica ha sido la clase _MaximumDiversityProblem_. Dicho objeto contiene:

* Un array de arrays de _double_, donde se encuentran, para el problema inicial, todos los vectores del problema, y para la solución, los vectores finales resultantes.
* Un array de _double_ denominado centroide que contiene El Centro de gravedad del problema actual.
* Un double Z que contiene el valor de la z del problema actual.

Esta estructura de datos ha sido diseñada de tal manera que se instancia a partir de un fichero de entrada contenedor del problema inicial, y se utiliza por los algoritmos para devolver el problema resuelto. Este objeto contiene los métodos de _euclideanDistance _\(calcula la distancia euclidiana entre dos vectores\)_, centroid\(calcula el centroid del problema actual\), Z _\(calcula el parámetro a maximizar\) y el método _insert _\(que inserta un vector en el problema y re-calcula el centroid y su Z\).

### Objeto Algorithm

Todos los algoritmos implementados en esta práctica son métodos estáticos que pertenecen al objeto Algorithm, entre ellos podemos encontrar los métodos greedy_, busquedaLocal, GREEDY y branchAndBound_.

Para la** búsqueda local** hemos desarrollado dos métodos. El método que hemos utilizado para calcular los resultados de la tabla ha sido uno que recibía como parámetros la _m_ del problema y el problema inicial. Dicho método construye primero una solución inicial a partir del algoritmo greedy y a continuación comienza a realizar la búsqueda local. Va comparando cada array dentro de la solución inicial con los arrays que se han quedado fuera de la solución, y se intercambian. Si esta nueva solución mejora Z, entonces pasa a ser la solución actual. Este proceso se repite hasta que se han comparado todos los nodos. A parte, tenemos otro método para la búsqueda local que recibe como parámetros una solución inicial y el problema inicial, de tal manera que se realiza una búsqueda local para una solución inicial cualquiera.

En el algoritmo _**GRASP**_ se calcula inicialmente El Centro de gravedad del problema, que pasa a ser El Centro de gravedad de la solución. Posteriormente calculamos el elemento más lejano a nuestro centro de gravedad, y lo insertamos en nuestra solución. A partir de aquí, comenzamos a explorar cada nodo, ordenando los nodos posibles en un TreeMap, estructura de datos similar a la HashTable pero que ordena los elementos de mayor a menor según su key \(distancia euclidiana al centro de gravedad, en nuestro caso\). Después de generar esta lista, la iteramos entre 0 y 1 o 2 veces, aleatoriamente dependiendo del tamaño de la LRC, de tal manera que cogeremos uno de los \|LRC\| elementos de la lista. Dicho elemento se inserta y se re-calcula el nuevo centro de gravedad. Todo esto se repite hasta que la solución sea de tamaño _m_, parámetro del problema. Una vez conseguida nuestra solución inicial, se realiza una búsqueda local y se devuelve el resultado final.

Para el _**branch&bound**_ hemos realizado una** exploración en anchura**, de tal manera que se realiza lo siguiente:

* Se calcula el centroide del problema inicial, tal y como se hace en los demás algoritmos
* Se busca el elemento más lejano al centro de gravedad.
* Se generan n-1 soluciones parciales, en las que se inserta cada vector del problema con el elemento más lejano.
* Si alguno de estos conjuntos no supera la cota dada por el algoritmo seleccionado para m=2, no se inserta en el vector de conjuntos.
* A continuación:
* Mientras el elemento 0 del vector de conjuntos tenga un número de vectores menor a _m_ hacer:
* desde i=0 hasta tamañoActualVector -1
* generar cota para nivel actual
* desde j=0 hasta problema.vectores.size-1 hacer
* temporal = conjunto\(i\)
* si el vector j del problema no está en el conjunto, se inserta
* si el conjunto pasa la cota, se inserta en el vector de conjuntos
* end
* end
* Eliminar los "tamañoActualVector-1" primeros elementos del vector de conjuntos
* end

De esta manera, para cada uno de los niveles del árbol, vamos generando cotas y eliminando ramas que no cumplen dichas cotas.

## Tablas

---

Para visualizar las tablas: https://www.gitbook.com/book/joaquinsanchiz/informe-daa

## Documentación
---
Para ver la documentación del codigo, visitar la gh-pages del repositorio: https://joaquinsanchiz.github.io/DAA-Branch-Bound/

