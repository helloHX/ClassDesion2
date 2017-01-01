package coinGame;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class ClickableCell extends Cell {
	public ClickableCell(String string) {
		super(string,false);
		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (getText().equals("H")) {
					setForeground(Color.red);
					setText("T"); // ��H��T��ת
				} else {
					setForeground(Color.black);
					setText("H"); // ��T��H��ת
				}
			}
		});
	}
}
