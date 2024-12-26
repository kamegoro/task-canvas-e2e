# POST /v1/signIn

## /v1/signInにリクエストを送るとログインができる
* /v1/signInにメールアドレス[test@example.com]、パスワード[password]をボディに含めてPOSTリクエストを送る
* ステータスコードが"200"である
* レスポンスヘッダのAuthorizationにトークンが含まれている

## /v1/signInにリクエストを送る際にメールアドレスが空の場合は400が返却される
* /v1/signInにパスワード[passowrd]をボディに含めてPOSTリクエストを送る
* ステータスコードが"400"である