## Escuela Colombiana de Ingeniería

## Arquitecturas de Software

# Componentes y conectores - Parte I.

El ejercicio se debe traer terminado para el siguiente laboratorio (Parte II).

#### Middleware- gestión de planos.


## Antes de hacer este ejercicio, realice [el ejercicio introductorio al manejo de Spring y la configuración basada en anotaciones](https://github.com/ARSW-ECI/Spring_LightweightCont_Annotation-DI_Example).

En este ejercicio se va a construír un modelo de clases para la capa lógica de una aplicación que permita gestionar planos arquitectónicos de una prestigiosa compañia de diseño. 

![](img/ClassDiagram1.png)

1. Configure la aplicación para que funcione bajo un esquema de inyección de dependencias, tal como se muestra en el diagrama anterior.


	Lo anterior requiere:

	* Agregar las dependencias de Spring.
	* Agregar la configuración de Spring.
	* Configurar la aplicación -mediante anotaciones- para que el esquema de persistencia sea inyectado al momento de ser creado el bean 'BlueprintServices'.


2. Complete los operaciones getBluePrint() y getBlueprintsByAuthor(). Implemente todo lo requerido de las capas inferiores (por ahora, el esquema de persistencia disponible 'InMemoryBlueprintPersistence') agregando las pruebas correspondientes en 'InMemoryPersistenceTest'.

3. Haga un programa en el que cree (mediante Spring) una instancia de BlueprintServices, y rectifique la funcionalidad del mismo: registrar planos, consultar planos, registrar planos específicos, etc.

4. Se quiere que las operaciones de consulta de planos realicen un proceso de filtrado, antes de retornar los planos consultados. Dichos filtros lo que buscan es reducir el tamaño de los planos, removiendo datos redundantes o simplemente submuestrando, antes de retornarlos. Ajuste la aplicación (agregando las abstracciones e implementaciones que considere) para que a la clase BlueprintServices se le inyecte uno de dos posibles 'filtros' (o eventuales futuros filtros). No se contempla el uso de más de uno a la vez:
	* (A) Filtrado de redundancias: suprime del plano los puntos consecutivos que sean repetidos.
	* (B) Filtrado de submuestreo: suprime 1 de cada 2 puntos del plano, de manera intercalada.

5. Agrege las pruebas correspondientes a cada uno de estos filtros, y pruebe su funcionamiento en el programa de prueba, comprobando que sólo cambiando la posición de las anotaciones -sin cambiar nada más-, el programa retorne los planos filtrados de la manera (A) o de la manera (B). 



### Desarrollo Laboratorio

Aqui esta el desarrollo del ejercicio (taller introducción al manejo de spring y la configuración basada en anotaciones)

```
https://github.com/andres3455/ARSW_LAB4_Ejercicio.git
```

1) Se agregaron las dependencias necesarias en el POM, para trabajar con inyección de dependencias y anotaciones de Spring

![](img/1.png)


2) Se completaron los metodos getBlueprint() y getBlueprintsByAuthor()

En el metodo getBlueprint busca un blueprint especifico en la tupla

En el metodo getBlueprintsByAuthor, devuelve el conjunto de blueprints que concuerdan con el autor

![](img/2.png)


y aqui estan las pruebas 


![](img/3.png)


3) Se desarrolla el programa en la clase App.java , junto con sus importaciones necesarias para su correcto funcionamiento

![](img/4.png)

![](img/5.png)

4) Primero, se crearon los filtros por redundancia y por submuestreo, se crearon las interfaces y las implementaciones de los filtros
![](img/6.png)
![](img/7.png)
![](img/8.png)
5) Se crearon 3 pruebas unitarias para probar los filtros y su correcto funcionamiento
![](img/9.png)
Algo interesante que se puede notar es que al ejecutar por primera vez las pruebas, estas fallan así:
ShouldApplyRedundancyFilter:
![](img/10.png)
ShouldFilterBlueprintsByAuthor:
![](img/11.png)
Los prints que se ven en las pruebas indican que al hacer las comparaciones de los puntos estas se hacen apuntando hacia un espacio de memoria.
Esto se soluciona sobreescribiendo el metodo equals en la clase Point, para que compare los puntos por sus coordenadas y no por su espacio de memoria
![](img/12.png)
Al ejecutar las pruebas después de hacer estos cambios, vemos que estas pasan correctamente.
![](img/13.png)