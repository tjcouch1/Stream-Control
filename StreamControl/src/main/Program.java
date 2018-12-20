package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

public class Program extends JPanel
{
	public JFrame frame;
	private JTabbedPane tabbedPane;
	
	//tab 1
	String streamTitle = readFile("files/streamTitle.txt");
	JTextField t1;
	String gameStatus = readFile("files/gameStatus.txt");
	JTextField t2;
	
	String player1 = readFile("files/player1.txt");
	JTextField pl1;
	String player2 = readFile("files/player2.txt");
	JTextField pl2;
	
	String playercam1 = readFile("files/playercam1.txt");
	JTextField pl3;
	String playercam2 = readFile("files/playercam2.txt");
	JTextField pl4;
	
	JLabel plLabel1;
	JLabel plLabel2;
	
	int score1 = 0;
	JLabel score1Label;
	int score2 = 0;
	JLabel score2Label;
	
	//tab 2
	int radioSelect = 0;
	JRadioButton[] radioButtons;
	
	String playerScene1 = readFile("files/playerScene1.txt");
	JTextField pl5;
	String playerScene2 = readFile("files/playerScene2.txt");
	JTextField pl6;
	
	JButton pB1;
	
	String commentator1 = readFile("files/commentator1.txt");
	JTextField c1;
	String commentatorTwitter1 = readFile("files/commentatorTwitter1.txt");
	JTextField cT1;

	String commentator2 = readFile("files/commentator2.txt");
	JTextField c2;
	String commentatorTwitter2 = readFile("files/commentatorTwitter2.txt");
	JTextField cT2;
	
	List<Integer> listNums = new ArrayList<Integer>();
	
	/*private class Handler implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			String str = "";
			
			if (event.getSource() == fieldPlayersGameTournamentName)
				str = String.format("field 2: %s", event.getActionCommand());
			else if (event.getSource() == fieldPlayersGameSetName)
				str = String.format("field 3: %s", event.getActionCommand());
			else if (event.getSource() == item4)
				str = String.format("field 4: %s", event.getActionCommand());
			
			JOptionPane.showMessageDialog(frame, str);
		}
	}*/
	
	public Program(JFrame frame)
	{
		super(new BorderLayout());
		this.frame = frame;
		
		//tab 1. Default vars if not read from file
		String s1 = readFile("files/score1.txt");
		if (s1 == null || s1.isEmpty())
			s1 = "0";
		score1 = Integer.parseInt(s1);
		s1 = readFile("files/score2.txt");
		if (s1 == null || s1.isEmpty())
			s1 = "0";
		score2 = Integer.parseInt(s1);
		
		if (streamTitle == null)
			streamTitle = "Stream Title";
		if (gameStatus == null)
			gameStatus = "Game Status";
		
		if (player1 == null)
			player1 = "Player 1";
		if (player2 == null)
			player2 = "Player 2";
		if (playercam1 == null)
			playercam1 = player1;
		if (playercam2 == null)
			playercam2 = player2;
		
		//tab 2
		s1 = readFile("files/settings/radioSelect.txt");
		if (s1 == null || s1.isEmpty())
			s1 = "0";
		radioSelect = Integer.parseInt(s1);
		
		if (playerScene1 == null)
			playerScene1 = player1;
		if (playerScene2 == null)
			playerScene2 = player2;
		if (commentator1 == null)
			commentator1 = "Commentator 1";
		if (commentatorTwitter1 == null)
			commentatorTwitter1 = "@Commentator1";
		if (commentator2 == null)
			commentator2 = "Commentator 2";
		if (commentatorTwitter2 == null)
			commentatorTwitter2 = "@Commentator2";
		
		tabbedPane = new JTabbedPane();
        add(tabbedPane, BorderLayout.CENTER);
		
        //First tab, containing Players/Game Scene
		JPanel tab = createTab("Player/Game", "Edit values of various items on the Players/Game Scene");
		
		//Titles Panel containing general and playercam
		JPanel p = createGroup("Titles");
		
		//Panel Containing the general titles
		JPanel p1 = createGroup("General");
		//p.add(new JLabel("Titles", JLabel.LEFT));
		
		//First Text Field, containing Stream Title (Ex: KaneSmash)
		t1 = createTextField(p1, streamTitle, 22);
		t1.setToolTipText("The Stream Title");
        
        //Second Test Field, containing Game Status (Ex: winners finals)
		t2 = createTextField(p1, gameStatus, 22);
		t2.setToolTipText("The Game Status");
		
		//P1 __ P2 __
		JPanel p2 = new JPanel(new FlowLayout());
		p2.add(createLabel("P1", "The tag shown for the first player"));
		pl1 = new JTextField(player1, 9);
		pl1.setToolTipText("The tag shown for the first player");
		p2.add(pl1);
		p2.add(createLabel("P2", "The tag shown for the second player"));
		pl2 = new JTextField(player2, 9);
		pl2.setToolTipText("The tag shown for the second player");
		p2.add(pl2);
		p1.add(p2);
		
		//add Titles group to tab
		p.add(p1);
		
		//Panel with Playercam stuff
		p1 = createGroup("PlayerCam");
		
		//checkbox for custom playercam title
		p2 = new JPanel();
		JCheckBox c = new JCheckBox("Custom Playercam Title");
		
		s1 = readFile("files/settings/check.txt");
		if (s1 == null || s1.isEmpty())
			s1 = "0";
		int i1 = Integer.parseInt(s1);
		
		c.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						pl3.setEditable(c.isSelected());
						pl4.setEditable(c.isSelected());
						pl3.setText(player1);
						pl4.setText(player2);
						playercam1 = player1;
						playercam2 = player2;
						saveTitles();
						saveToFile("files/settings/check.txt", "" + boolToInt(c.isSelected()));
					}
				});
		p2.add(c);
		p1.add(p2);
		
		//custom playercam title __ vs __
		p2 = new JPanel(new FlowLayout());
		pl3 = new JTextField(playercam1, 10);
		pl3.setToolTipText("The tag shown for the first player on the PlayerCam");
		//pl3.setEditable(false);
		p2.add(pl3);
		p2.add(new JLabel("vs"));
		pl4 = new JTextField(playercam2, 10);
		//pl4.setEditable(false);
		pl4.setToolTipText("The tag shown for the second player on the PlayerCam");
		p2.add(pl4);
		p1.add(p2);
		
		//setup c to be checked or not
		c.setSelected(intToBool(i1));
		pl3.setEditable(c.isSelected());
		pl4.setEditable(c.isSelected());
		
		//add playercam group to title group
		p.add(p1);
		
		//Button that applies changes to titles
		p1 = new JPanel();
		JButton b1 = new JButton("Apply Titles");
		b1.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						streamTitle = t1.getText();
						gameStatus = t2.getText();
						player1 = pl1.getText();
						player2 = pl2.getText();
						if (!c.isSelected())
						{
							pl3.setText(player1);
							pl4.setText(player2);
						}
						playercam1 = pl3.getText();
						playercam2 = pl4.getText();
						plLabel1.setText(player1);
						plLabel2.setText(player2);
						switch (radioSelect)
						{
						case 0:
							playerScene1 = player1;
							playerScene2 = player2;
							break;
						case 1:
							playerScene1 = playercam1;
							playerScene2 = playercam2;
							break;
						}
						if (radioSelect == 0 || radioSelect == 1)
						{
							pl5.setText(playerScene1);
							pl6.setText(playerScene2);
						}
						saveTitles();
					}
				});
		b1.setAlignmentX(CENTER_ALIGNMENT);
		p1.add(b1);
		p.add(p1);
		
		//add groups to tab
		tab.add(p);
		
		//scores group
		p = createGroup("Scores");
		
		//scores
		p1 = new JPanel(new GridLayout(2, 2));
		
		plLabel1 = createLabel(player1, "The Score for the first player");
		p1.add(plLabel1);
		plLabel2 = createLabel(player2, "The Score for the second player");
		p1.add(plLabel2);
		
		//scores for p1
		p2 = new JPanel();
		
		b1 = new JButton("-");
		b1.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						if (score1 > 0)
						{
							score1--;
							score1Label.setText("" + score1);
							saveToFile("files/score1.txt", "" + score1);
						}
					}
				});
		b1.setAlignmentX(CENTER_ALIGNMENT);
		p2.add(b1);
		
		score1Label = createLabel("" + score1, "The Score for the first player");
		p2.add(score1Label);
		
		b1 = new JButton("+");
		b1.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						score1++;
						score1Label.setText("" + score1);
						saveToFile("files/score1.txt", "" + score1);
					}
				});
		b1.setAlignmentX(CENTER_ALIGNMENT);
		p2.add(b1);
		p1.add(p2);
		
		//scores for p2
		p2 = new JPanel();
		
		b1 = new JButton("-");
		b1.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						if (score2 > 0)
						{
							score2--;
							score2Label.setText("" + score2);
							saveToFile("files/score2.txt", "" + score2);
						}
					}
				});
		b1.setAlignmentX(CENTER_ALIGNMENT);
		p2.add(b1);
		
		score2Label = createLabel("" + score2, "The Score for the first player");
		p2.add(score2Label);
		
		b1 = new JButton("+");
		b1.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						score2++;
						score2Label.setText("" + score2);
						saveToFile("files/score2.txt", "" + score2);
					}
				});
		b1.setAlignmentX(CENTER_ALIGNMENT);
		p2.add(b1);
		p1.add(p2);
		
		p.add(p1);
		
		//reset scores button
		b1 = new JButton("Reset Scores");
		b1.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						score1 = 0;
						score2 = 0;
						score1Label.setText("" + score1);
						score2Label.setText("" + score2);
						saveToFile("files/score1.txt", "" + score1);
						saveToFile("files/score2.txt", "" + score2);
					}
				});
		b1.setAlignmentX(CENTER_ALIGNMENT);
		p.add(b1);
		
		//add scores section to tab
		tab.add(p);
		
        //Second tab, Players/Commentators
		tab = createTab("Player/Commentator", "Edit values of various items on the Players Scene and Commentators Scene");
		
		//create players group
		p = createGroup("Players");
		
		//radio buttons
		p1 = new JPanel();
		p1.setLayout(new BoxLayout(p1, BoxLayout.Y_AXIS));
		
		ButtonGroup g = new ButtonGroup();
		
		radioButtons = new JRadioButton[3];

		p2 = new JPanel();
		JRadioButton r = new JRadioButton("Titles from Player Tabs");
		radioButtons[0] = r;
		r.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						radioSelect = 0;
						saveToFile("files/settings/radioSelect.txt", "" + radioSelect);
						playerScene1 = player1;
						playerScene2 = player2;
						pl5.setText(playerScene1);
						pl6.setText(playerScene2);
						pl5.setEditable(false);
						pl6.setEditable(false);
						pB1.setEnabled(false);
						saveTitles();
					}
				});
		g.add(r);
		p2.add(r);
		p1.add(p2);
		p2 = new JPanel();
		r = new JRadioButton("Titles from Player Cam");
		radioButtons[1] = r;
		r.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						radioSelect = 1;
						saveToFile("files/settings/radioSelect.txt", "" + radioSelect);
						playerScene1 = playercam1;
						playerScene2 = playercam2;
						pl5.setText(playerScene1);
						pl6.setText(playerScene2);
						pl5.setEditable(false);
						pl6.setEditable(false);
						pB1.setEnabled(false);
						saveTitles();
					}
				});
		g.add(r);
		p2.add(r);
		p1.add(p2);
		p2 = new JPanel();
		r = new JRadioButton("Custom Titles                 ");
		radioButtons[2] = r;
		r.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						radioSelect = 2;
						saveToFile("files/settings/radioSelect.txt", "" + radioSelect);
						pl5.setEditable(true);
						pl6.setEditable(true);
						pB1.setEnabled(true);
					}
				});
		g.add(r);
		p2.add(r);
		p1.add(p2);
		
		radioButtons[radioSelect].setSelected(true);
		
		p.add(p1);
		
		//custom players title __ vs __
		p1 = new JPanel(new FlowLayout());
		pl5 = new JTextField(playerScene1, 10);
		pl5.setToolTipText("The tag shown for the first player on the Player Scene");
		pl5.setEditable(radioSelect == 2);
		p1.add(pl5);
		p1.add(new JLabel("vs"));
		pl6 = new JTextField(playerScene1, 10);
		pl6.setEditable(radioSelect == 2);
		pl6.setToolTipText("The tag shown for the second player on the Player Scene");
		p1.add(pl6);
		p.add(p1);
		
		//Button that applies changes to player names
		p1 = new JPanel();
		pB1 = new JButton("Apply Player Names");
		pB1.setEnabled(radioSelect == 2);
		pB1.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						playerScene1 = pl5.getText();
						playerScene2 = pl6.getText();
						saveTitles();
					}
				});
		pB1.setAlignmentX(CENTER_ALIGNMENT);
		p1.add(pB1);
		p.add(p1);
		
		tab.add(p);
		
		//commentators group
		p = createGroup("Commentators");
		
		p1 = createGroup("Commentator 1");
		
		c1 = createTextField(p1, commentator1, 22);
		p2 = new JPanel();
		p2.add(createLabel("Twitter 1", "The twitter username shown for the first commentator on the Commentator Scene"));
		p1.add(p2);
		cT1 = createTextField(p1, commentatorTwitter1, 22);
		
		p.add(p1);
		
		p1 = createGroup("Commentator 2");
		
		c2 = createTextField(p1, commentator2, 22);
		p2 = new JPanel();
		p2.add(createLabel("Twitter 2", "The twitter username shown for the second commentator on the Commentator Scene"));
		p1.add(p2);
		cT2 = createTextField(p1, commentatorTwitter2, 22);
		
		p.add(p1);
		
		//Button that applies changes to commentator names
		p1 = new JPanel();
		b1 = new JButton("Apply Commentator Names");
		b1.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						commentator1 = c1.getText();
						commentator2 = c2.getText();
						commentatorTwitter1 = cT1.getText();
						commentatorTwitter2 = cT2.getText();
						saveTitles();
					}
				});
		b1.setAlignmentX(CENTER_ALIGNMENT);
		p1.add(b1);
		p.add(p1);
		
		tab.add(p);
		
		//tab 3 RNG
		tab = createTab("RNG", "Create a random number or list");
		//tab.setLayout(new BorderLayout());
		
		p = createGroup("Random Number Generator");
		p1 = new JPanel();
		p1.add(createLabel("Min", "The minimum value for the random number generator"));
		JTextField f1 = createTextField(p1, "1", 19);
		p.add(p1);
		
		p1 = new JPanel();
		p1.add(createLabel("Max", "The maximum value for the random number generator"));
		JTextField f2 = createTextField(p1, "5", 19);
		p.add(p1);
		
		p1 = new JPanel();
		JLabel l = createLabel("-", "The generated number");
		l.setAlignmentX(CENTER_ALIGNMENT);
		
		p2 = new JPanel();
		p2.setLayout(new BoxLayout(p2, BoxLayout.Y_AXIS));
		b1 = new JButton("Generate Number");
		b1.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						int fi1 = Integer.parseInt(f1.getText());
						int fi2 = Integer.parseInt(f2.getText());
						int i = (int) (Math.random() * (Math.abs(fi2 - fi1) + 1)) + fi1;
						l.setText("" + i);
					}
				});
		b1.setAlignmentX(CENTER_ALIGNMENT);
		p2.add(b1);
		p2.add(l);
		p1.add(p2);
		
		p.add(p1);
		
		tab.add(p);
		
		
		//list
		p = createGroup("Random List Generator");
		p1 = new JPanel();
		p1.add(createLabel("Min", "The minimum value for the random number generator"));
		JTextField f3 = createTextField(p1, "1", 19);
		p.add(p1);
		
		p1 = new JPanel();
		p1.add(createLabel("Max", "The maximum value for the random number generator"));
		JTextField f4 = createTextField(p1, "5", 19);
		p.add(p1);
		
		p1 = new JPanel();
		JLabel l1 = createLabel("-", "The generated number");
		l1.setAlignmentX(CENTER_ALIGNMENT);
		
		p2 = new JPanel();
		p2.setLayout(new BoxLayout(p2, BoxLayout.Y_AXIS));
		b1 = new JButton("Generate List");
		b1.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						int fi1 = Integer.parseInt(f3.getText());
						int fi2 = Integer.parseInt(f4.getText());
						int range = Math.abs(fi2 - fi1) + 1;
						
						listNums.clear();
						
						for (int i = fi1; i <= fi2; i++)
							listNums.add(i);
						
						Collections.shuffle(listNums);
						
						/*int in = (int) (Math.random() * (range)) + fi1;
						for (int i = 0; i < range; i++)
						{
							while (listNums.contains(in))
								in = (int) (Math.random() * (range)) + fi1;
							listNums.add(in);
						}*/
						l1.setText("" + listNums);
					}
				});
		b1.setAlignmentX(CENTER_ALIGNMENT);
		p2.add(b1);
		p2.add(l1);
		p1.add(p2);
		
		p.add(p1);
		
		//Button that applies changes to commentator names
		p1 = new JPanel();
		b1 = new JButton("Save List");
		b1.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						saveToFile("files/list.txt", "" + listNums);
					}
				});
		b1.setAlignmentX(CENTER_ALIGNMENT);
		p1.add(b1);
		
		p.add(p1);
		
		tab.add(p);
	}
	
	private JPanel createTab(String s, String t)
	{
		JPanel tab = new JPanel();
		tab.setLayout(new BoxLayout(tab, BoxLayout.Y_AXIS));
		tab.setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));
		tabbedPane.addTab(s, null, tab, t);
		
		return tab;
	}
	
	private JPanel createGroup(String s)
	{
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		p.setBorder(BorderFactory.createTitledBorder(s));
		return p;
	}
	
	private JTextField createTextField(JPanel p, int n)
	{
		//JPanel p1 = new JPanel();
		JTextField f = new JTextField(n);
		//p1.add(f);
		p.add(f);
		
		return f;
	}
	
	private JTextField createTextField(JPanel p, String s)
	{
		//JPanel p1 = new JPanel();
		JTextField f = new JTextField(s);
		//p1.add(f);
		p.add(f);
		
		return f;
	}
	
	private JTextField createTextField(JPanel p, String s, int n)
	{
		JPanel p1 = new JPanel();
		JTextField f = new JTextField(s, n);
		p1.add(f);
		p.add(p1);
		
		return f;
	}
	
	private JLabel createLabel(String s, String t)
	{
		JLabel l = new JLabel(s, SwingConstants.CENTER);
		l.setToolTipText(t);
		
		return l;
	}
	
	private void saveTitles()
	{
		saveToFile("files/streamTitle.txt", streamTitle);
		saveToFile("files/gameStatus.txt", gameStatus);
		saveToFile("files/player1.txt", player1);
		saveToFile("files/player2.txt", player2);
		saveToFile("files/playercam1.txt", playercam1);
		saveToFile("files/playercam2.txt", playercam2);
		String playercam = playercam1;
		if (!playercam2.isEmpty())
			playercam += " vs " + playercam2;
		saveToFile("files/playercam.txt", playercam);
		saveToFile("files/score1.txt", "" + score1);
		saveToFile("files/score2.txt", "" + score2);
		saveToFile("files/playerScene1.txt", playerScene1);
		saveToFile("files/playerScene2.txt", playerScene2);
		String playerScene = playerScene1;
		if (!playerScene2.isEmpty())
			playerScene += " vs " + playerScene2;
		saveToFile("files/playerScene.txt", playerScene);
		saveToFile("files/commentator1.txt", commentator1);
		saveToFile("files/commentatorTwitter1.txt", commentatorTwitter1);
		saveToFile("files/commentator2.txt", commentator2);
		saveToFile("files/commentatorTwitter2.txt", commentatorTwitter2);

		saveToFile("files/settings/windowX.txt", "" + frame.getX());
		saveToFile("files/settings/windowY.txt", "" + frame.getY());
	}
	
	private void saveToFile(String s, String t)
	{
		System.out.println("Saving " + t + " to file " + s);
		
		/*try
		{
			FileWriter fw = new FileWriter(new File(s).getAbsoluteFile(), false);
			fw.write(t);
			fw.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}*/
		
		/*try
		{
			File file = new File(s);
	
			if (!file.exists())
				file.createNewFile();
	
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(t);
			bw.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}*/
		
		/*BufferedWriter output = null;
        try
        {
            File file = new File(s);
            
            output = new BufferedWriter(new FileWriter(file));
            output.write(t);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (output != null)
				try
            	{
					output.close();
				}
	            catch (IOException e)
	            {
					e.printStackTrace();
				}
        }*/
		
		PrintWriter pr = null;
		try
		{
			pr = new PrintWriter(s);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		if (pr != null)
		{
			pr.print(t);
			pr.close();
		}
	}
	
	private String readFile(String s)
	{
		System.out.println("Reading from " + s);
		
		Scanner scan = null;
		try
		{
			scan = new Scanner(new File(s));
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		String str = "";
		if (scan != null)
		{
			boolean first = true;
			while (scan.hasNext())
			{
				if (!first)
					str += " ";
				first = false;
				str += scan.next();
			}
			scan.close();
			return str;
		}
		return null;
		
		/*try
		{
			byte[] encoded = Files.readAllBytes(Paths.get(s));
			return new String(encoded, Charset.forName("US-ASCII"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			return null;
		}*/
		/*String content;
		try
		{
			content = new Scanner(new File(s)).useDelimiter("\\Z").next();
			return content;
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		return null;*/
	}
	
	private boolean intToBool(int i)
	{
		if (i > 0)
			return true;
		else return false;
	}
	
	private int boolToInt(boolean b)
	{
		if (b)
			return 1;
		else return 0;
	}
	
	public static void main(String[] args)
	{
		JFrame frame = new JFrame("Stream Control");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Program swag = new Program(frame);
		frame.setContentPane(swag);
		frame.pack();
		frame.setSize(310, frame.getHeight());
		
		String s1 = swag.readFile("files/settings/windowX.txt");
		if (s1 == null || s1.isEmpty())
			s1 = "0";
		int x = Integer.parseInt(s1);
		
		s1 = swag.readFile("files/settings/windowY.txt");
		if (s1 == null || s1.isEmpty())
			s1 = "0";
		int y = Integer.parseInt(s1);
		
		frame.setBounds(x, y, frame.getWidth(), frame.getHeight());
		frame.setVisible(true);
	}
}

