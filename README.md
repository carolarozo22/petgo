# petgo


# Boilerplate

Generación de la linea base del proyecto.

## Requerimientos

 - DynamoDB de manera local [DynamoDB AWS](https://docs.aws.amazon.com/es_es/amazondynamodb/latest/developerguide/DynamoDBLocal.DownloadingAndRunning.html), se puede usar archivo Jar o Contenedor.
 - Command Line Interface o [CLI AWS](https://docs.aws.amazon.com/es_es/amazondynamodb/latest/developerguide/DynamoDBLocal.DownloadingAndRunning.html), para configurar las credenciales de acceso local a los servicios de Amazon que podemos vincular de manera local o directamente con cloud.

## Installation

1. Una vez instalado el CLI de AWS realizar la configuración de acceso, los datos deberan de ser los mismos del archivo properties del proyecto ID, SECRET, REGION:
    ```sh
    aws configure
    ```
    - ID KEY: peigo
    - SECRET kEY: Globant2021
    - REGION: us-east-1
    - OUTPUT: json

2. Ejecutar el JAR o Imagen de docker con Dynamo.
    2.1 En caso de [JAR](https://s3.sa-east-1.amazonaws.com/dynamodb-local-sao-paulo/dynamodb_local_latest.zip), ubicarse en la carpeta raiz del proyecto desde la terminal y ejecutar el comando:
    ```sh
    java -Djava.library.path=./DynamoDBLocal_lib -jar DynamoDBLocal.jar -sharedDb
    ```
    2.2 En caso de imagen de docker, se debe de bajar la imagen y crear un contenedor:
    ```sh
    docker pull amazon/dynamodb-local               // Descarga la imagen
    docker run -p 8000:8000 amazon/dynamodb-local   // Crea el contenedor
    ```
3. Crear la tabla desde linea de comandos base del proyecto
    ```sh
    aws dynamodb create-table \
    --table-name users \
    --attribute-definitions \
        AttributeName=id,AttributeType=S \
    --key-schema \
        AttributeName=id,KeyType=HASH \
    --provisioned-throughput \
        ReadCapacityUnits=10,WriteCapacityUnits=5 --endpoint-url http://localhost:8000
    ```
## Algunos comandos Dynamo DB
Eliminar tabla:
```sh
aws dynamodb delete-table --table-name users --endpoint-url http://localhost:8000
```

Consultar registros de toda la tabla:
```sh
aws dynamodb scan --table-name users --endpoint-url http://localhost:8000
```

