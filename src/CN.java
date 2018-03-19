import java.util.List;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;



class Axioms extends JPanel implements MouseListener{
	Axioms leftA;
	Axioms rightA;
	JPanel left=new JPanel();
	JPanel right=new JPanel();
	public int type=0;//1-合取,2-析取,3-存在,4-任意
	public boolean isRev=false;
	boolean isCN=false;
	boolean isR=false;
	boolean isleft=false;
	boolean isright=false;
	CN singleCN=new CN("null");
	R singleR=new R("null");
	JLabel temp1=new JLabel();
	JLabel temp2=new JLabel(".");
	JLabel temp3=new JLabel("¬");
	public Axioms()
	{
		this.setLayout(new GridLayout(1,1));
	}
	public Axioms(int t)
	{
		leftA=new Axioms();
		rightA=new Axioms();
		type=t;
		left.setBackground(Color.RED);
		right.setBackground(Color.RED);
		left.setLayout(new GridLayout(1,1));
		right.setLayout(new GridLayout(1,1));
		left.addMouseListener(this);
		right.addMouseListener(this);
		temp1.setHorizontalAlignment(JLabel.CENTER);
		temp2.setHorizontalAlignment(JLabel.CENTER);
		temp3.setHorizontalAlignment(JLabel.CENTER);
		switch(type)
		{
		case 1:
			temp1.setText("冂");
			this.setLayout(new GridLayout(1,3));
			this.add(left);
			this.add(temp1);
			this.add(right);
			break;
		case 2:
			temp1.setText("凵");
			this.setLayout(new GridLayout(1,3));
			this.add(left);
			this.add(temp1);
			this.add(right);
			break;
		case 3:
			temp1.setText("∃");
			this.setLayout(new GridLayout(1,4));
			this.add(temp1);
			this.add(left);
			this.add(temp2);
			this.add(right);
			break;
		case 4:
			temp1.setText("∀");
			this.setLayout(new GridLayout(1,4));
			this.add(temp1);
			this.add(left);
			this.add(temp2);
			this.add(right);
			break;
		default:
			break;
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==left)
		{
			isleft=true;
			isright=false;
			if(type==1||type==2)
			{
				Start.canCN=true;
				Start.canR=false;
				Start.canAxioms=true;
				Start.beclicked=this;
				left.setBackground(Color.GREEN);
				Start.checkclick=true;
			}
			else if(type==3||type==4)
			{
				Start.canCN=false;
				Start.canR=true;
				Start.canAxioms=false;
				Start.beclicked=this;
				left.setBackground(Color.GREEN);
				Start.checkclick=true;
			}
		}
		if(e.getSource()==right)
		{
			isleft=false;
			isright=true;
			if(type==1||type==2||type==3||type==4)
			{
				Start.canCN=true;
				Start.canR=false;
				Start.canAxioms=true;
				Start.beclicked=this;
				right.setBackground(Color.GREEN);
				Start.checkclick=true;
			}
		}
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}

class CN extends JLabel{
	public String myname="";
	boolean isRev=false;
	public CN(String str)
	{
		myname=str;
		this.setText("<html> "+myname+"</html>");
		this.setFont(new Font("宋体",Font.PLAIN,15));
	}
}

class R extends JLabel{
	public String myname="";
	public R(String str)
	{
		myname=str;
		this.setText("<html> "+myname+"</html>");
		this.setFont(new Font("宋体",Font.PLAIN,15));
	}
}

class node{
	public List<Axioms> innerA=new ArrayList<Axioms>();
	//public List<CN> innerCN=new ArrayList<CN>();
	public void add(Axioms a)
	{
		innerA.add(a);
	}
}

class edge{
	public node left=new node();
	public node right=new node();
	public R myR;
	public edge(node x,node y)
	{
		left=x;
		right=y;
	}
}
