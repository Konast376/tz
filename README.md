Author:
 1. Создание
 POST /authors/create
 <- CreateAuthorDto
 => AuthorDto
 
 2. Получение по идентификатору
 GET /authors/{id}
 <- id автора
 => AuthorDto  or  NotFoundException

 3. Получение списка с фильтрацией
 GET /authors/list
 <-AuthorDto, pageNo, pageSize, sortField, sortDirection
 =>CollectionDTO<AuthorDto>
 
 4. Обновление
POST /authors/{id}/update
 <- id, UpdateAuthorDto
 => AuthorDto  or  NotFoundException

5. Удаление
POST /authors/{id}/delete
<- id
=>200(OK)  or  NotFoundException

CreateAuthorDto  {
 String fullName;
 Date dateOfBirth;
 String nationality;
 }
 
AuthorDto  {
 Long id;
 String fullName;
 Date dateOfBirth;
 String nationality;
 }
 
 UpdateAuthorDto  {
  String fullName;
  Date dateOfBirth;
  String nationality;
  }