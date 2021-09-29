# VVorks Builder

## 概要

VVorks Builderは、モデル定義から各種のソフトウェアを自動生成するソフトウェアである
（最近話題に上がることの多い、いわゆる「ノーコード」開発ツール）

VVorks Builderは、Webアプリとし、開発フレームワークとして、
サーバーサイドはSpring Bootを、
クライアントサイドは、GWTを採用する。

クライアント・サーバー間の通信プロトコルとしては、
ベースプロトコルとしてWebSocketを、
上位層はJson-RPCを採用する。

## ステータス

開発中

## ビルドとデバッグ

### ビルド

最終成果物である vvorksbuilder-[version].jar を作成するためのコマンドは以下の通りである。

	gradlew bootJar

### 実行

作成されたjarファイルの実行方法は以下の通りである。

	java -jar build/libs/vvorksbuilder-[version].jar

上記プログラム実行後、Webブラウザで以下にアクセスする

	http://localhost:8080/index.html

## デバッグ

デバック実行のためには以下コマンドを使用する。

	gradlew bootRun			# Eclipseでサーバー側をデバッグ実行させてもよい
	gradlew gwtCodeServer

上記プログラム実行後、Webブラウザで以下にアクセスする

	http://localhost:8080/debug.html
