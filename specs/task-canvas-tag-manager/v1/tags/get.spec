# GET TaskCanvasTagManager /v1/tags

## TaskCanvasTagManagerの/v1/tagsにGETリクエストを送るとタグ一覧が返却される
* API"taskCanvasTagManager"のURL"/v1/tags"にGETリクエストを送る
* ステータスコードが"200"である
* レスポンスボディにタグの一覧が含まれている
* レスポンスボディにタグのIDが含まれている
* レスポンスボディにタグの名前が含まれている

## TaskCanvasTagManagerの/v1/tagsにGETリクエストを送ると削除済みのタグが含まれない
* API"taskCanvasTagManager"のURL"/v1/tags"にGETリクエストを送る
* レスポンスボディに削除済みのタグが含まれていない

## TaskCanvasTagManagerのv1/tagsにGETリクエストを送るとタグの検索ができる
* API"taskCanvasTagManager"のURL"/v1/tags?"にGETリクエストを送る