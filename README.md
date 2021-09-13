# Nisum

Este es un servicio para crear usuarios y mostrar un usuario.

1) Para ejecutar:

```
gradle build
```

En primer lugar, debe iniciar sesión en la aplicación utilizando el primer usuario 'root' ya creado: 

```
curl --request POST \
  --url http://localhost:8080/login \
  --header 'content-type: application/json' \
  --cookie JSESSIONID=D490FC4E201FDADD79C6598D9756A109 \
  --data '{
    "email": "root@root.com",
    "password": "password"
}'
```


* Para obtener un usuario:

```
GET http://localhost:8080/users/{id}
```

* Para crear usuario:

```
curl --request POST \
  --url http://localhost:8080/users \
  --header 'Content-Type: application/json' \
  --cookie JSESSIONID=D490FC4E201FDADD79C6598D9756A109 \
  --data '{
	"name": "Juan Rodriguez",
	"email": "juan@rodriguez.org",
	"password": "hunter2",
	"phones": [
		{
			"number": "1234567",
			"citycode": "1",
			"contrycode": "57"
		}
	]
}'
```
