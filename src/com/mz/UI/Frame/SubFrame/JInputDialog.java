package com.mz.UI.Frame.SubFrame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class JInputDialog extends JFrame {

	String _seizedValue;
	
	onOKClickListenerInterface _onOKClickRunnable;
	onCancelListenerInterface _onCancelClickRunnable;
	
	public String getInputValue()
	{
		return _seizedValue;
	}
	
	public JInputDialog(String Title, String PromptMessage,
			onOKClickListenerInterface onOKClickListener, onCancelListenerInterface onCancelClickListener) {
		setTitle(Title);
		_onOKClickRunnable=	onOKClickListener;
		_onCancelClickRunnable = onCancelClickListener;
		JLabel PromptLabel = new JLabel(PromptMessage);
		getContentPane().add(PromptLabel,BorderLayout.SOUTH);
		final JTextField textField = new JTextField();
		getContentPane().add(textField,BorderLayout.CENTER);
		JPanel buttons = new JPanel();
		JButton okButton = new JButton(new AbstractAction("OK") {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				_seizedValue = textField.getText();
				if(_onOKClickRunnable != null) _onOKClickRunnable.OnClick(_seizedValue);
				JInputDialog.this.dispose();
			}
		});
		JButton cancelButton = new JButton(new AbstractAction("Cancel") {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				_seizedValue = null;
				if(_onCancelClickRunnable != null) _onCancelClickRunnable.OnCancel();
				JInputDialog.this.dispose();
			}
		});
		buttons.add(okButton);
		buttons.add(cancelButton);
		getContentPane().add(buttons,BorderLayout.SOUTH);
		pack();
	}
	
	public interface onOKClickListenerInterface{
		public void OnClick(String InputValue);
	}
	
	
	public interface onCancelListenerInterface{
		public void OnCancel();
	}
}
