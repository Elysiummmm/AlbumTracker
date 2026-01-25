# API Endpoints

## /artists

### POST /artists
Request parameters:
- artistName: String

Returns: artist ID

### PUT /artists/{id}
Request parameters:
- artistName: String

Returns: artist ID

### GET /artists/{id}
Returns: Artist

### DELETE /artists/{id}
Returns: nothing

### GET /artists/{id}/albums
Returns: all albums by artist

## /albums

### POST /albums
Request parameters:
- albumName: String
- jacketURL: String
- artistId: Integer

Returns: album ID

### PUT /albums/{id}
Request parameters:
- albumName: String
- jacketURL: String
- artistId: Integer

Returns: album ID

### GET /albums/{id}
Returns: corresponding album

### DELETE /albums/{id}
Returns: nothing

## /tracks

### POST /tracks
Request parameters:
- trackName: String
- length: Float
- albumId: Integer

Returns: track ID

### PUT /tracks/{id}
Request parameters:
- trackName: String
- length: Float
- albumId: Integer

Returns: track ID

### GET /tracks/{id}
Returns: Track

### DELETE /tracks/{id}
Returns: nothing

## /users

### POST /users
Request parameters:
- username: String
- pfpURL: String

Returns: user ID

### PUT /users/{id}
Request parameters:
- username: String
- pfpURL: String

Returns: user ID

### GET /users/{id}
Returns: User

### DELETE /users/{id}
Returns: nothing

### PUT /users/{id}/albums
Request parameters:
- albumId: Integer

Returns: nothing

### GET /users/{id}/albums
Returns: all albums this user has listened to

### PUT /users/{id}/reviews
Request parameters:
- reviewId: Integer

Returns: nothing

### GET /users/{id}/reviews
Returns: all reviews this user has written

## /reviews

### POST /reviews
Request parameters:
- authorId: Integer
- albumId: Integer
- score: Integer
- content: String (can be empty)

Returns: review ID

### PUT /reviews/{id}
Request parameters:
- authorId: Integer
- albumId: Integer
- score: Integer
- content: String (can be empty)

Returns: review ID

### GET /reviews/{id}
Returns: Review

### DELETE /reviews/{id}
Returns: nothing