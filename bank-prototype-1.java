package bank;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.RoundRectangle2D;
import java.awt.Shape;

import javax.swing.border.AbstractBorder;
import javax.print.DocFlavor;
import javax.swing.*;

import java.util.HashMap;
import java.util.Map;

class line extends JPanel {
    int x1;
    int x2;
    int y1;
    int y2;

    public line(int x1z, int y1z, int x2z, int y2z) {
        x1 = x1z;
        y1 = y1z;
        x2 = x2z;
        y2 = y2z;
    }

    public void paint(java.awt.Graphics g) {
        g.drawLine(x1, y1, x2, y2);
    }
}

class RoundedPanel extends JPanel {
    private int radius;

    public RoundedPanel(int radius) {
        this.radius = radius;
        setOpaque(false); // Make the panel non-opaque to draw custom shapes
    }

    @Override
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

        // Clip the panel with rounded borders
        Shape clip = new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), radius, radius);

        // Optionally draw background
        g2.setColor(new Color(238,238,238,255));
        g2.fill(clip);

        g2.setClip(clip);

        super.paintComponent(g2);

        g2.dispose();
    }

    protected void paintBorder(java.awt.Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

        // Create a rounded rectangle for border
        Shape roundedRectangle = new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);

        // Paint the border
        g2.setColor(getForeground());
        g2.draw(roundedRectangle);

        // Dispose of the graphics context
        g2.dispose();
    }
}

class RoundedBorder extends AbstractBorder {
    private int radius;
    private Color borderColor;

    RoundedBorder(int radius, Color borderColor) {
        this.radius = radius;
        this.borderColor = borderColor;
    }

    public void paintBorder(java.awt.Component c, java.awt.Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        g2.draw(new RoundRectangle2D.Double(x, y, width - 1, height - 1, radius, radius));
    }

    public Insets getBorderInsets(java.awt.Component c) {
        return new Insets(this.radius + 1, this.radius + 1, this.radius + 2, this.radius);
    }

    public boolean isBorderOpaque() {
        return true;
    }
}

class FrameDimension {
    int frameWidth;
    int frameHeight;
    int widthCenter;
    int heightCenter;

    public FrameDimension(int frmWidth, int frmHeight) {
        frameWidth = frmWidth;
        frameHeight = frmHeight;
        widthCenter = frmWidth / 2;
        heightCenter = (1 + frmHeight) / 4;
    }
}

class Account {
    String name;
    String password;
    double balance;

    public Account(String accountName, String accountPassword, double accountBalance) {
        name = accountName;
        password = accountPassword;
        balance = accountBalance;
    }
}

class defaultButton extends JButton {
    public defaultButton(String text) {
        super(text);
        setBackground(new Color(51,51,51));
        setForeground(Color.white);
        setFont(new Font("Verdana", Font.PLAIN, 15));
        setFocusPainted(false);
        setRolloverEnabled(false);
        setContentAreaFilled(false);
        setOpaque(true);
        setBorder(new RoundedBorder(15, Color.black));

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setBackground(new Color(60, 60, 60));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                setBackground(new Color(51,51,51));
            }

            public void mousePressed(java.awt.event.MouseEvent evt) {
                setBackground(new Color(20, 20, 20));
            }

            public void mouseReleased(java.awt.event.MouseEvent evt) {
                setBackground(new Color(51, 51, 51));
            }
        });
    }
}

class secondaryButton extends JButton {
    public secondaryButton(String text) {
        super(text);

        setBackground(new Color(238,238,238,255));
        setForeground(Color.black);
        setBorder(new RoundedBorder(15, Color.black));
        setFont(new Font("Verdana", Font.PLAIN, 15));
        setFocusPainted(false);
        setContentAreaFilled(false);
        setOpaque(true);

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                setBackground(Color.black);
                setForeground(Color.white);
            }
            public void mouseReleased(java.awt.event. MouseEvent evt) {
                setBackground(new Color(238,238,238,255));
                setForeground(Color.black);
            }
        });
    }
}

class defaultTextField extends JTextField {
    public defaultTextField(String text) {
        super(text);

        setForeground(Color.gray);
        setFont(new Font("Verdana", Font.PLAIN, 15));
        setBorder(new RoundedBorder(10, Color.black));

        addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                if (getText().equals(text)) {
                    setText("");
                }
                setForeground(Color.black);
            }
            public void focusLost(FocusEvent e) {
                if (getText().equals("")) {
                    setText(text);
                }
                setForeground(Color.gray);
            }
        });
    }
}

public class protoOne {
    static Map<String, String> accountCredentials = new HashMap<>();
    static Map<String, Double> accountBalances = new HashMap<>();

    static final Font TITLE_FONT = new Font("Verdana", Font.BOLD, 24);
    static final Font NORMAL_FONT = new Font("Verdana", Font.PLAIN, 15);

    public static void main(String[] args) {
        showLoginScreen();
    }

    static void showMainMenu(String account) {
        double balance = accountBalances.get(account);
        JFrame mainMenuFrame = new JFrame();

        JPanel depositWithdrawButton = new JPanel(null);
        JPanel depositPnl = new JPanel();
        JPanel withdrawPnl = new JPanel();

        JLabel balanceLabel = new JLabel("Balance: " + " $ " + balance);
        JLabel accountName = new JLabel(account);

        defaultButton deposit = new defaultButton("Deposit");
        defaultButton withdraw = new defaultButton("Withdraw");

        defaultButton depositPnlButton = new defaultButton("Deposit") {
            public void setFont(Font font) {
                super.setFont(new Font("Verdana", Font.PLAIN, 10));
            }
        };
        defaultButton withdrawPnlButton = new defaultButton("Withdraw") {
            public void setFont(Font font) {
                super.setFont(new Font("Verdana", Font.PLAIN, 8));
            }
        };

        secondaryButton cancel = new secondaryButton("Cancel") {
            public void setFont(Font font) {
                super.setFont(new Font("Verdana", Font.PLAIN, 12));
            }
        };

        defaultTextField depositPnlField = new defaultTextField("Amount");
        defaultTextField withdrawPnlField = new defaultTextField("Amount");

        mainMenuFrame.setLayout(null);
        mainMenuFrame.setSize(400, 500);
        mainMenuFrame.setVisible(true);
        mainMenuFrame.setLocationRelativeTo(null);
        mainMenuFrame.setResizable(false);
        mainMenuFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        depositWithdrawButton.setVisible(true);
        depositWithdrawButton.setSize(400, 500);

        depositPnl.setSize(400, 500);
        depositPnl.setVisible(false); 
        depositPnl.setLayout(null);

        withdrawPnl.setSize(400, 500);
        withdrawPnl.setVisible(false);
        withdrawPnl.setLayout(null);

        balanceLabel.setBounds(50, 50, 400, 30);
        balanceLabel.setFont(TITLE_FONT);

        accountName.setBounds(50, 20, 200, 30);
        accountName.setFont(NORMAL_FONT);

        deposit.setBounds(75, 175, 250, 40);
        withdraw.setBounds(75, 250, 250, 40);

        depositPnlButton.setBounds(75, 220, 75, 30);
        withdrawPnlButton.setBounds(75, 220, 75, 30);

        cancel.setBounds(160, 220, 75, 30);

        depositPnlField.setBounds(75, 175, 250, 40);
        withdrawPnlField.setBounds(75, 175, 250, 40);

        JPanel lineOne = new JPanel() {
            protected void paintComponent(java.awt.Graphics g) {
                g.setColor(Color.black);
                g.drawLine(30, 0, 350, 0);
            }
        };
        lineOne.setBounds(0, 90, 400, 30);

        mainMenuFrame.add(balanceLabel);
        mainMenuFrame.add(accountName);
        mainMenuFrame.add(lineOne);
        mainMenuFrame.add(deposit);
        mainMenuFrame.add(withdraw);
        mainMenuFrame.add(depositWithdrawButton);
        mainMenuFrame.add(depositPnl);
        mainMenuFrame.add(withdrawPnl);

        depositWithdrawButton.add(deposit);
        depositWithdrawButton.add(withdraw);

        depositPnl.add(depositPnlField);
        depositPnl.add(depositPnlButton);

        withdrawPnl.add(withdrawPnlField);
        withdrawPnl.add(withdrawPnlButton);

        deposit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                depositPnl.add(cancel);
                depositWithdrawButton.setVisible(false);
                depositPnl.setVisible(true);
            }
        });

        withdraw.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                withdrawPnl.add(cancel);
                depositWithdrawButton.setVisible(false);
                withdrawPnl.setVisible(true);
            }
        });

        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                depositPnl.setVisible(false);
                withdrawPnl.setVisible(false);
                depositWithdrawButton.setVisible(true);
            }
        });

        depositPnlButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (Double.parseDouble(depositPnlField.getText()) >= 0.00) {
                    accountBalances.put(account, accountBalances.get(account) + Double.parseDouble(depositPnlField.getText()));
                    System.out.println(accountBalances.get(account));
                    balanceLabel.setText("Balance: " + "$ " + accountBalances.get(account));

                    depositPnl.setVisible(false);
                    depositWithdrawButton.setVisible(true);
                }
            }
        });

        withdrawPnlButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (Double.parseDouble(withdrawPnlField.getText()) >= 0.00 && accountBalances.get(account) >= 1) {
                    accountBalances.put(account, accountBalances.get(account) - Double.parseDouble(withdrawPnlField.getText()));
                    System.out.println(accountBalances.get(account));
                    balanceLabel.setText("Balance: " + "$ " + accountBalances.get(account));

                    withdrawPnl.setVisible(false);
                    depositWithdrawButton.setVisible(true);
                }
            }
        });
    }

    static void showCreateAccountScreen() {
        JFrame createAccountFrame = new JFrame();
        RoundedPanel buttonPanel = new RoundedPanel(15);

        JLabel signUp = new JLabel("Sign Up");

        defaultTextField accountNameField = new defaultTextField("Name");
        defaultTextField passwordField = new defaultTextField("Password");

        createAccountFrame.setLayout(null);
        createAccountFrame.setVisible(true);
        createAccountFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        createAccountFrame.setSize(400, 500);
        createAccountFrame.setResizable(false);
        createAccountFrame.setLocationRelativeTo(null);

        FrameDimension frameDimensions = new FrameDimension(createAccountFrame.getWidth(), createAccountFrame.getHeight());

        signUp.setBounds(150, 50, 200, 30);
        signUp.setFont(TITLE_FONT);
 
        accountNameField.setBounds(frameDimensions.widthCenter / 6 * 3 , 125, 200, 35);

        passwordField.setBounds(frameDimensions.widthCenter / 6 * 3, 175, 200, 35);

        JButton registerButton = createRegisterButton(accountNameField, passwordField, createAccountFrame);

        createAccountFrame.add(accountNameField);
        createAccountFrame.add(passwordField);
        createAccountFrame.add(registerButton);
        createAccountFrame.add(signUp);
        createAccountFrame.add(buttonPanel);

        buttonPanel.setBounds(75, 250, 250, 40);
        //buttonPanel.add(registerButton);
    }

    static JButton createRegisterButton(JTextField accountNameField, JTextField passwordField, JFrame frame) {
        defaultButton registerButton = new defaultButton("Register");
        registerButton.setBounds(75, 250, 250, 40);

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String accountName = accountNameField.getText();
                String password = passwordField.getText();

                registerButton.setFocusPainted(false);

                if (!accountName.isEmpty() && !password.isEmpty() &&
                    !accountName.equals("Name") && !password.equals("Password")) {
                    Account newAccount = new Account(accountName, password, 0);
                    accountCredentials.put(newAccount.name, newAccount.password);
                    accountBalances.put(newAccount.name, newAccount.balance);

                    System.out.println("Created Account: " + accountCredentials + " " + accountBalances);
                    frame.dispose();
                    showLoginScreen();
                }
            }
        });

        return registerButton;
    }

    static void showLoginScreen() {
        JFrame loginFrame = new JFrame();

        JLabel loginLabel = new JLabel("Login");

        defaultButton createAccountButton = new defaultButton("Create an Account");
        defaultButton loginButton = new defaultButton("Login");

        defaultTextField nameLogIn = new defaultTextField("Name");
        defaultTextField passwordLogIn = new defaultTextField("Password");

        loginFrame.setLayout(null);
        loginFrame.setVisible(true);
        loginFrame.setSize(400, 500);
        loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        loginFrame.setResizable(false);
        loginFrame.setLocationRelativeTo(null);

        FrameDimension frameDimensions = new FrameDimension(loginFrame.getWidth(), loginFrame.getHeight());

        nameLogIn.setBounds(frameDimensions.widthCenter / 2, 125, 200, 35);

        passwordLogIn.setBounds(frameDimensions.widthCenter / 2, 175, 200, 35);

        loginLabel.setBounds(frameDimensions.widthCenter / 5 * 4, 50, 100, 30);
        loginLabel.setFont(TITLE_FONT);

        createAccountButton.setBounds(75, 300, 250, 40);

        loginButton.setBounds(75, 250, 250, 40);

        loginFrame.add(createAccountButton);
        loginFrame.add(loginLabel);
        loginFrame.add(nameLogIn);
        loginFrame.add(passwordLogIn);
        loginFrame.add(loginButton);

        createAccountButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loginFrame.dispose();
                showCreateAccountScreen();
            }
        });

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (accountCredentials.containsKey(nameLogIn.getText())) {
                    String accountPassword = accountCredentials.get(nameLogIn.getText());
                    if (passwordLogIn.getText().equals(accountPassword)) {
                        loginFrame.dispose();
                        showMainMenu(nameLogIn.getText());
                    }
                }
            }
        });
    }
}
