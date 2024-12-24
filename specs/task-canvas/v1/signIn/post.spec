# POST /v1/signIn

## /v1/signInにリクエストを送るとログインができる
* /v1/signInにメールアドレス[test@example.com]、パスワード[password]をボディに含めてPOSTリクエストを送る
* ステータスコードが"200"である
* レスポンスヘッダのAuthorizationにトークンが含まれている
