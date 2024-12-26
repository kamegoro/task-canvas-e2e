# POST /v1/todos

* TaskCanvasにログインする

## /v1/todosにPOSTリクエストを送るとTODOが作成される
* URL"/v1/todos"にボディ"{\"content\": \"新規追加するTODO\", \"completed\": true}"で、POSTリクエストを送る
* ステータスコードが"200"である
* レスポンスボディに新規作成したTODOのIDが含まれている

## /v1/todosにPOSTリクエスト送る際にcontentが空の場合は400が返却される
* URL"/v1/todos"にボディ"{ \"completed\": false}"で、POSTリクエストを送る
* ステータスコードが"400"である

## /v1/todosにPOSTリクエスト送る際にcompletedが空の場合でも作成される
* URL"/v1/todos"にボディ"{\"content\": \"新規追加するTODO\"}"で、POSTリクエストを送る
* ステータスコードが"200"である