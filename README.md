# Nested Comments Service

## API Design:

![Screenshot 2024-03-24 at 7.52.11 PM.png](https://github.com/divpali/CommentsService/blob/main/Screenshot%202024-03-24%20at%207.52.11%20PM.png)

### 1. Create User

POST: http://localhost:8080/comments-service/user

Request:
```
{
    "username":"user1"
}
```

Response:
```
{
    "userId": 1,
    "userName": "user1"
}
```

### 2. Create Post

POST: http://localhost:8080/comments-service/posts/1

Request:
```
{
    "content":"New post"
}
```

Response:
```
{
    "postId": 1,
    "postContent": "New post",
    "postCreatedTime": "2024-03-24T13:05:27.924+00:00",
    "postUserId": 1,
    "postUsername": "user1",
    "comments": [],
    "likesCount": 0,
    "dislikesCount": 0
}
```

### 3. Add Comments To Post

POST: http://localhost:8080/comments-service/add/1

Request:
```
{
    "userId":"3",
    "userName":"user3",
    "commentContent":"Comment3 to Post"
}
```

Response:
```
{
    "postId": 1,
    "postContent": "New post",
    "postCreatedTime": "2024-03-24T13:05:27.924+00:00",
    "postUserId": 1,
    "postUsername": "user1",
    "comments": [
        {
            "commentId": 1,
            "commentContent": "Comment2 to Post",
            "commentCreatedTime": "2024-03-24T13:05:53.704+00:00",
            "commentUserId": 2,
            "commentUserName": "user2",
            "replies": [],
            "likesCount": 0,
            "dislikesCount": 0
        },
        {
            "commentId": 2,
            "commentContent": "Comment3 to Post",
            "commentCreatedTime": "2024-03-24T13:06:56.232+00:00",
            "commentUserId": 3,
            "commentUserName": "user3",
            "replies": [],
            "likesCount": 0,
            "dislikesCount": 0
        }
    ],
    "likesCount": 0,
    "dislikesCount": 0
}
```

### 4. Add Reply To Comment

POST: http://localhost:8080/comments-service/add-replyComment/2

Request:
```
{
    "userId":"4",
    "userName":"user4",
    "commentContent":"Reply Comment to Comment2"
}
```

Response:
```
{
    "commentId": 2,
    "commentContent": "Comment3 to Post",
    "commentCreatedTime": "2024-03-24T13:06:56.232+00:00",
    "commentUserId": 3,
    "commentUserName": "user3",
    "replies": [
        {
            "commentId": 3,
            "commentContent": "Reply Comment to Comment2",
            "commentCreatedTime": "2024-03-24T13:08:36.479+00:00",
            "commentUserId": 4,
            "commentUserName": "user4",
            "replies": [],
            "likesCount": 0,
            "dislikesCount": 0
        }
    ],
    "likesCount": 0,
    "dislikesCount": 0
}
```

### 5. Like Or Dislike Post

POST: http://localhost:8080/comments-service/like-post/1?likeDislikeType=LIKE

Response:
```
{
    "postId": 1,
    "postContent": "New post",
    "postCreatedTime": "2024-03-24T13:05:27.924+00:00",
    "postUserId": 1,
    "postUsername": "user1",
    "comments": [
        {
            "commentId": 1,
            "commentContent": "Comment2 to Post",
            "commentCreatedTime": "2024-03-24T13:05:53.704+00:00",
            "commentUserId": 2,
            "commentUserName": "user2",
            "replies": [],
            "likesCount": 0,
            "dislikesCount": 0
        },
        {
            "commentId": 2,
            "commentContent": "Comment3 to Post",
            "commentCreatedTime": "2024-03-24T13:06:56.232+00:00",
            "commentUserId": 3,
            "commentUserName": "user3",
            "replies": [
                {
                    "commentId": 3,
                    "commentContent": "Reply Comment to Comment2",
                    "commentCreatedTime": "2024-03-24T13:08:36.479+00:00",
                    "commentUserId": 4,
                    "commentUserName": "user4",
                    "replies": [],
                    "likesCount": 0,
                    "dislikesCount": 0
                }
            ],
            "likesCount": 0,
            "dislikesCount": 0
        }
    ],
    "likesCount": 1,
    "dislikesCount": 0
}
```

POST: http://localhost:8080/comments-service/like-post/1?likeDislikeType=DISLIKE

Response:
```
{
    "postId": 1,
    "postContent": "New post",
    "postCreatedTime": "2024-03-24T13:05:27.924+00:00",
    "postUserId": 1,
    "postUsername": "user1",
    "comments": [
        {
            "commentId": 1,
            "commentContent": "Comment2 to Post",
            "commentCreatedTime": "2024-03-24T13:05:53.704+00:00",
            "commentUserId": 2,
            "commentUserName": "user2",
            "replies": [],
            "likesCount": 0,
            "dislikesCount": 0
        },
        {
            "commentId": 2,
            "commentContent": "Comment3 to Post",
            "commentCreatedTime": "2024-03-24T13:06:56.232+00:00",
            "commentUserId": 3,
            "commentUserName": "user3",
            "replies": [
                {
                    "commentId": 3,
                    "commentContent": "Reply Comment to Comment2",
                    "commentCreatedTime": "2024-03-24T13:08:36.479+00:00",
                    "commentUserId": 4,
                    "commentUserName": "user4",
                    "replies": [],
                    "likesCount": 0,
                    "dislikesCount": 0
                }
            ],
            "likesCount": 0,
            "dislikesCount": 0
        }
    ],
    "likesCount": 1,
    "dislikesCount": 1
}
```

### 6. Like Or Dislike Comment

POST: http://localhost:8080/comments-service/like-comment/1?likeDislikeType=LIKE

Response:
```
{
    "commentId": 1,
    "commentContent": "Comment2 to Post",
    "commentCreatedTime": "2024-03-24T13:05:53.704+00:00",
    "commentUserId": 2,
    "commentUserName": "user2",
    "replies": [],
    "likesCount": 1,
    "dislikesCount": 0
}
```

POST: http://localhost:8080/comments-service/like-comment/1?likeDislikeType=DISLIKE

Response:
```
{
    "commentId": 1,
    "commentContent": "Comment2 to Post",
    "commentCreatedTime": "2024-03-24T13:05:53.704+00:00",
    "commentUserId": 2,
    "commentUserName": "user2",
    "replies": [],
    "likesCount": 1,
    "dislikesCount": 1
}
```


### 7. Get Post By Id

GET: http://localhost:8080/comments-service/post/1

Response:
```
{
    "postId": 1,
    "postContent": "New post",
    "postCreatedTime": "2024-03-24T13:05:27.924+00:00",
    "postUserId": 1,
    "postUsername": "user1",
    "comments": [
        {
            "commentId": 1,
            "commentContent": "Comment2 to Post",
            "commentCreatedTime": "2024-03-24T13:05:53.704+00:00",
            "commentUserId": 2,
            "commentUserName": "user2",
            "replies": [],
            "likesCount": 1,
            "dislikesCount": 0
        },
        {
            "commentId": 2,
            "commentContent": "Comment3 to Post",
            "commentCreatedTime": "2024-03-24T13:06:56.232+00:00",
            "commentUserId": 3,
            "commentUserName": "user3",
            "replies": [
                {
                    "commentId": 3,
                    "commentContent": "Reply Comment to Comment2",
                    "commentCreatedTime": "2024-03-24T13:08:36.479+00:00",
                    "commentUserId": 4,
                    "commentUserName": "user4",
                    "replies": [],
                    "likesCount": 0,
                    "dislikesCount": 0
                }
            ],
            "likesCount": 0,
            "dislikesCount": 0
        }
    ],
    "likesCount": 1,
    "dislikesCount": 1
}
```
