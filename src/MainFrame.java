import static db.JdbcUtil.close;
import static db.JdbcUtil.commit;
import static db.JdbcUtil.getConnection;
import static db.JdbcUtil.rollback;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;


public class MainFrame extends JFrame{
	Connection con;

	public MainFrame(String title){
		super(title);
        setBounds(120, 120, 350, 330);
        setLayout(new BorderLayout());
        	
        	// 첫화면
        JPanel firstBorder =FirstBorder();
        ShowJPanel(firstBorder);

        	//종료
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public JPanel SideMenu(JPanel frm){ // 메뉴
		JButton inputButton = new JButton("INPUT");
			inputButton.addMouseListener(new MouseAdapter(){
				public void mouseClicked(MouseEvent e){
					//입력
			        JPanel inputBorder =InputBorder();
			        ShowJPanel(inputBorder);
				}
			});
		
		JButton searchButton = new JButton("SEARCH");
			searchButton.addMouseListener(new MouseAdapter(){
				public void mouseClicked(MouseEvent e){
					//검색
			        JPanel searchBorder =SearchBorder();
			        ShowJPanel(searchBorder);
				}
			});
		
		JButton deleteButton = new JButton("DELETE");
			deleteButton.addMouseListener(new MouseAdapter(){
				public void mouseClicked(MouseEvent e){
					//삭제
			        JPanel deleteBorder =DeleteBorder();
			        ShowJPanel(deleteBorder);
				}
			});
		
		JButton modifyButton = new JButton("MODIFY");
			modifyButton.addMouseListener(new MouseAdapter(){
				public void mouseClicked(MouseEvent e){
					//수정
			        JPanel modifyBorder =ModifyBorder();
			        ShowJPanel(modifyBorder);
				}
			});
		
		JButton showButton = new JButton("SHOW");
			showButton.addMouseListener(new MouseAdapter(){
				public void mouseClicked(MouseEvent e){
					//출력
			        JPanel showBorder =ShowBorder();
			        ShowJPanel(showBorder);
				}
			});
		
		JButton exitButton = new JButton("EXIT");
			exitButton.addMouseListener(new MouseAdapter(){
				public void mouseClicked(MouseEvent e){
					//종료
					System.exit(0);
				}
			});
		
		frm.add(inputButton);
		frm.add(searchButton);
		frm.add(deleteButton);
		frm.add(modifyButton);
		frm.add(showButton);
		frm.add(exitButton);
		return frm;
	}
	public void ShowJPanel(JPanel getPanel){
    	// 메뉴
		JPanel sidemenu = new JPanel();
		sidemenu.setLayout(new GridLayout(6, 1));
    		SideMenu(sidemenu);
    	
		getContentPane().removeAll();
		getContentPane().add(sidemenu);
		getContentPane().add(getPanel);// Adding to content pane, not to Frame
		
    	// window에 올리기(정렬)
		add(sidemenu, BorderLayout.WEST);
		add(getPanel, BorderLayout.CENTER);
    
		repaint();
		printAll(getGraphics());
		setVisible(true);
		
	}
	public JPanel FirstBorder(){ // 첫화면
		Border border=BorderFactory.createEtchedBorder();
		border=BorderFactory.createTitledBorder(border, "Welcome!");
        JPanel boder_JPanel=new JPanel();
        boder_JPanel.setLayout(null);
        boder_JPanel.setBorder(border);
        
        JLabel firstLabel=new JLabel("왼쪽에 배치되어 있는 버튼을",SwingConstants.LEFT);
        JLabel firstLabel2=new JLabel("클릭하여 사용하세요!",SwingConstants.LEFT);
        JButton clickButton = new JButton("누가 만든겨?");
        clickButton.addMouseListener(
    			new MouseAdapter()
    			{	
    				public void mouseClicked(MouseEvent e)
    				{
    					try{
    						String url="http://ejungdo.blog.me/50175192803";
    						executeWebBrowser(url);
    					}
    					catch(IOException exc){
    						exc.printStackTrace();
    						JOptionPane.showMessageDialog(null, "웹 브라우저를 실행하지 못했습니다."); 
    					}
    				}
    			}
    			);
//        http://blog.naver.com/sdsman36?Redirect=Log&logNo=27552531
//        panel.setLayout(null);  <-- 모든 컨테이너들은 디폴트 레이아웃이 있어서 이를 제거
//        component.setBounds(x좌표, y좌표, 넓이, 높이);
        firstLabel.setBounds(10, 30, 200, 30);
        firstLabel2.setBounds(10, 60, 200, 30);
        clickButton.setBounds(50, 220, 150, 40);
        boder_JPanel.add(firstLabel);
        boder_JPanel.add(firstLabel2);
        boder_JPanel.add(clickButton);
        boder_JPanel.setVisible(true);
		return boder_JPanel;
	}
	public JPanel InputBorder(){ // 입력
		Border border=BorderFactory.createEtchedBorder();
		border=BorderFactory.createTitledBorder(border, "Input Data!");
        JPanel boder_JPanel=new JPanel();
        boder_JPanel.setLayout(new FlowLayout());
        boder_JPanel.setBorder(border);
        //
        JLabel inputName=new JLabel("Name ", SwingConstants.RIGHT);
        final JTextField inputNameF=new JTextField(15);
        JLabel inputPhone=new JLabel("Phone ", SwingConstants.RIGHT);
        final JTextField inputPhoneF=new JTextField(15);
        JButton inputData = new JButton("INPUT");
        inputData.addMouseListener(
    			new MouseAdapter()
    			{	
    				public void mouseClicked(MouseEvent e)
    				{
    					con = getConnection();
    					PreparedStatement pstmt = null;
    					GetText getText = new GetText();
    					getText.inputText(inputNameF,inputPhoneF);
    					String name = getText.getComNameText();
    					String phone = getText.getComPhoneText();

    					String sql = "INSERT INTO common VALUES(?,?)";

    					try {
    						pstmt = con.prepareStatement(sql);
    						pstmt.setString(1, name);
    						pstmt.setString(2, phone);

    						int count = pstmt.executeUpdate();
    						if (count > 0) {
    							commit(con);
    						} else {
    							rollback(con);
    						}
    					} catch (Exception ex) {
    						ex.printStackTrace();
    					} finally {
    						close(pstmt);
    						close(con);
    					}
    					JOptionPane.showMessageDialog(null, "입력이 완료되었습니다!"); 
    				}
    			}
    			);
        
        boder_JPanel.add(inputName);
        boder_JPanel.add(inputNameF);
        boder_JPanel.add(inputPhone);
        boder_JPanel.add(inputPhoneF);
        boder_JPanel.add(inputData);
        boder_JPanel.setVisible(true);
		return boder_JPanel;
	}
	public JPanel SearchBorder(){ // 검색
		Border border=BorderFactory.createEtchedBorder();
		border=BorderFactory.createTitledBorder(border, "search Data!");
		JPanel boder_JPanel=new JPanel();
		JPanel boder_JPanelTOP=new JPanel();
        boder_JPanel.setLayout(new FlowLayout());
        boder_JPanelTOP.setLayout(new FlowLayout());
        boder_JPanel.setBorder(border);
		
//        
        final JTextArea textArea=new JTextArea(12, 20);
        JScrollPane simpleScroll = new JScrollPane(textArea);
        final JTextField tSearch=new JTextField(12);
        JButton bSearch = new JButton("Search");
        bSearch.addMouseListener(
        			new MouseAdapter()
        			{	
        				public void mouseClicked(MouseEvent e)
        				{
        					con = getConnection();
        					PreparedStatement pstmt = null;
        					ResultSet rs = null;
        					
        					GetText getText = new GetText();
        					getText.searchText(tSearch);
        					String name = getText.getSearchText();
        					
        					try{
        						String sql = "SELECT * FROM common WHERE name = ?";
        						pstmt = con.prepareStatement(sql);
        						pstmt.setString(1, name);
        						rs = pstmt.executeQuery();

        						if (rs.next()) {
        							String getName = rs.getString("NAME");
        							String getPhone = rs.getString("PHONE");
        							textArea.append("이름:" + getName + ", 전화번호:" + getPhone+"\n");
        						}
        						
        					}
        					catch (Exception ex) {
        						ex.printStackTrace();
        					} finally {
        						close(rs);
        						close(pstmt);
        						close(con);
        					}
        				}
        			}
        		);
        //
        boder_JPanelTOP.add(tSearch);
        boder_JPanelTOP.add(bSearch);
        boder_JPanel.add(boder_JPanelTOP);
        boder_JPanel.add(simpleScroll);
        boder_JPanel.setVisible(true);
		return boder_JPanel;
	}
	public JPanel DeleteBorder(){ // 삭제
		Border border=BorderFactory.createEtchedBorder();
		border=BorderFactory.createTitledBorder(border, "delete Data!");
		JPanel boder_JPanel=new JPanel();
		JPanel boder_JPanelTOP=new JPanel();
        boder_JPanel.setLayout(new FlowLayout());
        boder_JPanelTOP.setLayout(new FlowLayout());
        boder_JPanel.setBorder(border);
		
//        
        final JTextArea textArea=new JTextArea(12, 20);
        JScrollPane simpleScroll = new JScrollPane(textArea);
        final JTextField tDelete=new JTextField(12);
        JButton bDelete = new JButton("Delete");
        bDelete.addMouseListener(
        			new MouseAdapter()
        			{	
        				public void mouseClicked(MouseEvent e)
        				{
        					//삭제 구현
        					con = getConnection();
        					PreparedStatement pstmt = null;
        					GetText getText = new GetText();
        					getText.deleteText(tDelete);
        					String name = getText.getDeleteText();
        					
        					try{
        						String sql = "DELETE common WHERE name = ?";
        						pstmt = con.prepareStatement(sql);
        						pstmt.setString(1, name);
        						pstmt.executeUpdate();
        						textArea.append("삭제를 완료하였습니다. \n");
        						
        					}
        					catch (Exception ex) {
        						ex.printStackTrace();
        					} finally {
        						close(pstmt);
        						close(con);
        					}
        				}
        			}
        		);
        //
        boder_JPanelTOP.add(tDelete);
        boder_JPanelTOP.add(bDelete);
        boder_JPanel.add(boder_JPanelTOP);
        boder_JPanel.add(simpleScroll);
        boder_JPanel.setVisible(true);
		return boder_JPanel;
	}
	public JPanel ModifyBorder(){ // 수정
		Border border=BorderFactory.createEtchedBorder();
		border=BorderFactory.createTitledBorder(border, "Modify Data!");
        JPanel boder_JPanel=new JPanel();
        boder_JPanel.setLayout(new FlowLayout());
        boder_JPanel.setBorder(border);
        //
        JLabel inputName=new JLabel("Name ", SwingConstants.RIGHT);
        final JTextField inputNameF=new JTextField(15);
        JLabel inputPhone=new JLabel("Phone ", SwingConstants.RIGHT);
        final JTextField inputPhoneF=new JTextField(15);
        JButton inputData = new JButton("INPUT");
        inputData.addMouseListener(
    			new MouseAdapter()
    			{	
    				public void mouseClicked(MouseEvent e)
    				{
    					con = getConnection();
    					PreparedStatement pstmt = null;
    					GetText getText = new GetText();
    					getText.inputText(inputNameF,inputPhoneF); //inputText 메소드 재활용
    					String name = getText.getComNameText();
    					String phone = getText.getComPhoneText();
    					String sql = "UPDATE common SET PHONE = ? WHERE name = ?";
    					try {
    						pstmt = con.prepareStatement(sql);
    						pstmt.setString(1, phone); //위치 주의!!
    						pstmt.setString(2, name);
    						pstmt.executeUpdate();
    					} catch (Exception ex) {
    						ex.printStackTrace();
    					} finally {
    						close(pstmt);
    						close(con);
    					}
    					JOptionPane.showMessageDialog(null, "수정이 완료되었습니다!"); 
    				}
    			}
    			);
        
        boder_JPanel.add(inputName);
        boder_JPanel.add(inputNameF);
        boder_JPanel.add(inputPhone);
        boder_JPanel.add(inputPhoneF);
        boder_JPanel.add(inputData);
        boder_JPanel.setVisible(true);
		return boder_JPanel;
	}
	public JPanel ShowBorder(){ // 출력
		Border border=BorderFactory.createEtchedBorder();
		border=BorderFactory.createTitledBorder(border, "show Data!");
        JPanel boder_JPanel=new JPanel();
        JPanel boder_JPanelTOP=new JPanel();
        boder_JPanel.setLayout(new FlowLayout());
        boder_JPanelTOP.setLayout(new FlowLayout());
        boder_JPanel.setBorder(border);
        
        JLabel infoLabel=new JLabel("자동으로 출력됩니다!",SwingConstants.CENTER);
        
		final JTextArea textArea=new JTextArea(12, 20);
        JScrollPane simpleScroll = new JScrollPane(textArea);
        //
		con = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT * FROM common";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				textArea.append("이름:" + rs.getString("NAME") + ", 전화번호:"
						+ rs.getString("PHONE")+"\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
			close(con);
		}
        
        //
		boder_JPanelTOP.add(infoLabel);
        boder_JPanel.add(boder_JPanelTOP);
        boder_JPanel.add(simpleScroll);
        boder_JPanel.setVisible(true);
		return boder_JPanel;
	}
	class GetText{ //에디트 검색과 삭제
		JTextField search;
		JTextField delete;
		JTextField comName;
		JTextField comPhone;
		//검색
		public void searchText(JTextField search) {
			this.search = search;
		}
		public String getSearchText(){
			return search.getText();
		}
		//삭제
		public void deleteText(JTextField delete) {
			this.delete = delete;
		}
		public String getDeleteText(){
			return delete.getText();
		}
		//입력
		public void inputText(JTextField comName,JTextField comPhone) {
			this.comName = comName;
			this.comPhone = comPhone;
		}
		public String getComNameText(){
			return comName.getText();
		}
		public String getComPhoneText(){
			return comPhone.getText();
		}
	}
	
    // 웹 브라우저 실행.
    private void executeWebBrowser(String url) throws IOException {
        String[] cmds = { "C:/Program Files/Internet Explorer/IEXPLORE.EXE", url };
        Runtime.getRuntime().exec(cmds);
        //http://kin.naver.com/qna/detail.nhn?d1id=1&dirId=1040201&docId=120298325&qb=Sk9wdGlvblBhbmUg7Ju5&enc=utf8&section=kin&rank=1&search_sort=0&spq=0&pid=ROGMMU5Y7uRssc%2BR9kssssssstG-356755&sid=Ud4OHnJvLB8AAGjPjSk
    }
}
