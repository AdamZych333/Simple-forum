# Simple forum web application
Application similar to twitter/reddit, but simpler. Created for me to practice full stack development.

## Technologies

Server: java, spring boot

Client: typescript, angular, rxjs

Database: mysql

Other: docker compose

## Running app
In main directory: `docker-compose up`

In server directory: `mvnw spring-boot:run`
It might be needed to generate implementations of mappers with: `mvnw clean package`

In client directory: `ng serve -o`

On `localhost:9000/` you can see database with adminer. 

Server: mysql

User: root

Password: mysql

Database: forum

On `localhost:8080/` is located server application. 

On `localhost:4200/` is located client application (it opens up automatically after ng serve -o). 

## Functionality overview

**General functionality:**
- JWT Authentication
- CRUD users
- CRUD posts
- CRUD comments on posts
- Likes and dislikes
- Tags
- Searching posts by tag, query
- Following posts

**General pages**
- Home page ('/')
  - Recent posts
  - Search bar
  - Popular tags
- Post page ('/posts/:id')
  - Selected post
  - Comments
- Authentication ('/auth/login', '/auth/register')
  - Signing in
  - Signing up
- Profile page ('/users/:id')
  - User info
  - Created posts
  - Created comments
  - Followed posts. You can see how many comments were created from last visit under given post.
