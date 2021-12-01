# HTTP SERVER FOR STORING RECIPES

### Technology Stack
- Spring Boot
- REST API
- Spring Boot Security
- H2 database
- LocalDateTime

### Run the project
#
```
.\gradlew bootRun
```

## Usage
_Request without authentication_
```
POST /api/register 
{
    "email": "CamelCaseRecipe@somewhere.com",
     "password": "C00k1es."
}
```

Status code: 200 (Ok)

_Request with basic authentication_
```
POST /api/recipe/new request 
{
   "name": "Mint Tea",
   "category": "beverage",
   "description": "Light, aromatic and refreshing beverage, ...",
   "ingredients": ["boiled water", "honey", "fresh mint leaves"],
   "directions": ["Boil water", "Pour boiling hot water into a mug", "Add fresh mint leaves", "Mix and let the mint leaves seep for 3-5 minutes", "Add honey and mix again"]
}

```
Response:
```
{
   "id": 1
}
```

## Features

### Registration of users
```
POST request ("api/recipe/register")
```
### User post new recipe
```
POST request ("api/recipe/new")
```
### User change recipe by id
```
POST request ("api/recipe/{id}")
```
### User search recipe by name and category
```
POST request ("api/recipe/{id}")
```
### User get recipe by id
```
GET request ("api/recipe/{id}")
```
