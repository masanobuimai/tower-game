## ステップ４:インターフェースを実装する

`extends`に指定できるクラスは１つだが，インターフェースなら複数指定できる（ただし，`implements`で指定する）


### Recoveryインターフェース
* 実装するとライフ回復が可能になる
  * `targets()`を実装すること
* `targets()`で`Map`の使い方を覚える
  * ついでに列挙型とラッパー型も覚える

`MyTower`のインスタンス変数を使えば，使用回数ごとに対象を変えることもできる


### EarthShakeインターフェース
* 実装すると全体攻撃が可能になる
  * `shake()`を実装すること
* `shake()`の引数 `Ground.shake()` を実行して地面を揺らすが，たまに失敗する（バグ持ち）
  * `try-catch`の例外処理を覚える
  * 検査例外の例は無い
  * 自前で例外クラスを作る例もない
  
  
### RushAttackインターフェース
* 実装すると突進攻撃が可能になる
  * `attack()`を実装すること

`MyTower`のインスタンス変数を使えば，使用回数ごとに対象を変えることもできる