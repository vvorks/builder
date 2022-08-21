package com.github.vvorks.builder.shared.common.lang;

import java.util.HashMap;
import java.util.Map;

/**
 * 汎用Factoryクラス
 */
public class Factory {

	private static class SingletonCreator<T> implements Creator<T> {

		/** Creator */
		private Creator<T> creator;
		/** シングルトンインスタンス */
		private T singleton;

		/**
		 * アダプタを作成する
		 *
		 * @param creator
		 * 		インスタンス生成I/F
		 */
		private SingletonCreator(Creator<T> creator) {
			this.creator = creator;
			this.singleton = null;
		}

		@Override
		public T create(Object... args) {
			if (singleton == null) {
				singleton = creator.create(args);
			}
			return singleton;
		}

	}

	/**
	 * クラスとインスタンス生成I/Fと対を管理するシングルトンオブジェクトのクラス
	 */
	public static class Configure {

		private final Map<Class<?>, Creator<?>> creators = new HashMap<>();

		/**
		 * 標準オブジェクトのクラスとインスタンス生成I/Fとの対を登録する
		 *
		 * @param <T>
		 * 		作成する型
		 * @param cls
		 * 		クラス
		 * @param creator
		 * 		インスタンス生成I/F
		 * @return
		 */
		public <T> Configure bindTo(Class<T> cls, Creator<T> creator) {
			creators.put(cls, creator);
			return this;
		}

		/**
		 * 標準オブジェクトのクラスとインスタンス生成I/Fとの対を登録する
		 *
		 * @param <T>
		 * 		作成する型
		 * @param cls
		 * 		クラス
		 * @param creator
		 * 		インスタンス生成I/F
		 * @return
		 */
		public <T> Configure bindIn(Class<T> cls, Creator<T> creator) {
			creators.put(cls, new SingletonCreator<>(creator));
			return this;
		}

		/**
		 * インスタンス生成I/Fを取得する
		 *
		 * @param <T>
		 * 		作成する型
		 * @param cls
		 * 		クラス
		 * @return
		 * 		インスタンス生成I/Fのアダプタ
		 */
		protected <T> Creator<T> getCreator(Class<T> cls) {
			@SuppressWarnings("unchecked")
			Creator<T> creator = (Creator<T>) creators.get(cls);
			return creator;
		}

	}

	/**
	 * Configureのシングルトンオブジェクト
	 */
	private static final Configure conf = new Configure();

	/**
	 * Configureオブジェクトを取得する
	 *
	 * @return
	 * 		Configureオブジェクト
	 */
	public static Configure configure() {
		return conf;
	}

	/**
	 * ファクトリが登録済みが否かを得る
	 *
	 * @param cls 対象クラス
	 * @return 登録済みの場合、真
	 */
	public static boolean isRegistered(Class<?> cls) {
		return conf.getCreator(cls) != null;
	}

	/**
	 * 新しいインスタンスを作成する
	 *
	 * @param <T>
	 * 		作成インタスタンスの型
	 * @param cls
	 * 		作成インスタンスのクラス
	 * @param args
	 * 		引数
	 * @return
	 * 		作成したインスタンス
	 */
	public static <T> T newInstance(Class<T> cls, Object... args) {
		Creator<T> creator = conf.getCreator(cls);
		Asserts.requireNotNull(creator, "cls");
		try {
			return creator.create(args);
		} catch (Exception err) {
			throw new IllegalArgumentException(err);
		}
	}

	/**
	 * シングルトンインスタンスを取得する（存在しない場合のみ作成する）
	 *
	 * @param args
	 * 		インスタンス作成時の引数（インスタンス作成後は無視される）
	 * @return
	 * 		インスタンス
	 */
	public static <T> T getInstance(Class<T> cls) {
		Creator<T> creator = conf.getCreator(cls);
		Asserts.requireNotNull(creator, "cls");
		try {
			return creator.create();
		} catch (Exception err) {
			throw new IllegalArgumentException(err);
		}
	}

	private Factory() {
	}

}
