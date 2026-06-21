# task-canvas-e2e

e2e testing of task-canvas using kotlin and gauge

## 実行方法

### ローカル環境（docker-compose）

```bash
mvn test
```

`src/test/resources/env.yaml` の設定（localhost）がそのまま使われる。

### MiniStack環境（aws-infrastructure）

[aws-infrastructure](https://github.com/kamegoro/aws-infrastructure) の MiniStack 上に
task-canvas の AWS 構成（S3+CloudFront / ECS / Aurora）を立ち上げて E2E テストを実行する。

```bash
# aws-infrastructure リポジトリから一括実行
cd ../aws-infrastructure
make e2e \
  FRONTEND_DIR=../task-canvas/frontend/out \
  E2E_DIR=../task-canvas-e2e \
  E2E_CMD="mvn test -Denv=ministack" \
  DB_PASSWORD=<task_canvas ユーザーのパスワード>
```

`-Denv=ministack` を指定すると `src/test/resources/env-ministack.yaml` がベース設定として
読み込まれ、Terraform の outputs から生成された環境変数で各エンドポイントが上書きされる。

#### 環境変数の対応表

`make e2e` 内で `scripts/tf-outputs-ministack-env.sh` が terraform output から
以下の環境変数を自動設定する（Hoplite 2.x の `__` ネストセパレータ規約）。

| 環境変数 | terraform output | 説明 |
|---|---|---|
| `TASK_CANVAS__REST__BASE_URL` | `alb_dns_name` | バックエンド API の URL |
| `TASK_CANVAS_WEB__BACKEND_HOST` | `alb_dns_name` | バックエンドのホスト名 |
| `TASK_CANVAS_WEB__BACKEND_PORT` | 固定: `80` | ALB のポート |
| `TASK_CANVAS__DB__JDBC_URL` | `db_endpoint`, `db_port`, `db_name` | Aurora の JDBC URL |
| `TASK_CANVAS__DB__USERNAME` | 固定: `task_canvas` | DB ユーザー名 |
| `TASK_CANVAS__DB__PASSWORD` | `DB_PASSWORD` 引数 | DB パスワード（要手動指定） |
| `TASK_CANVAS__DB__SCHEMA` | 固定: `task_canvas` | DB スキーマ名 |
| `SELENIDE__BASE_URL` | `frontend_distribution_domain_name` | フロントエンドの URL |
