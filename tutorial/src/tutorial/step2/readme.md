## ステップ２:タワーを建てる

* `new`演算子でインスタンス（`BasicTower`）を作る
* インスタンスメソッドを呼び出す
* インスタンスはメソッドの引数に渡すこともできる
* さりげなく，オーバーロードをやってる
  * `TowerGame.start()`と`TowerGame.start(Tower)`
* `BasicTower`には１名兵隊（`Soldier`）を設定できる
  * 兵隊はノーマル／スピード型／パワー型を選べる

タワーの耐久力（life）は任意指定できるけど，兵隊１人じゃほぼ無意味ですぐ死ぬ。