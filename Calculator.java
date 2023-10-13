import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;



public class Calculator extends JFrame {
	private static final String DIV="\u00F7",SIGN  = "\u00b1",DOT = "\u2219",PLUS="\u002b",MIN="\u2212",MUL="\u00d7";//유니코드 써먹으려고
	private static JTextField outF = new JTextField("0");
	private static String[][] bNames = {
			{PLUS,MIN,MUL,DIV},
			{"7","8","9","%"},
			{"4","5","6","CE"},
			{"1","2","3","="},
			{SIGN,"0",DOT}
	};
	private String op1,op2,operator;
	private static JButton[][] btns = new JButton[bNames.length][];
	
	public Calculator() {
		super("계산기");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		JPanel outPan = new JPanel(new GridBagLayout()), keyPan = new JPanel(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.BOTH;
		gc.weightx = 1.0;
		gc.weighty = 1.0;
		gc.insets = new Insets(2, 2, 2, 2);
		gc.gridx = gc.gridy = 0;
		gc.ipadx = 400;
		gc.ipady = 20;
		outF.setFont(new Font("Arial",Font.BOLD,24));
		outF.setHorizontalAlignment(JTextField.RIGHT);
		outF.setEditable(false);//커서가 깜빡이지 않게됨
		outF.setBackground(new Color(51,0,102));
		outF.setForeground(new Color(235,235,244));
		
		outPan.add(outF, gc);
		gc.gridx = 1;
		gc.gridy = 0;
		gc.ipadx = 0;
		outPan.add(new JButton("DEL"), gc);	
		MadeKeyPad(keyPan);
		add(outPan, BorderLayout.NORTH);
		add(keyPan, BorderLayout.CENTER);
		
		setPreferredSize(new Dimension(500,300));
		pack();
		setVisible(true);
		setLocationRelativeTo(null);
	}
	public static void main(String[] args) {
		new Calculator();
	}
	public /*static*/ void MadeKeyPad(JPanel p) {
		//그리드 백 레이아수을 위해선 그리드백컨스뭐시기가 필요
		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.BOTH;
		gc.weightx = 1.0;
		gc.weighty = 1.0; 
		gc.insets = new Insets(2, 2, 2, 2);
		
		for(int y =0 ; y<bNames.length;y++) {
			btns[y] = new JButton[bNames[y].length];//각 행에대한 1차원배열
			for(int x=0;x<bNames[y].length;x++) {
				gc.gridx = x;
				gc.gridy = y;
				if(bNames[y][x].equals("=")) {
					gc.gridheight = 2;//DIV같은걸로 해도딤
				} else {
					gc.gridheight =1;
				}
				p.add(btns[y][x] = new JButton(bNames[y][x]),gc);//btns를 이미 준비함,JButton안써도 됨, gc안넣으면 1자로 나옴
				btns[y][x].setFocusPainted(false);//뭔역할이야?
				btns[y][x].addActionListener(new MyHandler());//static 메소드는 생성될 메소드를 사용불가(메소드 생성전에 있기덈에)
			}
		}
	}
	private static String compute(String AS, String oper, String BS) {
		int		A, B;
		float	result=0;
		A = Integer.parseInt(AS);
		B = Integer.parseInt(BS);
		
		switch (oper) {
		case PLUS:
			result = A + B;
			break;
		case MIN:
			result = A - B;
			break;
		case DIV:
			result = (float)A / B;
			break;
		case MUL:
			result = A * B;
			break;
		default:
			break;
		}
		
		return (""+((int)result));///compute가 실수형을 parseint중이라 두번이상 계산이 안됨 그래서 int로 바꿈
	}
	
	class MyHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String bName = ((JButton)e.getSource()).getText();
			switch(bName) {
			case "0": case "1": case "2": case "3": case "4": case "5": case "6": case "7": case "8": case "9":
				if(outF.getText().equals("0")/*getTitle().equals("0")*/) {
					outF.setText(bName);//setTitle(bName);
				}
				else {
					outF.setText(outF.getText()+bName);
				//setTitle(getTitle()+bName);//0이 아니면 bname,이거 제못바뀜
				}
				break;
			case "CE":
				outF.setText("0");
				break;
			case PLUS: case MIN: case DIV: case MUL:
				op1 = outF.getText();
				operator = bName;
				outF.setText("0");//op2를 위해
				break;
			case "=":
				op2 = outF.getText();
				outF.setText(compute(op1, operator, op2));//compute가 parseint중이라 두번이상 계산이 안됨
				break;
			}
			//return (""+((int)result));
		}
		
	}
}
