import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Start extends JFrame implements ActionListener,MouseListener{
	////////////////////////////////////////////////////////全局变量///////////////////////////////////////////////
	
	//public static int indexCN=-1;
	//public static int indexR=-1;
	
	public static Axioms beclicked=new Axioms();  //当前被点击的AXIOM对象
	public static boolean checkclick=false;   //判断当前是否有输入框被选中
	
	public static boolean canCN=false;      //是否能输入CN
	public static boolean canR=false;       //是否能输入R
	public static boolean canAxioms=true;   //是否能输入公式Axioms
	
	///////////////////////////////////////////////////////全局变量///////////////////////////////////////////////
	public boolean isSatisfy=true;   //公式是否可满足
	
	public boolean isfirst=true;    //是否当前输入值是初始化公式（非添加的子值）
	
	public List<node> x=new ArrayList<node>();
	public List<edge> xy=new ArrayList<edge>();
	
	public JLabel recentlabel=new JLabel();       
	
	JPanel jp=new JPanel();
	
	JPanel axiom=new JPanel();    //公式区
	
	JTextArea textbox=new JTextArea();  //输出区
	
	JButton run=new JButton("GO!");
	
	FlowLayout flow=new FlowLayout();  //定义flowlayout
	GridLayout grid=new GridLayout(1,1); //定义gridlayout
	
	//输入CN/R
	JPanel myCN = new JPanel ();     //存放CN的容器
	JPanel myR = new JPanel ();      //存放R的容器
	JTextField inputCN=new JTextField(20);    //供用户输入CN
	JTextField inputR=new JTextField(20);     //供用户输入R
	JButton btCN=new JButton("Add");     
	JButton btR=new JButton("Add");
	
	//输入交、并、存在、任意、非
	JButton btexist=new JButton("∃");
	JButton btunivers=new JButton("∀");
	JButton btconj=new JButton("冂");
	JButton btdisc=new JButton("凵");
	JButton btrevers=new JButton("¬");
	
	public Start()
	{
		jp.setLayout(null);
		jp.setBounds(0, 0, 1000, 618);
		
		//公式
		axiom.setBounds(100, 50, 800, 100);
		axiom.setLayout(grid);
		jp.add(axiom);
		//输出区
		//textbox.setFont(new Font("宋体",Font.PLAIN,15));
		textbox.setBounds(50, 200,400, 350);
		jp.add(textbox);
		
		//执行按钮
		run.setBounds(450, 150, 75, 50);
		run.setFont(new Font("Segoe Print",Font.BOLD,20));
		run.addActionListener(this);
		jp.add(run);
		
		//选择框
		myCN.setBounds(525, 200,250, 200);
		myCN.setLayout(flow);
		myCN.setBorder(BorderFactory.createTitledBorder("CN"));
		jp.add(myCN);
		myR.setBounds(525,400, 250, 150);
		myR.setLayout(flow);
		myR.setBorder(BorderFactory.createTitledBorder("R"));
		jp.add(myR);
		
		//输入CN和R
		inputCN.setBounds(780, 350, 200, 40);
		jp.add(inputCN);
		inputR.setBounds(780, 480, 200, 40);
		jp.add(inputR);
		btCN.setBounds(880, 400, 100, 40);
		jp.add(btCN);
		btCN.addActionListener(this);
		btR.setBounds(880, 530, 100, 40);
		jp.add(btR);
		btR.addActionListener(this);
		
		//交、并、合取、析取、非
		btexist.setBounds(780, 200, 50, 50);
		btunivers.setBounds(830, 200, 50, 50);
		btconj.setBounds(880, 200, 50, 50);
		btdisc.setBounds(930, 200, 50, 50);
		btrevers.setBounds(780, 255, 200, 50);
		
		btexist.addActionListener(this);
		btunivers.addActionListener(this);
		btconj.addActionListener(this);
		btdisc.addActionListener(this);
		btrevers.addActionListener(this);
		
		jp.add(btexist);
		jp.add(btunivers);
		jp.add(btconj);
		jp.add(btdisc);
		jp.add(btrevers);	
		
		add(jp);
		
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		int windowsWedth = 1000;
		int windowsHeight = 638;
		//设置窗体在显示器居中显示
		this.setBounds((width - windowsWedth) / 2,(height - windowsHeight) / 2, windowsWedth, windowsHeight);
		setVisible(true);
		setSize(windowsWedth,windowsHeight);
	}
	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==run)
		{
			isSatisfy=Tableaux();
		}
		if(e.getSource()==btCN)
		{
			if(inputCN.getText().trim().length()==0)
			{
				JOptionPane.showMessageDialog(this,"请输入CN"); 
			}
			else
			{
				CN tempCN=new CN(inputCN.getText());
				myCN.add(tempCN);
				tempCN.addMouseListener(this);
				myCN.updateUI();
				inputCN.setText("");
			}
		}
		if(e.getSource()==btR)
		{
			if(inputR.getText().trim().length()==0)
			{
				JOptionPane.showMessageDialog(this,"请输入R"); 
			}
			else
			{
				R tempR=new R(inputR.getText());
				myR.add(tempR);
				tempR.addMouseListener(this);
				myR.updateUI();
				inputR.setText("");
			}
		}
		if(canAxioms)
		{
			if(e.getSource()==btconj)
			{
				Axioms temp=new Axioms(1);
				if(isfirst==true)
				{
					node nodex=new node();
					x.add(nodex);
					nodex.add(temp);
					axiom.add(temp);
					isfirst=false;
				}
				else
				{
					if(beclicked.isleft)
					{
						beclicked.left.removeMouseListener(this);
						beclicked.left.setBackground(Color.WHITE);
						beclicked.left.add(temp);
						temp.isRev=beclicked.leftA.isRev;
						beclicked.leftA=temp;
						beclicked.isleft=false;
					}
					else if(beclicked.isright)
					{
						beclicked.right.removeMouseListener(this);
						beclicked.right.setBackground(Color.WHITE);
						beclicked.right.add(temp);
						temp.isRev=beclicked.rightA.isRev;
						beclicked.rightA=temp;
						beclicked.isright=false;
					}
				}
				axiom.updateUI();
			}
			if(e.getSource()==btdisc)
			{
				Axioms temp=new Axioms(2);
				if(isfirst==true)
				{
					node nodex=new node();
					x.add(nodex);
					nodex.add(temp);
					axiom.add(temp);
					isfirst=false;
				}
				else
				{
					if(beclicked.isleft)
					{
						beclicked.left.removeMouseListener(this);
						beclicked.left.setBackground(Color.WHITE);
						beclicked.left.add(temp);
						temp.isRev=beclicked.leftA.isRev;
						beclicked.leftA=temp;
						beclicked.isleft=false;
					}
					else if(beclicked.isright)
					{
						beclicked.right.removeMouseListener(this);
						beclicked.right.setBackground(Color.WHITE);
						beclicked.right.add(temp);
						temp.isRev=beclicked.rightA.isRev;
						beclicked.rightA=temp;
						beclicked.isright=false;
					}
				}
				axiom.updateUI();
			}
			if(e.getSource()==btexist)
			{
				Axioms temp=new Axioms(3);
				if(isfirst==true)
				{
					node nodex=new node();
					x.add(nodex);
					nodex.add(temp);
					axiom.add(temp);
					isfirst=false;
				}
				else
				{
					if(beclicked.isleft)
					{
						beclicked.left.removeMouseListener(this);
						beclicked.left.setBackground(Color.WHITE);
						beclicked.left.add(temp);
						temp.isRev=beclicked.leftA.isRev;
						beclicked.leftA=temp;
						beclicked.isleft=false;
					}
					else if(beclicked.isright)
					{
						beclicked.right.removeMouseListener(this);
						beclicked.right.setBackground(Color.WHITE);
						beclicked.right.add(temp);
						temp.isRev=beclicked.rightA.isRev;
						beclicked.rightA=temp;
						beclicked.isright=false;
					}
				}
				axiom.updateUI();
			}
			if(e.getSource()==btunivers)
			{
				Axioms temp=new Axioms(4);
				if(isfirst==true)
				{
					node nodex=new node();
					x.add(nodex);
					nodex.add(temp);
					axiom.add(temp);
					isfirst=false;
				}
				else
				{	
					if(beclicked.isleft)
					{
						beclicked.left.removeMouseListener(this);
						beclicked.left.setBackground(Color.WHITE);
						beclicked.left.add(temp);
						temp.isRev=beclicked.leftA.isRev;
						beclicked.leftA=temp;
						beclicked.isleft=false;
					}
					else if(beclicked.isright)
					{
						beclicked.right.removeMouseListener(this);
						beclicked.right.setBackground(Color.WHITE);
						beclicked.right.add(temp);
						temp.isRev=beclicked.rightA.isRev;
						beclicked.rightA=temp;
						beclicked.isright=false;
					}
					checkclick=false;
				}
				axiom.updateUI();
			}
			if(e.getSource()==btrevers)
			{
				if(isfirst==true||!checkclick)
				{
					JOptionPane.showMessageDialog(this,"You can't negate nothing!"); 
				}
				else
				{
					String rev="";
					if(beclicked.isleft)
					{
						if(beclicked.leftA.isRev)
						{
							beclicked.leftA.isRev=false;
							rev="true";
						}
						else
						{
							beclicked.leftA.isRev=true;
							rev="false";
						}
						beclicked.isleft=false;
					}
					else if(beclicked.isright)
					{
						if(beclicked.rightA.isRev)
						{
							beclicked.rightA.isRev=false;
							rev="true";
						}
						else
						{
							beclicked.rightA.isRev=true;
							rev="false";
						}
						beclicked.isright=false;
					}
					JOptionPane.showMessageDialog(this,"Negating success! \nNow the negate condition is ["+rev+"] !"); 
				}
				axiom.updateUI();
			}
		}
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getClickCount()==2)
		{
			if(canCN)
			{
				for(Component c:myCN.getComponents())
				{
					if(e.getSource()==c)
					{
						if(beclicked.isleft)
						{
							
							beclicked.left.removeMouseListener(this);
							beclicked.left.setBackground(Color.WHITE);
							beclicked.left.add(c);
							beclicked.leftA.isCN=true;
							beclicked.leftA.singleCN=(CN) c;
							if(beclicked.leftA.isRev)
							{
								beclicked.leftA.singleCN.isRev=true;
							}
							checkclick=false;
							canCN=false;
							beclicked.isleft=false;
							jp.updateUI();
							break;
						}
						else if(beclicked.isright)
						{
							beclicked.right.removeMouseListener(this);
							beclicked.right.setBackground(Color.WHITE);
							beclicked.right.add(c);
							beclicked.rightA.isCN=true;
							beclicked.rightA.singleCN=(CN) c;
							if(beclicked.rightA.isRev)
							{
								beclicked.rightA.singleCN.isRev=true;
							}
							checkclick=false;
							canCN=false;
							beclicked.isright=false;
							jp.updateUI();
							break;
						}
					}
				}
			}
			if(canR)
			{
				for(Component c:myR.getComponents())
				{
					if(e.getSource()==c)
					{
						beclicked.left.removeMouseListener(this);
						beclicked.left.setBackground(Color.WHITE);
						beclicked.left.add(c);
						beclicked.leftA.isR=true;
						beclicked.leftA.singleR=(R) c;
						checkclick=false;
						canR=false;
						beclicked.isleft=false;
						jp.updateUI();
						break;
					}
				}
			}
		}
	}
	
	boolean Tableaux()
	{
		int i,k;
		boolean isfulldepended;
		Axioms chosen;
		i=0;
			do//对所有node进行遍历
			{
					k=0;
					do//对节点Xi里的公式进行遍历
					{
						isfulldepended=true;
						if(k>=x.get(i).innerA.size())
						{
							k=0;
						}
						//对x.get(i).innerA.get(k)进行拓展
						chosen=x.get(i).innerA.get(k);
						//交规则*********************************************************************************************
						if(chosen.type==1)
						{
							if(checkAxioms(chosen.leftA,i))//如果左子树不起冲突
							{
								if(!sameAxioms(chosen.leftA,i))//如果L(x)里没有与之重复的项
								{
									isfulldepended=false;
									x.get(i).innerA.add(chosen.leftA);
									out("Apply the 冂-rule:");
									out("    L(X"+i+") —> L(X"+i+")∪{"+outA(chosen.leftA,false)+"}");
								}
							}
							else
							{
								isfulldepended=false;
								out("Apply the 冂-rule:");
								out(outA(chosen.leftA,false)+" is leads to a clash!!!");
								return false;
							}
							if(checkAxioms(chosen.rightA,i))//如果右子树不起冲突
							{
								if(!sameAxioms(chosen.rightA,i))//如果L(x)里没有与之重复的项
								{
									isfulldepended=false;
									x.get(i).innerA.add(chosen.rightA);
									out("Apply the 冂-rule:");
									out("    L(X"+i+") —> L(X"+i+")∪{"+outA(chosen.rightA,false)+"}");
								}
							}
							else
							{
								isfulldepended=false;
								out("Apply the 冂-rule:");
								out(outA(chosen.rightA,false)+" is leads to a clash!!!");
								return false;
							}
						}
						//并规则*****************************************************************************************************
						if(chosen.type==2)
						{
							if(!sameAxioms(chosen.leftA,i)&&!sameAxioms(chosen.rightA,i))
							{
								isfulldepended=false;
								if(checkAxioms(chosen.leftA,i))
								{
									x.get(i).innerA.add(chosen.leftA);
									out("Apply the 凵-rule:");
									out("    L(X"+i+") —> L(X"+i+")∪{"+outA(chosen.leftA,false)+"}");
								}
								else if(checkAxioms(chosen.rightA,i))
								{
									x.get(i).innerA.add(chosen.rightA);
									out("Apply the 凵-rule:");
									out("    L(X"+i+") —> L(X"+i+")∪{"+outA(chosen.rightA,false)+"}");
								}
								else
								{
									out("Apply the 凵-rule:");
									out(outA(chosen.leftA,false)+" and "+outA(chosen.rightA,false)+" both lead to a clash!!!");
									return false;
								}
							}
						}
						//存在规则***************************************************************************************************
						if(chosen.type==3)
						{
							boolean existXY=false;
							for(edge c:xy)
							{
								c.myR.equals(chosen.leftA.singleR);
								existXY=true;
							}
							if(!existXY)
							{
								isfulldepended=false;
								String rev="";
								if(chosen.rightA.isRev)
								{
									rev="¬";
								}
								node tempN=new node();
								x.add(tempN);//加入新的节点y
								tempN.innerA.add(chosen.rightA);//L(y)中加入右子树
								edge tempE=new edge(x.get(i),tempN);//新生成一条边<x,y>
								tempE.myR=chosen.leftA.singleR;//L(<x,y>)=R
								xy.add(tempE);  
								out("Apply the ∃-rule:");
								out("    Create a new node X"+(i+1)+" and edge <X"+i+",X"+(i+1)+">\n"
								+"        L(X"+(i+1)+")={"+rev+outA(chosen.rightA,false)+"}\n"
								+ "        L(<X"+i+",X"+(i+1)+">)="+chosen.leftA.singleR.myname);
							}
						}
						//任意规则***************************************************************************************************
						if(chosen.type==4)
						{
							boolean existinXY=false;
							for(edge c:xy)
							{
								if(c.myR.myname.equals(chosen.leftA.singleR.myname))
								{
									for(Axioms a:c.right.innerA)
									{
										if(outA(a,false).equals(outA(chosen.rightA,false)))
										{
											existinXY=true;
										}
									}
									if(!existinXY)
									{	
										isfulldepended=false;
										if(checkAxioms(chosen.rightA,x.indexOf(c.right)))
										{
											if(!sameAxioms(chosen.rightA,x.indexOf(c.right)))
											{
												c.right.innerA.add(chosen.rightA);
												out("Apply the ∀-rule:");
												out("    L(X"+x.indexOf(c.right)+")->L(X"+x.indexOf(c.right)+")∪{"+outA(chosen.rightA,false)+"}");
											}
										}
										else
										{
											out("Apply the ∀-rule:");
											out(outA(chosen.rightA,false)+" is leads to a clash!!!");
											return false;
										}			
									}
								}
							}
						}
						k++;
					}while(!isfulldepended||k<x.get(i).innerA.size());//当还没有完全扩展或者没有遍历到最后一个结点的时候，继续遍历
				i++;
			}while(i<x.size());
		return true;
	}
	
	boolean checkAxioms(Axioms a,int i)//i为当前节点
	{
		for(Axioms c:x.get(i).innerA)
		{
			if(outA(c,true).equals(outA(a,true))&&(c.isRev!=a.isRev))
				return false;
		}
		return true;
	}
	boolean sameAxioms(Axioms a,int i)
	{
		for(Axioms c:x.get(i).innerA)
		{
			if(outA(c,false).equals(outA(a,false)))
				return true;
		}
		return false;
	}
	void out(String str){
    	textbox.append(str+"\n");
    	textbox.setCaretPosition(textbox.getText().length());
	}
	
	String outA(Axioms a,boolean isfirst)
	{
		String rev="";
		if(a.isRev&&!isfirst)
		{
			rev="¬";
		}
		if(a.isCN)
		{
			return rev+a.singleCN.myname;
		}
		else if(a.isR)
		{
			return rev+a.singleR.myname;
		}
		else
		{
			switch(a.type)
			{
			case 1:
				return rev+"(("+outA(a.leftA,false)+")"+"冂"+"("+outA(a.rightA,false)+"))";
			case 2:
				return rev+"(("+outA(a.leftA,false)+")"+"凵"+"("+outA(a.rightA,false)+"))";
			case 3:
				return rev+"(∃"+outA(a.leftA,false)+"."+"("+outA(a.rightA,false)+"))";
			case 4:
				return rev+"(∀"+outA(a.leftA,false)+"."+"("+outA(a.rightA,false)+"))";
			}
		}
		return null;
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
