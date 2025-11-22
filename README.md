# User & Role Management Service

A Spring application that provides CRUD operations for **Users**, **Roles**, and **User–Role Mappings**.  
It supports assigning roles to users, revoking roles, and querying role/user relationships.

---

## Features

- Create, update, delete, and fetch **Users**
- Create, update, delete, and fetch **Roles**
- Assign a role to a user
- Revoke a role from a user
- Get all roles assigned to a user
- Get all users associated with a role
- Clean layered architecture (Controller → Service → Repository)
- Uses `ResponseEntity` for well-structured API responses

---

# API Documentation

Below are the controllers and the endpoints exposed by the system.

---

# User Controller (`/api/v1/users`)

### **Create User**

**cURL:**
> _curl --location 'http://localhost:8080/user-role/api/v1/users' \
--header 'Content-Type: application/json' \
--data-raw '{
"name": "John Doe",
"email": "john@email.com",
"address": "NW",
"phone": "016545678765345"
}'_

---

### **Get User by ID**

**cURL:**
with whatever your user-id is
> _curl --location 'http://localhost:8080/user-role/api/v1/users/e6b69613-303a-46c0-a3a1-8970c529cbf6'_

---

### **Get All Users**

**cURL:**
> _curl --location 'http://localhost:8080/user-role/api/v1/users'_

---

### **Update User**

**cURL:**
with whatever your user-id is
> _curl -X 'PUT' \
'http://localhost:8080/user-role/api/v1/users/e6b69613-303a-46c0-a3a1-8970c529cbf6' \
-H 'Content-Type: application/json' \
-d '{
"name": "John D",
"address": "NYC",
"phone": "016545678765345"
}'_

---

### **Delete User**

**cURL:**
what whatever your user-id is
> _curl -X 'DELETE' \
'http://localhost:8080/user-role/api/v1/users/e6b69613-303a-46c0-a3a1-8970c529cbf6'_

---

# Role Controller (`/api/v1/roles`)

### **Create Role**

**cURL:**
> _curl -X 'POST' \
'http://localhost:8080/user-role/api/v1/roles' \
-H 'Content-Type: application/json' \
-d '{
"name": "Administrator",
"status": "ACTIVE"
}'_

---

### **Get Role by ID**

**cURL:**
with your role id
> _curl -X 'GET' \
'http://localhost:8080/user-role/api/v1/roles/95a1c610-2fe9-4cea-9ba1-2ec3f8611798'_

---

### **Get All Roles**

**cURL:**
> _curl -X 'GET' \
'http://localhost:8080/user-role/api/v1/roles'_

---

### **Update Role**


**cURL:**
with your role id
> _curl -X 'PUT' \
'http://localhost:8080/user-role/api/v1/roles/95a1c610-2fe9-4cea-9ba1-2ec3f8611798' \
-H 'Content-Type: application/json' \
-d '{
"name": "Administrator",
"status": "INACTIVE"
}'_

---

### **Delete Role**

**cURL:**
> _curl -X 'DELETE' \
'http://localhost:8080/user-role/api/v1/roles/95a1c610-2fe9-4cea-9ba1-2ec3f8611798'_

---

# User–Role Mapping Controller (`/api/v1/mapping`)

### **Assign Role to User**

**cURL:**
with your user-id and role-id
> _curl -X 'POST' \
'http://localhost:8080/user-role/api/v1/role-mapping/assign?roleId=381c0355-3318-4553-b960-1ad6c152762f&userId=ee3a9d5a-6394-42b3-8d6b-517ed759612e'_

---

### **Revoke Role From User**

**cURL:**
with your user-id and role-id
> _curl -X 'DELETE' \
'http://localhost:8080/user-role/api/v1/role-mapping/revoke?roleId=381c0355-3318-4553-b960-1ad6c152762f&userId=ee3a9d5a-6394-42b3-8d6b-517ed759612e'_

---

### **Get All Roles for a User**

**cURL:**
> _curl -X 'GET' \
'http://localhost:8080/user-role/api/v1/role-mapping/user/ee3a9d5a-6394-42b3-8d6b-517ed759612e'_

---

### **Get All Users for a Role**

**cURL:**
> _curl -X 'GET' \
'http://localhost:8080/user-role/api/v1/role-mapping/role/381c0355-3318-4553-b960-1ad6c152762f'_

---

---

### **Get All Role Mappings**

**cURL:**
> _curl -X 'GET' \
'http://localhost:8080/user-role/api/v1/role-mapping'_

---

## Running the Project

### **Clone the Repository**
```bash
git clone https://github.com/Guyvinay/user-role.git

cd user-role

create postgres database named: user_role

./mvnw spring-boot:run

http://localhost:8080/user-role 
           OR
http://localhost:8080/user-role/swagger-ui/index.html
