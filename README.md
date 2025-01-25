Para correr este proyecto se necesita el JDK 17, Docker y correr la imagen de mysql de docker o tener mysql local.

En el directorio base del proyecto ejecutar
docker-compose -f mysql.yml up -d
se mostrara lo siguiente
![image](https://github.com/user-attachments/assets/05b2b13e-9209-4d69-9db8-9005cd6c20b3)


Una ves corriendo la imagen se debe cambiar la configuración dentro del application.properties


![image](https://github.com/user-attachments/assets/f7b4d78e-1dac-44e6-8ad3-21fc1972008a)

Según sea el usuario configurado, en este caso esta el de la imagen de docker


En algun editor de base de datos conectarse a la base de datos 

![image](https://github.com/user-attachments/assets/9f619bc2-155b-4e83-8535-dff73093359a)

y ejecutar el siguiente script

schema.sql

![image](https://github.com/user-attachments/assets/b134e9af-80d8-4cba-8832-0d26dd78d170)

Una ves todo configurado ya podemos ejecutar

![image](https://github.com/user-attachments/assets/55fec79d-e36b-4d12-8562-8386b476db86)


Para ejecutar los test se debe tener un IDE con esta funcionabilidad y poder ver el coverage
![image](https://github.com/user-attachments/assets/041d80a2-fa4b-4124-844e-7f5060f2694a)

![image](https://github.com/user-attachments/assets/a6edc45b-c9bc-4a9e-a86b-74574c5d2022)


Al proyecto se adjunta el postman collection y swagger.

![image](https://github.com/user-attachments/assets/a6ef215e-8f3e-4898-93b8-3b457b65a649)




Secuencias:

![image](https://github.com/user-attachments/assets/19157569-3520-4517-8bcd-7a1b78739ba0)
![image](https://github.com/user-attachments/assets/3c84165c-94b7-40a3-9c31-a557a4db7664)
![image](https://github.com/user-attachments/assets/6159d4a9-3c7b-42c2-91b3-c07d6fd4c472)
![image](https://github.com/user-attachments/assets/c4598bcd-4998-4db5-bd26-e30e86d81853)



