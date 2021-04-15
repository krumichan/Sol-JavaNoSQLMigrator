package jp.co.cec.vfInputDatMigrator.util;

import java.awt.Dimension;
import java.awt.HeadlessException;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 * Dialog Utility
 */
public class DialogUtil {
	private DialogUtil() { }
	
	private static final String MESSAGE_CANNOT_DISPLAY_DIALOG = "[WARN] ダイアログ表示できませんでした。 キーボード、ディスプレイ、またはマウスに依存するコードが、キーボード、ディスプレイ、またはマウスをサポートしない環境で呼び出されたためです。";
	
	/**
	 * Textで、ダイアログ表示
	 * @param isDialog trueの時、ダイアログ表示もする。尚、何方でも System.out.println する。
	 * @param message 表示するメッセージ
	 */
	public static void showMessageDialogWithText(boolean isDialog, String message) {
		if (isDialog) {
			System.out.println(message);
			try {
				JOptionPane.showMessageDialog(null, message);
			} catch (HeadlessException e) {
				System.out.println(MESSAGE_CANNOT_DISPLAY_DIALOG);
			}
		} else {
			System.out.println(message);
		}
	}

	/**
	 * TextAreaで、ダイアログ表示
	 * @param isDialog trueのとき、ダイアログ表示もする. なお、どちらでも System.out.println する.
	 * @param message  表示するメッセージ
	 */
	public static void showMessageDialogWithTextArea(boolean isDialog, String message) {
		if (isDialog) {
			System.out.println(message);
		} else {
			showMessageDialog(message);
		}
	}
	
	/**
	 * ダイアログ表示の共通部分
	 * @param message
	 */
	private static void showMessageDialog(String message) {
		JTextArea jta = new JTextArea(message);
		jta.setEditable(false);
		jta.setLineWrap(true);
		jta.setOpaque(false);
		jta.setSize(480, 10);
		jta.setPreferredSize(new Dimension(480, jta.getPreferredSize().height));
		System.out.println(message);
		try {
			JOptionPane.showMessageDialog(null, jta);
		} catch (HeadlessException e) {
			System.out.println(MESSAGE_CANNOT_DISPLAY_DIALOG);
		}
	}
}
