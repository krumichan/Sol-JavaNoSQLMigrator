package jp.co.cec.vfInputDatMigrator.db.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.TransactionIsolationLevel;

public class SqlSessionFactoryCustom {
	/**
	 * セッション・ファクトリー
	 */
	private SqlSessionFactory factory;
	
	/**
	 * テスト実行か
	 */
	public static boolean isTest = false;
	
	/**
	 * デフォルト・コンストラクタ
	 */
	public SqlSessionFactoryCustom(Properties prop) throws Exception {
		if (isTest) {
			try (Reader r = new InputStreamReader(new FileInputStream("src/main/resources/mybatis-config.xml"));) {
				factory = new SqlSessionFactoryBuilder().build(r, prop);
			} catch (IOException e) {
				e.printStackTrace();
				throw new Exception("mybatis-configの読み込み失敗");
			}
		} else {
			try (Reader r = Resources.getResourceAsReader("resources/mybatis-config.xml");) {
				factory = new SqlSessionFactoryBuilder().build(r, prop);
			} catch (IOException e) {
				e.printStackTrace();
				throw new Exception("mybatis-configの読み込み失敗");
			}
		}
	}

    /**
     * @fn SqlSession openSession()
     * @brief SQLセッション開始
     * @return SQLセッション
     */
    public SqlSession openSession() {
        return factory.openSession();
    }
    
    /**
     * @fn SqlSession openSession(final boolean autoCommit)
     * @brief SQLセッション開始
     * @param autoCommit オートコミットモードにするかどうか
     * @return SQLセッション
     */
    public SqlSession openSession(final boolean autoCommit) {
        return factory.openSession(autoCommit);
    }

    /**
     * @fn SqlSession openSession(final Connection connection)
     * @brief SQLセッション開始
     * @param connection データベース接続
     * @return SQLセッション
     */
    public SqlSession openSession(final Connection connection) {
        return factory.openSession(connection);
    }

    /**
     * @fn SqlSession openSession(final ExecutorType execType)
     * @brief SQLセッション開始
     * @param execType 実行モード（シンプル、ステートメント再利用、バッチ）
     * @return SQLセッション
     */
    public SqlSession openSession(final ExecutorType execType) {
        return factory.openSession(execType);
    }

    /**
     * @fn SqlSession openSession(final TransactionIsolationLevel level)
     * @brief SQLセッション開始
     * @param level トランザクション分離レベル
     * @return SQLセッション
     */
    public SqlSession openSession(final TransactionIsolationLevel level) {
        return factory.openSession(level);
    }

    /**
     * @fn SqlSession openSession(final ExecutorType execType, final boolean autoCommit)
     * @brief SQLセッション開始
     * @param execType 実行モード（シンプル、ステートメント再利用、バッチ）
     * @param autoCommit オートコミットフラグ
     * @return SQLセッション
     */
    public SqlSession openSession(final ExecutorType execType, final boolean autoCommit) {
        return factory.openSession(execType, autoCommit);
    }

    /**
     * @fn SqlSession openSession(final ExecutorType execType, final Connection connection)
     * @brief SQLセッション開始
     * @param execType 実行モード（シンプル、ステートメント再利用、バッチ）
     * @param connection データベース接続
     * @return SQLセッション
     */
    public SqlSession openSession(final ExecutorType execType, final Connection connection) {
        return factory.openSession(execType, connection);
    }

    /**
     * @fn SqlSession openSession(final ExecutorType execType, final TransactionIsolationLevel level)
     * @brief SQLセッション開始
     * @param execType 実行モード（シンプル、ステートメント再利用、バッチ）
     * @param level トランザクション分離レベル
     * @return SQLセッション
     */
    public SqlSession openSession(final ExecutorType execType, final TransactionIsolationLevel level) {
        return factory.openSession(execType, level);
    }

    /**
     * @fn Configuration getConfiguration()
     * @brief 設定取得
     * @return SQLセッション 設定
     */
    public Configuration getConfiguration() {
        return factory.getConfiguration();
    }
}
